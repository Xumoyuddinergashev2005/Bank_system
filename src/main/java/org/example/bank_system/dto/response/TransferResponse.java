package org.example.bank_system.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.example.bank_system.entity.transaction.TransactionStatus;

import java.math.BigDecimal;

@Builder
public record TransferResponse(
        @JsonProperty("to_account_number_owner_name")
        String toAccountNumberOwnerName,
        @JsonProperty("total_debit")
        BigDecimal totalDebit,
        @JsonProperty("secret_key")
        Long secretKey,
        @JsonProperty("status")
        TransactionStatus status,
        @JsonProperty("transaction_id")
        Long transactionId


) {
}
