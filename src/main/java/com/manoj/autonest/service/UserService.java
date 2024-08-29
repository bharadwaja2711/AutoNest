package com.manoj.autonest.service;

import java.util.List;

import com.manoj.autonest.dto.UserDto;
import com.manoj.autonest.model.User;

public interface UserService {
	
	User save (UserDto userDto);
	
	List<User> getAllUsers();
	
	User updateUserRole(Long userId, String role);

}
