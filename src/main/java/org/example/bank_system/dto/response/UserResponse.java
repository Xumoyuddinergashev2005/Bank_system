package org.example.bank_system.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("full_name")
        String fullName,
        @JsonProperty("phone_number")
        String phoneNumber


) {
}
