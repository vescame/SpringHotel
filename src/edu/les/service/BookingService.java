package edu.les.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.BookingEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.BookingRepository;

@Service
public class BookingService{
	@Autowired
	public BookingRepository bookingRepository;
	
	public boolean add(BookingEntity bookingEntity) throws ExceptionHandler{
		boolean result = false;
		try {
			this.bookingRepository.save(bookingEntity);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionHandler();
		}
		return result;
	}
}