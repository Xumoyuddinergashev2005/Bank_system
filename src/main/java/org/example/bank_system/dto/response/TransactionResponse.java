package org.example.bank_system.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.example.bank_system.entity.transaction.TransactionStatus;
import org.example.bank_system.entity.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(
        @JsonProperty("user")
        UserResponse user,
        @JsonProperty("user_id")
        Long userId,
        @JsonProperty("to_account_number")
        Long toAccountNumber,
        @JsonProperty("from_account_number")
        Long fromAccountNumber,
        @JsonProperty("status")
        TransactionStatus status,
        @JsonProperty("type")
        TransactionType type,
        @JsonProperty("commission")
        BigDecimal commission,
        @JsonProperty("total_debit")
        BigDecimal totalDebit,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("deleted_at")
        LocalDateTime deletedAt) {
}
