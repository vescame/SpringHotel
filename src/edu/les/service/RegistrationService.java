package edu.les.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.CredentialEntity;
import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.CredentialRepository;
import edu.les.repository.UserRepository;

@Service
public class RegistrationService {
	@Autowired
	public UserRepository userRepository;

	@Autowired
	public CredentialRepository credentialRepository;

	public boolean newUser(UserEntity userEntity, CredentialEntity credentialEntity) throws ExceptionHandler {
		boolean result = false;
		try {
			userRepository.save(userEntity);
			credentialRepository.save(credentialEntity);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionHandler("Column user_cpf too long");
		}
		return result;
	}
}
