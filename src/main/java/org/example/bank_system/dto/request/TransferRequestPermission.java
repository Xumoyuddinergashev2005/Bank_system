package org.example.bank_system.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferRequestPermission(
        @JsonProperty("transaction_id")
        @NotNull(message = "Transaction_Id cannot be null")
        Long transactionId,
        @JsonProperty("to_account_number")
        @NotNull(message = "to_account_number cannot be null")
        Long toAccountNumber,
        @JsonProperty("from_account_number")
        @NotNull(message = "from_account_number cannot be null")
        Long fromAccountNumber,
        @JsonProperty("secret_key")
        @NotNull(message = "secret_key cannot be null")
        Long secretKey
) {
}
