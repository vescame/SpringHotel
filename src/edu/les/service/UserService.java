package edu.les.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressService addressService;

	@Autowired
	private CredentialService credentialService;

	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public boolean add(UserEntity userEntity) throws ExceptionHandler {
		boolean result = false;
		this.userRepository.save(userEntity);
		result = true;
		return result;
	}

	public Optional<UserEntity> findByCpf(String cpf) {
		return this.userRepository.findById(cpf);
	}

	public boolean updateUser(UserEntity userEntity) throws ExceptionHandler {
		boolean result = false;
		if (hasErrors(userEntity)) {
			return result;
		}
		result = this.userRepository.save(userEntity) != null;
		return result;
	}

	public Iterable<UserEntity> fetchAll() {
		return this.userRepository.findAll();
	}

	public boolean hasErrors(UserEntity u) throws ExceptionHandler {
		boolean result = true;
		List<String> errorFields = new ArrayList<String>();

		if (u == null) {
			return result;
		}

		if (u.getUserCpf().length() == 0 || u.getUserCpf().length() != 11) {
			errorFields.add("CPF");
		}

		if (u.getUsername().length() == 0 || u.getUsername().length() > 35) {
			errorFields.add("Name");
		}

		if (u.getUserRole() == null) {
			errorFields.add("User Role");
		}

		if (u.getHouseNumber() == 0) {
			errorFields.add("House Number");
		}

		if (u.getTelephoneNumber().length() > 15) {
			errorFields.add("Telephone");
		}

		if (u.getCelphoneNumber().length() > 15) {
			errorFields.add("Celphone");
		}

		if (u.getDateOfBirth() != null) {
			if (!this.isBirthdayValid(u.getDateOfBirth())) {
				errorFields.add("Birthday");
			}
		}

		// TODO: check if is needed to validate user status
		// because it is hardcoded at the register
		
		try {
			// catch exception gotten inside those classes
			// no need to receive boolean results
			this.addressService.hasErrors(u.getAddressEntity());
			this.credentialService.hasErrors(u.getCredentialEntity());
		} catch (ExceptionHandler h) {
			errorFields.addAll(h.getErrors());
		}

		if (errorFields.isEmpty()) {
			result = false;
		} else {
			throw new ExceptionHandler(errorFields);
		}

		return result;
	}

	public boolean isBirthdayValid(Date date) {
		this.sdf.setLenient(false);
		try {
			String dateStr = this.sdf.format(date);
			String[] yMd = dateStr.split("\\-");
			if (yMd[0].length() > 4) {
				throw new ParseException("The year has more than 4 chars", 1);
			}
			this.sdf.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
