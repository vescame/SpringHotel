package edu.les.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public boolean hasErrors(CredentialEntity c) throws ExceptionHandler {
		boolean result = true;
		List<String> errorFields = new ArrayList<String>();
		
		if (c == null) {
			return result;
		}
		
		if (c == null || c.getEmail().length() == 0 || c.getEmail().length() > 35) {
			errorFields.add("Email");
		}
		
		if (c == null || c.getPassword().length() == 0 || c.getPassword().length() > 35) {
			errorFields.add("Password");
		}
		
		if (errorFields.isEmpty()) {
			result = false;
		} else {
			throw new ExceptionHandler(errorFields);
		}
		
		return result;
	}
}
