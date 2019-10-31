package edu.les.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.CredentialEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.CredentialRepository;

@Service
public class CredentialService {
	@Autowired
	public CredentialRepository credentialRepository;

	public CredentialEntity fetch(CredentialEntity credentialEntity) {
		CredentialEntity c = this.credentialRepository.fetchCredential(credentialEntity.getEmail(),
				credentialEntity.getPassword());
		return c;
	}

	public boolean add(CredentialEntity credentialEntity) throws ExceptionHandler {
		boolean result = false;
		try {
			this.credentialRepository.save(credentialEntity);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionHandler();
		}
		return result;
	}
}
