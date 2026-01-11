package org.example.bank_system.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("phone_number")
        String phoneNumber,
        @JsonProperty("password")
        String password
) {
}
