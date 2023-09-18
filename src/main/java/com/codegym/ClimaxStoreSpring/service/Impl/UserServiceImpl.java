package com.codegym.ClimaxStoreSpring.service.Impl;

import com.codegym.ClimaxStoreSpring.dto.request.UserLoginDto;
import com.codegym.ClimaxStoreSpring.dto.request.UserRegisterDto;
import com.codegym.ClimaxStoreSpring.entity.user.Role;
import com.codegym.ClimaxStoreSpring.entity.user.User;
import com.codegym.ClimaxStoreSpring.repository.RoleRepository;
import com.codegym.ClimaxStoreSpring.repository.UserRepository;
import com.codegym.ClimaxStoreSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) throws EntityNotFoundException {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            user.setId(id);
            return userRepository.save(user);
        }
    }

    @Override
    public void remove(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            userRepository.delete(user.get());
        }
    }

    @Override
    public User findByUserName(String userName) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            return user.get();
        }
    }

    @Override
    public boolean checkUserExist(String userName, String email, String phoneNumber) {
        boolean checkUserName = userRepository.existsUserByUserName(userName);
        boolean checkEmail = userRepository.existsUserByEmail(email);
        boolean checkPhoneNumber = userRepository.existsUserByPhoneNumber(phoneNumber);
        return (checkUserName || checkEmail || checkPhoneNumber);
    }

    @Override
    public User registerUser(UserRegisterDto userRegisterDto) {
        if (checkUserExist(userRegisterDto.getUserName(),
                userRegisterDto.getEmail(),
                userRegisterDto.getPhoneNumber())) {
            return null;
        }
        User user = User.builder()
                .userName(userRegisterDto.getUserName())
                .email(userRegisterDto.getEmail())
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .createdDate(new Date())
                .role(roleRepository.findRoleByRoleName("ROLE_CUSTOMER").get())
                .deleted(false)
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .build();
        return userRepository.save(user);
    }

    @Override
    public User loginUser(UserLoginDto userLoginDto) {
        User user = null;
        String loginString = userLoginDto.getUserName();
        String pass = userLoginDto.getPassword();
        Optional<User> withUserName = userRepository.findByUserName(loginString);
        Optional<User> withEmail = userRepository.findByEmail(loginString);
        Optional<User> withPhoneNumber = userRepository.findByPhoneNumber(loginString);
        if (withUserName.isPresent()) {
            user = withUserName.get();
        } else if (withEmail.isPresent()) {
            user = withEmail.get();
        } else if (withPhoneNumber.isPresent()) {
            user = withPhoneNumber.get();
        }
        if (user != null && passwordEncoder.matches(pass, user.getPassword())) {
            return user;
        }
        return null;
    }
}
