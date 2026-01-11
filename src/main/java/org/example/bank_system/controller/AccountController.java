package org.example.bank_system.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bank_system.dto.request.AccountCreateRequest;
import org.example.bank_system.dto.response.SuccessResponse;
import org.example.bank_system.entity.account.Account;
import org.example.bank_system.entity.user.User;
import org.example.bank_system.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody @Valid AccountCreateRequest request,
                                           @AuthenticationPrincipal User user) {
        accountService.createAccount(request,user);
        return ResponseEntity.ok(SuccessResponse.ok("Account created successfully"));
    }

    @GetMapping("/all-accounts")
    public ResponseEntity<?> getAllAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.getAll(user));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id,
                                            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.getById(id, user));

    }

    @DeleteMapping("/by/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable Long id,
                                               @AuthenticationPrincipal User user) {
        accountService.deleteById(id, user);
        return ResponseEntity.ok(SuccessResponse.ok("Account deleted successfully"));
    }


}
