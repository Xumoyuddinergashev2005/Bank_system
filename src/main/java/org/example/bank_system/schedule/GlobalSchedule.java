package org.example.bank_system.schedule;

import lombok.RequiredArgsConstructor;
import org.example.bank_system.entity.transaction.Transaction;
import org.example.bank_system.entity.transaction.TransactionStatus;
import org.example.bank_system.repository.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GlobalSchedule {

    private final TransactionRepository transactionRepository;



    @Scheduled(fixedDelay = 20_000, initialDelay = 20_000) // every
    public void TransactionDeleteSchedular(){


        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        List<Transaction> expiredTransactions = transactionRepository.findAllByStatusAndDeletedAtIsNullAndPendingTimeBefore(TransactionStatus.PENDING,  fiveMinutesAgo);

        for  (Transaction transaction : expiredTransactions){


            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

        }

    }
}
