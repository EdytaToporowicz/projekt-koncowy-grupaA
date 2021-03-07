package com.example.demo.service;

import com.example.demo.domain.model.AccountStatus;
import com.example.demo.domain.model.AccountType;
import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.UserDto;
import com.example.demo.service.mappers.UserMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(user -> userMapper.map(user)).collect(Collectors.toList());
    }

    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        user.setCreated(LocalDate.now());
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setAccountType(AccountType.STANDARD);
        return userMapper.map(userRepository.save(user));

    }


    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    public UserDto updateUser(UserDto userDto, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " cannot be found"));
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAccountName(userDto.getAccountName());
        user.setRegion(userDto.getRegion());
        user.setCity(userDto.getCity());
        user.setStreet(user.getStreet());
        user.setHouseNumber(userDto.getHouseNumber());
        user.setPostalCode(userDto.getPostalCode());
        user.setCreated(LocalDate.now());
        if (userDto.getAccountStatus() != null) {
            user.setAccountStatus(AccountStatus.valueOf(userDto.getAccountStatus().toUpperCase()));
        }
        if (userDto.getAccountType() != null) {
            user.setAccountType(AccountType.valueOf(userDto.getAccountType().toUpperCase()));
        }

        return userMapper.map(userRepository.save(user));
    }

    public UserDto findUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " cannot be found"));
        return userMapper.map(user);
    }
}
