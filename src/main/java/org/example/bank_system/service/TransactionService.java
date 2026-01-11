package org.example.bank_system.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bank_system.dto.request.TransferRequest;
import org.example.bank_system.dto.request.TransferRequestPermission;
import org.example.bank_system.dto.response.TransferResponse;
import org.example.bank_system.entity.account.Account;
import org.example.bank_system.entity.account.AccountStatus;
import org.example.bank_system.entity.transaction.Transaction;
import org.example.bank_system.entity.transaction.TransactionStatus;
import org.example.bank_system.entity.transaction.TransactionType;
import org.example.bank_system.entity.user.User;
import org.example.bank_system.exception.BadRequestException;
import org.example.bank_system.exception.InsufficientBalanceException;
import org.example.bank_system.exception.NotFoundException;
import org.example.bank_system.repository.AccountRepository;
import org.example.bank_system.repository.TransactionRepository;
import org.example.bank_system.repository.UserRepository;
import org.example.bank_system.util.NumberGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final NumberGenerator generator;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Long> redisTemplate;
    private static final BigDecimal COMMISSION_RATE = BigDecimal.valueOf(0.02);
    private static final Long BANK_ACCOUNT_NUMBER = 86004499L;

    @Transactional
    public TransferResponse transfer(TransferRequest request, User user) {


        Account toAccount = accountRepository
                .findByAccountNumberAndDeletedAtIsNull(request.toAccountNumber())
                .orElseThrow(() -> new NotFoundException("To account not found"));


        BigDecimal amount = request.amount();
        BigDecimal commission = amount.multiply(COMMISSION_RATE);
        BigDecimal totalDebit = amount.add(commission);

        Transaction transaction = Transaction.builder()
                .toAccount(toAccount)
                .amount(amount)
                .user(user)
                .commission(commission)
                .status(TransactionStatus.PENDING)
                .totalDebit(totalDebit)
                .pendingTime(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);

        Long code = generator.generateNumber();
        redisTemplate.opsForValue().set(user.getPhoneNumber(), code, 3, TimeUnit.MINUTES);


        return TransferResponse.builder()
                .secretKey(code)
                .toAccountNumberOwnerName(toAccount.getUser().getFirstName() + " " + toAccount.getUser().getLastName())
                .totalDebit(totalDebit)
                .status(TransactionStatus.PENDING)
                .transactionId(transaction.getId())
                .build();
    }

    @Transactional
    public void completeTransfer(TransferRequestPermission request) {


        Account fromAccount = accountRepository
                .findByAccountNumberAndDeletedAtIsNull(request.fromAccountNumber())
                .orElseThrow(() -> new NotFoundException("From account not found"));

        Long cachedCode = redisTemplate.opsForValue().get(fromAccount.getUser().getPhoneNumber());

        if (cachedCode == null || !cachedCode.equals(request.secretKey())) {
            throw new BadRequestException("Invalid or expired verification code");
        }
        Transaction transaction = transactionRepository.findById(request.transactionId())
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        if (transaction.getStatus() == TransactionStatus.FAILED || transaction.getStatus() == TransactionStatus.SUCCESS) {
            throw new BadRequestException("This transaction is already completed");
        }


        Account toAccount = accountRepository
                .findByAccountNumberAndDeletedAtIsNull(request.toAccountNumber())
                .orElseThrow(() -> new NotFoundException("To account not found"));
        Account bankAccount = accountRepository.findByAccountNumberAndDeletedAtIsNull(BANK_ACCOUNT_NUMBER)
                .orElseThrow(() -> new IllegalStateException("Bank account not found"));


        String from = String.valueOf(fromAccount.getAccountNumber());
        String to = String.valueOf(toAccount.getAccountNumber());

        if (!from.startsWith("8600") && to.startsWith("8600")) {
            throw new BadRequestException("Only internal transfers are allowed");
        }
        ;

        if (fromAccount.getBalance().compareTo(transaction.getTotalDebit()) < 0) {
            throw new InsufficientBalanceException("Not enough balance");
        }

        fromAccount.setBalance(
                fromAccount.getBalance().subtract(transaction.getTotalDebit())
        );

        toAccount.setBalance(
                toAccount.getBalance().add(transaction.getAmount())
        );

        bankAccount.setBalance(
                bankAccount.getBalance().add(transaction.getCommission())
        );

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        accountRepository.save(bankAccount);

        transaction.setFromAccount(fromAccount);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setType(TransactionType.INTERNAL_TRANSFER);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);

    }




    public void deleteTransactionById(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new NotFoundException("Transaction not found");
        }
        transactionRepository.deleteById(id);

    }

}

