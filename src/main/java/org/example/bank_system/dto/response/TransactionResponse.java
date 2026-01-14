package org.example.bank_system.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.example.bank_system.entity.transaction.TransactionStatus;
import org.example.bank_system.entity.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(
      /*  @JsonProperty("user")
        UserResponse user,*/
        @JsonProperty("userId")
        Long userId,
        @JsonProperty("toAccountNumber")
        Long toAccountNumber,
        @JsonProperty("fromAccountNumber")
        Long fromAccountNumber,
        @JsonProperty("status")
        TransactionStatus status,
        @JsonProperty("type")
        TransactionType type,
        @JsonProperty("commission")
        BigDecimal commission,
        @JsonProperty("totalDebit")
        BigDecimal totalDebit,
        @JsonProperty("createdAt")
        LocalDateTime createdAt,
        @JsonProperty("deletedAt")
        LocalDateTime deletedAt) {
}
