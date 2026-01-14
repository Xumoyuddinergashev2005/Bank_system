package org.example.bank_system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bank_system.dto.request.TransactionFilterRequest;
import org.example.bank_system.dto.request.TransferRequest;
import org.example.bank_system.dto.request.TransferRequestPermission;
import org.example.bank_system.dto.response.SuccessResponse;
import org.example.bank_system.dto.response.TransferResponse;
import org.example.bank_system.entity.transaction.TransactionStatus;
import org.example.bank_system.entity.transaction.TransactionType;
import org.example.bank_system.entity.user.User;
import org.example.bank_system.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;


    @PostMapping("/transferPreparing")
    @Operation(summary = "Commission rate is 2 % for each transfer")
    public ResponseEntity<?> transferToMoneyForPermission(@Valid @RequestBody TransferRequest transferRequest,
                                                          @AuthenticationPrincipal User user) {
        TransferResponse response = transactionService.transfer(transferRequest, user);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/transferConfirm")
    public ResponseEntity<?> completeTransfer(
            @Valid @RequestBody TransferRequestPermission transferRequestPermission,
            @AuthenticationPrincipal User user
    ) {
        transactionService.completeTransfer(transferRequestPermission, user);
        return ResponseEntity.ok(SuccessResponse.ok("Money transferred successfully"));
    }


    @DeleteMapping("/by/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api only for Admins")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
        return ResponseEntity.ok(SuccessResponse.ok("Transaction deleted successfully"));
    }

    @GetMapping("/all-transactions")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api only for Admins")
    public ResponseEntity<?> getAllTransactions(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) BigDecimal minTotalDebit,
            @RequestParam(required = false) BigDecimal maxTotalDebit,
            @RequestParam(required = false) BigDecimal minCommission,
            @RequestParam(required = false) BigDecimal maxCommission,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) Long userId
    ) {
        TransactionFilterRequest filterRequest = TransactionFilterRequest.builder()
                .search(search)
                .status(status)
                .type(type)
                .minTotalDebit(minTotalDebit)
                .maxTotalDebit(maxTotalDebit)
                .minCommission(minCommission)
                .maxCommission(maxCommission)
                .fromDate(fromDate!=null ? LocalDateTime.parse(fromDate) : null)
                .toDate(toDate != null ? LocalDateTime.parse(toDate) : null)
                .userId(userId)
                .build();

        return ResponseEntity.ok(transactionService.getAllTransactions(filterRequest));
    }

    @GetMapping("/user-transactions")
    public ResponseEntity<?> getUserTransactions(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) BigDecimal minTotalDebit,
            @RequestParam(required = false) BigDecimal maxTotalDebit,
            @RequestParam(required = false) BigDecimal minCommission,
            @RequestParam(required = false) BigDecimal maxCommission,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @AuthenticationPrincipal User user) {
        TransactionFilterRequest filterRequest = TransactionFilterRequest.builder()
                .search(search)
                .status(status)
                .type(type)
                .minTotalDebit(minTotalDebit)
                .maxTotalDebit(maxTotalDebit)
                .minCommission(minCommission)
                .maxCommission(maxCommission)
                .fromDate(fromDate!=null ? LocalDateTime.parse(fromDate) : null)
                .toDate(toDate != null ? LocalDateTime.parse(toDate) : null)
                .userId(user.getId())
                .build();


        return ResponseEntity.ok(transactionService.getAllTransactions(filterRequest));


    }

}




