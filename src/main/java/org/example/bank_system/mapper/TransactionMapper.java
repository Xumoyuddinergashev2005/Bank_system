package org.example.bank_system.mapper;


import org.example.bank_system.dto.response.TransferResponse;
import org.example.bank_system.entity.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
/*
@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TransactionMapper {

    @Mapping(target = "toAccountNumberOwnerName", source = "toAccount.user")
    @Mapping(target = "totalDebit", source = "amount")
    @Mapping(target = "secretKey")
    TransferResponse toResponse(Transaction transaction);

    List<TransferResponse> toResponseList(List<Transaction> transactions);
}
*/
