package org.example.bank_system.specification;

import org.example.bank_system.entity.transaction.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransactionSpecification {
    public static Specification<Transaction> filterTransactions(
            String search,
            String status,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Long accountId
    ){
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();


            if(search!=null && !search.trim().isEmpty()){

                String searchPattern = "%"+search.trim().toLowerCase()+ "%";
                var searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("account").get("accountNumber")), searchPattern)
                );
                predicates = criteriaBuilder.and(predicates, searchPredicate);

            }
            if (status != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("status"), status));
            }

            if (fromDate != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            }

            if (toDate != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toDate));
            }
            if (accountId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("account").get("id"), accountId));
            }

            return predicates;
        };
    }
}


