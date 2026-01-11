package org.example.bank_system.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferRequest(
        @JsonProperty("to_account_number")
       // @NotNull(message = "number cannot be null")
        Long toAccountNumber,
        @JsonProperty("amount")
     //   @NotNull(message = "Amount cannot be null")
        BigDecimal amount
) {}
