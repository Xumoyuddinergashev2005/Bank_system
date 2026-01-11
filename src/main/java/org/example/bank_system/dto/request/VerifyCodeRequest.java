package org.example.bank_system.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record VerifyCodeRequest(
        @JsonProperty("phone_number")
        String phoneNumber,
        @JsonProperty("code")
        Long code
) {
}