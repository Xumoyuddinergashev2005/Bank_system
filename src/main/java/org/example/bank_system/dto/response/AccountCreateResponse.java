package org.example.bank_system.dto.response;


import lombok.Builder;

@Builder
public record AccountCreateResponse(
        Long accountId,
        Long accountNumber
) {
}
