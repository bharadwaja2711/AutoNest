package com.manoj.autonest.service;

import com.manoj.autonest.dto.UserDto;
import com.manoj.autonest.model.User;

public interface UserService {
	
	User save (UserDto userDto);
}
