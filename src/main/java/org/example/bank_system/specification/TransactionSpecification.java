package org.example.bank_system.specification;


import org.example.bank_system.entity.transaction.Transaction;

import org.example.bank_system.entity.transaction.TransactionStatus;
import org.example.bank_system.entity.transaction.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
public class TransactionSpecification {

    public static Specification<Transaction> filterTransactions(
            String search,
            TransactionStatus status,
            TransactionType type,
            BigDecimal minTotalDebit,
            BigDecimal maxTotalDebit,
            BigDecimal minCommission,
            BigDecimal maxCommission,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Long userId
    ) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();




            if(search!=null && !search.trim().isEmpty()){

                String searchPattern = "%"+search.trim().toLowerCase()+ "%";
                var searchPredicate = criteriaBuilder.or(
                       /* criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("fromAccount").get("accountNumber").as(String.class)),
                                searchPattern
                        ),
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("toAccount").get("accountNumber").as(String.class)),
                                searchPattern
                        ),*/
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("user").get("phoneNumber")),
                                searchPattern
                        ),
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("user").get("firstName")),
                                searchPattern
                        ),
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("user").get("lastName")),
                                searchPattern
                        )
                );

                predicates = criteriaBuilder.and(predicates, searchPredicate);
            }

            // STATUS
            if (status != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("status"), status));
            }

            // TYPE
            if (type != null) {
                predicates =criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("type"), type));
            }

            // DATE RANGE
            if (fromDate != null) {
                predicates =criteriaBuilder.and(predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            }

            if (toDate != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toDate));
            }

            // TOTAL DEBIT
            if (minTotalDebit != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("totalDebit"), minTotalDebit));
            }

            if (maxTotalDebit != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("totalDebit"), maxTotalDebit));
            }

            // COMMISSION
            if (minCommission != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("commission"), minCommission));
            }

            if (maxCommission != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("commission"), maxCommission));
            }
            if (userId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("user").get("id"), userId));
            }



            // NOT DELETED
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.isNull(root.get("deletedAt")));

            return predicates;
        };
    }
}
