package edu.les.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	public UserRepository userRepository;

	public boolean add(UserEntity userEntity) throws ExceptionHandler {
		boolean result = false;
		try {
			this.userRepository.save(userEntity);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionHandler();
		}
		return result;
	}
}
