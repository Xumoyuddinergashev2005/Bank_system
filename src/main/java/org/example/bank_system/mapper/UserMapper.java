package org.example.bank_system.mapper;

import org.example.bank_system.dto.response.UserResponse;
import org.example.bank_system.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
/*

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    UserResponse toUserResponse(User user);

    default String toUserFullName(User user) {
        if (user == null) return null;
        return user.getFirstName() + " " + user.getLastName();
    }
}
*/
