package org.example.bank_system.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.example.bank_system.entity.transaction.TransactionStatus;
import org.example.bank_system.entity.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionFilterRequest(
        @JsonProperty("search")
        String search,
        @JsonProperty("status")
        TransactionStatus status,
        @JsonProperty("type")
        TransactionType type,
        @JsonProperty("min_total_debit")
        BigDecimal minTotalDebit,
        @JsonProperty("max_total_debit")
        BigDecimal maxTotalDebit,
        @JsonProperty("min_commission")
        BigDecimal minCommission,
        @JsonProperty("max_commission")
        BigDecimal maxCommission,
        @JsonProperty("user_id")
        Long userId,
        @JsonProperty("from_date")
        LocalDateTime fromDate,
        @JsonProperty("to_date")
        LocalDateTime toDate
) {
}
