package org.example.bank_system.dto.response;

import lombok.Builder;
import org.example.bank_system.entity.transaction.TransactionStatus;

import java.math.BigDecimal;

@Builder
public record TransferResponse(
        String toAccountNumberOwnerName,
        BigDecimal totalDebit,
        Long secretKey,
        TransactionStatus status,
        Long transactionId


) {
}
