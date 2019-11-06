package edu.les.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.BookingEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.BookingRepository;

@Service
public class BookingService{
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoomService roomService;
	
	public void add(BookingEntity bookingEntity) throws ExceptionHandler{
		if (!this.hasErrors(bookingEntity)) {
			this.bookingRepository.save(bookingEntity);
		}
	}

	private boolean hasErrors(BookingEntity b) throws ExceptionHandler {
		boolean result = true;
		List<String> errorFields = new ArrayList<>();
		if (b.getBookingId() <= 0) {
			errorFields.add("Booking ID");
		}
		
		if (b.getCheckIn() == null) {
			errorFields.add("Check In Date");
		}
		
		// checkout < checkin : < 0
		// checkout > checkin : > 0
		// checkout == checkin : == 0
		// checkout can be null
		if (b.getCheckOut() != null && b.getCheckOut().compareTo(b.getCheckIn()) < 0) {
			errorFields.add("Check Out Date");
		}
		
		if (b.getStatus() == null) {
			errorFields.add("Status");
		}
		
		try {
			this.userService.hasErrors(b.getUserEntity());
			this.roomService.hasErrors(b.getRoomEntity());
		} catch (ExceptionHandler e) {
			errorFields.addAll(e.getErrors());
		}
		
		if (errorFields.isEmpty()) {
			result = false;
		} else {
			throw new ExceptionHandler(errorFields);
		}
		
		return result;
	}
}