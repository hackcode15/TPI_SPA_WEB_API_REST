package com.app.JWTImplementation.repository;

import com.app.JWTImplementation.dto.projection.CustomerProjection;
import com.app.JWTImplementation.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("""
    SELECT
        u.id as id,
        u.email as email,
        u.username as username,
        u.password as password,
        u.firstName as firstName,
        u.lastName as LastName,
        c.phone as phone,
        c.birthdate as birthdate,
        u.role as role
    FROM Customer c
    JOIN User u ON c.id = u.id
    """)
    List<CustomerProjection> findAllCustomers();

    @Query("""
    SELECT
        u.id as id,
        u.email as email,
        u.username as username,
        u.password as password,
        u.firstName as firstName,
        u.lastName as LastName,
        c.phone as phone,
        c.birthdate as birthdate,
        u.role as role
    FROM Customer c
    JOIN User u ON c.id = u.id
    WHERE c.id = :id
    """)
    Optional<CustomerProjection> findCustomerById(@Param("id") Integer id);


}
