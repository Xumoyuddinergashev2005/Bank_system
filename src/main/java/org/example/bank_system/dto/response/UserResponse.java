package org.example.bank_system.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("fullName")
        String fullName,
        @JsonProperty("phoneNumber")
        String phoneNumber


) {
}
