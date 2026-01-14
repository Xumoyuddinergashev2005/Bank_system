package org.example.bank_system.mapper;

import org.example.bank_system.dto.response.UserProfileResponse;
import org.example.bank_system.dto.response.UserResponse;
import org.example.bank_system.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role")
    @Mapping(target = "status", source = "status")
    UserProfileResponse toUserResponse(User user);

   List<UserProfileResponse> toUserResponseList(List<User> users);
}

