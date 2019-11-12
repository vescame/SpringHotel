package edu.les.lab.dao;

import java.util.ArrayList;
import java.util.List;

import edu.les.entity.AddressEntity;
import edu.les.exception.ExceptionHandler;

public class AddressValidation {
	public static boolean hasErrors(AddressEntity a) throws ExceptionHandler {
		boolean result = true;
		List<String> errorFields = new ArrayList<String>();

		if (a == null) {
			return result;
		}

		if (a.getZipCode().length() != 8) {
			errorFields.add("Zip Code");
		}

		if (a.getStreet().length() == 0 || a.getStreet().length() > 45 || a.getStreet().contains("...")) {
			errorFields.add("Street");
		}

		if (a.getDistrict().length() == 0 || a.getDistrict().length() > 45 || a.getDistrict().contains("...")) {
			errorFields.add("District");
		}

		if (a.getCity().length() == 0 || a.getCity().length() > 45 || a.getCity().contains("...")) {
			errorFields.add("City");
		}

		if (a.getFederalUnit().length() == 0 || a.getFederalUnit().length() > 2 || a.getFederalUnit().contains("...")) {
			errorFields.add("FederalUnit");
		}

		if (errorFields.isEmpty()) {
			result = false;
		} else {
			throw new ExceptionHandler(errorFields);
		}
		return result;
	}
}
