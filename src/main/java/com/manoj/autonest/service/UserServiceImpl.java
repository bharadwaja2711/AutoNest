package com.manoj.autonest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manoj.autonest.dto.UserDto;
import com.manoj.autonest.model.User;
import com.manoj.autonest.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(UserDto userDto) {
        User user = new User(
            userDto.getEmail(),
            passwordEncoder.encode(userDto.getPassword()),
            userDto.getRole(),
            userDto.getSurname(),
            userDto.getGivenname(),
            userDto.getDob(),
            userDto.getMobno(),
            userDto.getGender(),
            userDto.getCity(),
            userDto.getState(),
            userDto.getCountry(),
            userDto.getPincode()
        );
        return userRepository.save(user);
    }

}
