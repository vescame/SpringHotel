package edu.les.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public void add(UserEntity userEntity) throws ExceptionHandler {
		if (!this.hasErrors(userEntity)) {
			if (this.userRepository.findById(userEntity.getUserCpf()).isPresent()) {
				throw new ExceptionHandler("User with CPF [" + userEntity.getUserCpf() + "] already exists");
			}
			this.userRepository.save(userEntity);
		}
	}

	public void update(UserEntity userEntityUpdated) throws ExceptionHandler {
		if (!this.hasErrors(userEntityUpdated)) {
			this.userRepository.save(userEntityUpdated);
		}
	}

	public UserEntity findByCpf(String cpf) throws ExceptionHandler {
		Optional<UserEntity> u = this.userRepository.findById(cpf);
		if (!u.isPresent()) {
			throw new ExceptionHandler("User not found!");
		}
		return u.get();
	}

	public void delete(String userCpf) throws ExceptionHandler {
		try {
			this.userRepository.deleteById(userCpf);
		} catch (EmptyResultDataAccessException e) {
			throw new ExceptionHandler("User not found!");
		}
	}

	public Iterable<UserEntity> fetchAll() {
		return this.userRepository.findAll();
	}

	public Optional<UserEntity> fetchLogin(String email, String password) {
		return this.userRepository.findByCredential(email, password);
	}

	public boolean hasErrors(UserEntity u) throws ExceptionHandler {
		boolean result = true;
		List<String> errorFields = new ArrayList<String>();

		if (!u.getUserCpf().matches("[0-9]+")) {
			throw new ExceptionHandler("Invalid CPF");
		}
		
		if (u.getUserCpf().length() != 11) {
			errorFields.add("CPF");
		}

		if (u.getUsername().length() == 0 || u.getUsername().length() > 35) {
			errorFields.add("Name");
		}

		if (u.getUserRole() == null || u.getUserRole().isEmpty()) {
			errorFields.add("User Role");
		}

		if (u.getHouseNumber() == 0) {
			errorFields.add("House Number");
		}

		if (u.getTelephoneNumber() != null) {
			if (u.getTelephoneNumber().length() > 15) {
				errorFields.add("Telephone");
			}
		}

		if (u.getCelphoneNumber() != null) {
			if (u.getCelphoneNumber().length() > 15) {
				errorFields.add("Celphone");
			}
		}

		if (u.getDateOfBirth() != null) {
			if (!this.isBirthdayValid(u.getDateOfBirth())) {
				errorFields.add("Birthday");
			}
		}

		if (u.getEmail() != null) {
			if (u.getEmail().length() == 0 || u.getEmail().length() > 35) {
				errorFields.add("E-mail");
			}
		}

		if (u.getPassword() != null) {
			if (u.getPassword().length() == 0 || u.getPassword().length() < 6 || u.getPassword().length() > 35) {
				errorFields.add("Password");
			}
		}

		// TODO: check if is needed to validate user status
		// because it is hardcoded at the register

		try {
			// catch exception gotten inside those classes
			// no need to receive boolean results
			this.addressService.hasErrors(u.getAddressEntity());
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
