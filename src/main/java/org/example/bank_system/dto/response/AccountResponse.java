package org.example.bank_system.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.example.bank_system.entity.account.AccountStatus;
import org.example.bank_system.entity.account.AccountType;
import org.example.bank_system.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AccountResponse(
        @JsonProperty("account_number")
        Long accountNumber,
        @JsonProperty("user")
        UserResponse user,
        @JsonProperty("status")
        AccountStatus status,
        @JsonProperty("balance")
        BigDecimal balance,
        @JsonProperty("account_type")
        AccountType type,
        @JsonProperty("created_at")
        LocalDateTime createdAt) {
}
