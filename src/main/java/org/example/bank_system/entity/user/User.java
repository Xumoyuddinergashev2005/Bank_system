package org.example.bank_system.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @Column(name = "kyc_status")
    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "avatar_url")
    public String avatarUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean is_active;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


   /* @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isEnabled() {
        return is_active;
    }
}