package org.example.bank_system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bank_system.dto.request.TransferRequest;
import org.example.bank_system.dto.request.TransferRequestPermission;
import org.example.bank_system.dto.response.SuccessResponse;
import org.example.bank_system.dto.response.TransferResponse;
import org.example.bank_system.entity.user.User;
import org.example.bank_system.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;


    @PostMapping("/transferRequest")
    @Operation(summary = "Commission rate is 2 % for each transfer")
    public ResponseEntity<?> transferToMoneyForPermission(@Valid @RequestBody TransferRequest transferRequest,
                                                          @AuthenticationPrincipal User user) {
       TransferResponse response = transactionService.transfer(transferRequest, user);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/transferResponse")
    public ResponseEntity<?> completeTransfer(
            @Valid @RequestBody TransferRequestPermission transferRequestPermission
    ){
        transactionService.completeTransfer(transferRequestPermission);
        return ResponseEntity.ok(SuccessResponse.ok("Money transferred successfully"));
    }



    @DeleteMapping("/by/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransactionById(id);
        return ResponseEntity.ok(SuccessResponse.ok("Transaction deleted successfully"));
    }




}
