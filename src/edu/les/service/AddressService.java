package edu.les.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.AddressEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.AddressRepository;

@Service
public class AddressService {
	@Autowired
	public AddressRepository addressRepository;

	public boolean add(AddressEntity addressEntity) throws ExceptionHandler {
		boolean result = false;
		try {
			this.addressRepository.save(addressEntity);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionHandler();
		}
		return result;
	}

	public boolean hasErrors(AddressEntity a) throws ExceptionHandler {
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
