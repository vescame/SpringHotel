package edu.les.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.UserEntity;

@Service
public class LoginService {
	@Autowired
	private UserService userService;

	public UserEntity login(UserEntity userEntity) {
		Optional<UserEntity> user = this.userService.fetchLogin(userEntity.getEmail(), userEntity.getPassword());
		return user.isPresent() ? user.get() : null;
	}

}
