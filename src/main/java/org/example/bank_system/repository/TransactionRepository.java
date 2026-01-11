package org.example.bank_system.repository;


import lombok.NonNull;
import org.example.bank_system.entity.transaction.Transaction;
import org.example.bank_system.entity.transaction.TransactionStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> , JpaSpecificationExecutor<Transaction> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"account"} )
    List<Transaction> findAll(@NonNull Specification<Transaction> spec);

    @EntityGraph(attributePaths = {"user"} )
    Optional<Transaction> findByIdAndFromAccount_User_Id(Long transactionId, Long userId);

    List<Transaction> findAllByStatusAndDeletedAtIsNullAndPendingTimeBefore(
            TransactionStatus status,
            LocalDateTime time
    );




}
