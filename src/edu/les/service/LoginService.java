package edu.les.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.AddressEntity;
import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;

@Service
public class LoginService {
	@Autowired
	private UserService userService;

	public UserEntity login(UserEntity userEntity) throws ExceptionHandler {
		if (this.isAdmin(userEntity.getEmail(), userEntity.getPassword())) {
			return this.defaultAdminUser();
		}
		Optional<UserEntity> user = this.userService.fetchLogin(userEntity.getEmail(), userEntity.getPassword());
		if (!user.isPresent()) {
			throw new ExceptionHandler("Invalid credentials!");
		}
		return user.get();
	}
	
	private boolean isAdmin(String email, String password) {
		UserEntity admin = this.defaultAdminUser();
		if (email.equals(admin.getEmail()) && password.equals(admin.getPassword())) {
			return true;
		}
		return false;
	}
	
	private UserEntity defaultAdminUser() {
		AddressEntity address = new AddressEntity();
        address.setZipCode("01001000");
        address.setStreet("Praça da Sé");
        address.setDistrict("Sé");
        address.setCity("São Paulo");
        address.setFederalUnit("SP");

        UserEntity admin = new UserEntity();
        admin.setUserCpf("00000000000");
        admin.setUsername("Administrator");
        admin.setUserRole("ADMINISTRATOR");
        admin.setHouseNumber(1);
        admin.setDateOfBirth(new Date());
        admin.setStatus(new StringBuilder("A").charAt(0));
        admin.setEmail("admin@spring.hotel");
        admin.setPassword("#springhotel");
        admin.setAddressEntity(address);
        
        return admin;
	}
}
