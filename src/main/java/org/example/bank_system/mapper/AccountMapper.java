package org.example.bank_system.mapper;


import org.example.bank_system.dto.response.AccountResponse;
import org.example.bank_system.dto.response.UserResponse;
import org.example.bank_system.entity.account.Account;
import org.example.bank_system.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    @Mapping(target = "user", source = "user")
    AccountResponse toResponse(Account account);

    List<AccountResponse> toResponseList(List<Account> accounts);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    UserResponse userToUserResponse(User user);


}
