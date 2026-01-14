package org.example.bank_system.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.example.bank_system.entity.transaction.TransactionStatus;

import java.math.BigDecimal;

@Builder
public record TransferResponse(
        @JsonProperty("toAccountNumberOwnerName")
        String toAccountNumberOwnerName,
        @JsonProperty("totalDebit")
        BigDecimal totalDebit,
        @JsonProperty("secretKey")
        Long secretKey,
        @JsonProperty("status")
        TransactionStatus status,
        @JsonProperty("transactionId")
        Long transactionId


) {
}
