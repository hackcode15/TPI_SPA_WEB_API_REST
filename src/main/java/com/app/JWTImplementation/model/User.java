package com.app.JWTImplementation.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false, length = 45)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    // relacion con reserva
    // Al elimiar un Usuario se borrara sus reservas asociadas
    @OneToMany(
            targetEntity = Reserve.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
            //fetch = FetchType.EAGER
    )
    private List<Reserve> reserves;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private Role role = Role.CUSTOMER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(("ROLE_" + role.name())));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;    
    }

    @Override
    public boolean isAccountNonExpired() {
       return true;
    }
    @Override
    public boolean isAccountNonLocked() {
       return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum Role {
        CUSTOMER,
        PROFESSIONAL,
        ADMIN,
        DEVELOPER
    }

    public Customer asCustomer() {
        if (this instanceof Customer) {
            return (Customer) this;
        }
        throw new IllegalStateException("El usuario no es un Customer");
    }

    public Professional asProfessional() {
        if (this instanceof Professional) {
            return (Professional) this;
        }
        throw new IllegalStateException("El usuario no es un Professional");
    }
    
}
