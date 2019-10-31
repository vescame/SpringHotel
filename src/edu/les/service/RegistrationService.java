package edu.les.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;

@Service
public class RegistrationService {
	@Autowired
	public UserService userService;

	public boolean hasErrors(UserEntity userEntity) throws ExceptionHandler {
		boolean result = true;
		List<String> errorFields = new ArrayList<String>();
		try {
			if (!userService.hasErrors(userEntity)) {
				result = false;
			}
		} catch (Exception e) {
			throw new ExceptionHandler(errorFields);
		}
		return result;
	}

	public void registerUser(UserEntity userEntity) throws ExceptionHandler {
		// como a entidade esta mapeada para cascade ALL
		// nao e necessario explicitamente salvar as dependencias
		// AddressEntity e CredentialEntity
		// o hibernate faz isso para nos
		boolean hasAny = this.hasErrors(userEntity);
		if (!hasAny) {
			this.userService.add(userEntity);
		} else {
			return;
		}
	}
}
