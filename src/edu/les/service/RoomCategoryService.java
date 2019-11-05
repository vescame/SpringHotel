package edu.les.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.RoomCategoryEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.RoomCategoryRepository;

@Service
public class RoomCategoryService {
	@Autowired
	private RoomCategoryRepository roomCategoryRepository;
	
	public Iterable<RoomCategoryEntity> fetchAll() {
		return this.roomCategoryRepository.findAll();
	}
	
	public void addOrUpdate(RoomCategoryEntity roomCategoryEntity) throws ExceptionHandler {
		if (!this.hasErrors(roomCategoryEntity)) {
			this.roomCategoryRepository.save(roomCategoryEntity);
		}
	}
	
	public boolean hasErrors(RoomCategoryEntity c) throws ExceptionHandler {
		boolean result = true;
		List<String> fieldsWithError = new ArrayList<String>();
		if (c.getCategory().length() == 0 || c.getCategory().length() > 25) {
			fieldsWithError.add("Category");
		}
		
		// TODO: check if need any value validation
		
		if (c.getMaxPeople() > 9) {
			fieldsWithError.add("Number of People");
		}
		
		if (fieldsWithError.isEmpty()) {
			result = false;
		} else {
			throw new ExceptionHandler(fieldsWithError);
		}
		
		return result;
	}

	public Optional<RoomCategoryEntity> findById(int id) throws ExceptionHandler {
		return this.roomCategoryRepository.findById(id);
	}

	public RoomCategoryEntity fetchByCategory(String category) {
		return this.roomCategoryRepository.fetchByCategory(category);
	}
}
