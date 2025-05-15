package com.app.JWTImplementation.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.app.JWTImplementation.dto.projection.UserHistoryReservationProjection;
import com.app.JWTImplementation.dto.responses.HistoryReservationResponse;
import com.app.JWTImplementation.dto.responses.UserReservationHistoryResponse;
import com.app.JWTImplementation.dto.responses.UserResponse;
import com.app.JWTImplementation.exceptions.CancelationInvalidException;
import com.app.JWTImplementation.exceptions.ReservationCancelledException;
import com.app.JWTImplementation.exceptions.ReserveNotFoundException;
import com.app.JWTImplementation.model.Customer;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.repository.ReserveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.dto.UserDTO;
import com.app.JWTImplementation.exceptions.UserNotFoundException;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.model.User.Role;
import com.app.JWTImplementation.repository.UserRepository;
import com.app.JWTImplementation.service.impl.IUserService;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse findUserByUsername(String username) {

        Optional<User> userOptional = repository.findUserByUsername(username);

        if (userOptional.isEmpty()) {
            log.debug("Usuario no encontrado con username: {}", username);
            throw new UserNotFoundException("User not found whith username: " + username);
        }

        User user = userOptional.get();

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreateAt(),
                user.getUpdateAt(),
                user.getRole()
        );

    }

    @Override
    public List<UserResponse> findAllUsers() {

        return repository.findAll().stream()
                .map(UserResponse::fromUser)
                .toList();

    }

    @Override
    public UserResponse saveUser(UserDTO userDetails) {

        User user = userDetails.toEntity(passwordEncoder);
        User savedUser = repository.save(user);
        return UserResponse.fromUser(savedUser);

    }

    @Override
    public UserResponse findUserById(Integer id) {

        return repository.findById(id)
                .map(UserResponse::fromUser)
                .orElseThrow(() -> new UserNotFoundException(id));

    }

    @Override
    public void deleteUserById(Integer id) {
        User user = repository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id));
        repository.deleteById(user.getId());
    }

    @Override
    public UserResponse updateUserById(Integer id, UserDTO userDetails) {

        //User user = this.findUserById(id);
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());

        if(userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // encriptar la contraseña nueva
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUpdateAt(LocalDateTime.now());
        user.setRole(Role.CUSTOMER);

        User updateUser = repository.save(user);

        return UserResponse.fromUser(updateUser);

    }


    // Consultas para los usuarios de tipo cliente, informacion personal
    // Listar el historial de reservas de un cliente en especifico
    public HistoryReservationResponse findAllUserReservationHistoryById(Integer id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Roles other than Customer do not have booking history");
        }

        List<UserHistoryReservationProjection> projections = repository.findAllHistoryReservationById(id);

        List<UserReservationHistoryResponse> userReservations = projections.stream()
                .map(UserReservationHistoryResponse::fromUserReservationHistory)
                .toList();

        Integer countReservations = userReservations.size();
        BigDecimal totalPrice = userReservations.stream()
                .map(UserReservationHistoryResponse::getServicePrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return HistoryReservationResponse.builder()
                .title(user.getFirstName() + " Reservation History")
                .countReservation(countReservations)
                .totalPrice(totalPrice)
                .reservations(userReservations)
                .build();

    }

    // Cancelar reserva
    // solo pueden cancelar reservas propias
    @Transactional
    public Boolean cancelReservationById(Integer userId, Integer reservationId) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Reserve reserve = reserveRepository.findById(reservationId)
                .orElseThrow(() -> new ReserveNotFoundException(reservationId));

        //Integer updatedRows = repository.cancelReservationById(user.getId(), reserve.getId());

        if (!(user instanceof Customer)) {
            throw new CancelationInvalidException("Debes ser cliente para cancelar una reserva");
        }

        if (reserve.getUser().getId() != user.getId()) {
            throw new ReservationCancelledException("No puedes cancelar una reserva que no te pertenece");
        }

        if (reserve.getStatus() == Reserve.StatusReserve.CANCELLED) {
            throw new ReservationCancelledException("Ya has cancelado esta reserva");
        }

        Integer updatedRows = repository.cancelReservationById(user.getId(), reserve.getId());

        if (updatedRows == 0) {
            throw new ReservationCancelledException("Error al cancelar la reserva");
        }

        return true;

    }
    
}
