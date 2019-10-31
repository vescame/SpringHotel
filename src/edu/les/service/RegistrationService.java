package edu.les.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;

@Service
public class RegistrationService {
	@Autowired
	public UserService userService;

	public void registerUser(UserEntity userEntity) throws ExceptionHandler {
		// como a entidade esta mapeada para cascade ALL
		// nao e necessario explicitamente salvar as dependencias
		// AddressEntity e CredentialEntity
		// o hibernate faz isso para nos
		this.userService.add(userEntity);
	}
}
