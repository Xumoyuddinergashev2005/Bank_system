package org.example.bank_system.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record TransferRequest(
        @NotNull(message = "to_account_number cannot be null")
        @JsonProperty("to_account_number")
        Long toAccountNumber,

        @NotNull(message = "Amount cannot be null")
        @JsonProperty("amount")
        BigDecimal amount
) {}
