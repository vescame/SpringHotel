package edu.les.lab.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;

public class UserValidation {
	public static boolean hasErrors(UserEntity u) throws ExceptionHandler {
		boolean result = true;
		List<String> errorFields = new ArrayList<String>();

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
			if (!UserValidation.isBirthdayValid(u.getDateOfBirth())) {
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
			AddressValidation.hasErrors(u.getAddressEntity());
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

	public static boolean isBirthdayValid(Date date) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			String dateStr = sdf.format(date);
			String[] yMd = dateStr.split("\\-");
			if (yMd[0].length() > 4) {
				throw new ParseException("The year has more than 4 chars", 1);
			}
			sdf.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
