package org.example.bank_system.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.example.bank_system.entity.user.KycStatus;
import org.example.bank_system.entity.user.Role;
import org.example.bank_system.entity.user.UserStatus;

import java.time.LocalDateTime;

@Builder
public record UserProfileResponse(
        @JsonProperty("userId")
        Long id,
        @JsonProperty("firstName")
        String firstName,
        @JsonProperty("lastName")
        String lastName,
        @JsonProperty("phoneNumber")
        String phoneNumber,
        @JsonProperty("role")
        String role,
        @JsonProperty("status")
        String status,
        @JsonProperty("password")
        String password,
        @JsonProperty("kycStatus")
        KycStatus kycStatus,
        @JsonProperty("is_active")
        Boolean is_active,
        @JsonProperty("createdAt")
        LocalDateTime createdAt

) {
}
