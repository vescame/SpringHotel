package edu.les.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.CredentialEntity;
import edu.les.repository.CredentialRepository;

@Service
public class LoginService {
	@Autowired
	public CredentialRepository credentialRepository;

	public boolean tryLogin(CredentialEntity credentialEntity) {
		boolean result = false;
		CredentialEntity c = this.credentialRepository.findLoginAndPassword(credentialEntity.getEmail(),
				credentialEntity.getPassword());
		if (c.equals(credentialEntity)) {
			result = true;
		}
		return result;
	}
}
