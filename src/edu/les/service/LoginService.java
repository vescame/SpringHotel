package edu.les.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.CredentialEntity;
import edu.les.entity.UserEntity;

@Service
public class LoginService {
	@Autowired
	private CredentialService credentialService;
	
	@Autowired
	private UserService userService;

	public UserEntity tryLogin(CredentialEntity credentialEntity) {
		CredentialEntity c = credentialService.fetch(credentialEntity);
		Optional<UserEntity> userEntity= this.userService.findByCredential(c.getCredentialId());
		if (c != null && c.equals(credentialEntity)) {
			if (userEntity.isPresent()) {
				return userEntity.get();
			}
		}
		return null;
	}

}
