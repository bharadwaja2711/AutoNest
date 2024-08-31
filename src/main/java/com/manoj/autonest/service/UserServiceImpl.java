package com.manoj.autonest.service;

import java.util.List;

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

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public User updateUserRole(Long userId, String role) {
	    User user = userRepository.findById(userId).orElse(null);
	    if (user != null) {
	        user.setRole(role);
	        return userRepository.save(user);
	    }
	    return null;
	}
	
	@Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email); // Implemented method to fetch user by email
    }
	
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}
