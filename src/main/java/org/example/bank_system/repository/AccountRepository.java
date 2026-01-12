package org.example.bank_system.repository;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.bank_system.dto.response.AccountResponse;
import org.example.bank_system.entity.account.Account;
import org.example.bank_system.entity.account.AccountType;
import org.example.bank_system.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByUser_IdAndDeletedAtIsNullOrderByCreatedAtDesc(Long id);

    boolean existsByUserAndType(User user, AccountType type);

    Optional<Account> findByIdAndUserIdAndDeletedAtIsNull(Long id,  Long userId);

    Optional<Account> findByAccountNumberAndUser_Id(Long aLong, Long id);

    Optional<Account> findByAccountNumberAndDeletedAtIsNull(Long accountNumber);

    boolean existsByIdAndDeletedAtIsNull(Long id);
}
