package org.example.bank_system.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.example.bank_system.entity.account.AccountType;

@Builder
public record AccountCreateRequest(
        @JsonProperty(value = "type")
        @NotNull(message = "Account type cannot be null")
        AccountType type

) {
}
