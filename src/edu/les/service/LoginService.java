package edu.les.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.CredentialEntity;

@Service
public class LoginService {
	@Autowired
	private CredentialService credentialService;

	public boolean tryLogin(CredentialEntity credentialEntity) {
		boolean result = false;
		CredentialEntity c = credentialService.fetch(credentialEntity);
		if (c != null && c.equals(credentialEntity)) {
			result = true;
		}
		return result;
	}
}
