package com.codegym.ClimaxStoreSpring.service;

import com.codegym.ClimaxStoreSpring.dto.request.UserLoginDto;
import com.codegym.ClimaxStoreSpring.dto.request.UserRegisterDto;
import com.codegym.ClimaxStoreSpring.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityNotFoundException;

public interface UserService extends GenericService<User> {
    User findByUserName(String userName) throws EntityNotFoundException;

    boolean checkUserExist(String userName, String email, String phoneNumber);

    User registerUser(UserRegisterDto userRegisterDto);

    User loginUser(UserLoginDto userLoginDto);
}
