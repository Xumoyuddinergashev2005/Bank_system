package org.example.bank_system.service;


import lombok.RequiredArgsConstructor;
import org.example.bank_system.dto.request.AccountCreateRequest;
import org.example.bank_system.dto.response.AccountResponse;
import org.example.bank_system.entity.account.Account;
import org.example.bank_system.entity.account.AccountStatus;
import org.example.bank_system.entity.user.KycStatus;
import org.example.bank_system.entity.user.User;
import org.example.bank_system.exception.ConflictException;
import org.example.bank_system.exception.KycNotVerifiedException;
import org.example.bank_system.exception.NotFoundException;
import org.example.bank_system.mapper.AccountMapper;
import org.example.bank_system.repository.AccountRepository;
import org.example.bank_system.repository.UserRepository;
import org.example.bank_system.util.AccountNumberGenerator;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private static final String BANK_PREFIX = "8600";
    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final AccountMapper  accountMapper;


    public void createAccount(AccountCreateRequest request, User user) {
        if (!user.getKycStatus().equals(KycStatus.VERIFIED)) {
            throw new KycNotVerifiedException("User KYC status is not VERIFIED");
        }
        if(accountRepository.existsByUserAndType(user, request.type())) {
            throw new ConflictException("You already have this type of account");
        }

        Long accountNumber = accountNumberGenerator.generateAccountNumber();

        Account account = Account.builder()
                .user(user)
                .accountNumber(Long.parseLong(BANK_PREFIX+accountNumber))
                .status(AccountStatus.ACTIVE)
                .type(request.type())
                .balance(BigDecimal.ZERO)
                .build();
        accountRepository.save(account);


    }

    public List<AccountResponse> getAll(User user) {
       List<Account> responses = accountRepository.findAllByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(user.getId());
        return accountMapper.toResponseList(responses);

    }

    public AccountResponse getById(Long id, User user) {

        Account account = accountRepository.findByIdAndUserIdAndDeletedAtIsNull(id, user.getId()).orElseThrow(() -> new NotFoundException("Account not found"));
        return accountMapper.toResponse(account);
    }

    public void deleteById(Long id, User user) {
        Account account = accountRepository.findByIdAndUserIdAndDeletedAtIsNull(id, user.getId()).orElseThrow(() -> new NotFoundException("Account not found"));
        account.setDeletedAt(LocalDateTime.now());
        account.setStatus(AccountStatus.INACTIVE);
        accountRepository.save(account);

    }
}
