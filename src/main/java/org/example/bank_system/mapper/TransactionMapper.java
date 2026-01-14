package org.example.bank_system.mapper;


import org.example.bank_system.dto.response.AccountResponse;
import org.example.bank_system.dto.response.TransactionResponse;
import org.example.bank_system.dto.response.TransferResponse;
import org.example.bank_system.dto.response.UserResponse;
import org.example.bank_system.entity.account.Account;
import org.example.bank_system.entity.transaction.Transaction;
import org.example.bank_system.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    /*@Mapping(target = "user", source = "user")*/
    @Mapping(target = "toAccountNumber", source = "toAccount.accountNumber")
    @Mapping(target = "fromAccountNumber", source = "fromAccount.accountNumber")
    @Mapping(target = "userId", source = "user.id")
    TransactionResponse toResponse(Transaction transaction);

    List<TransactionResponse> toResponseList(List<Transaction> transactions);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    UserResponse userToUserResponse(User user);


}

