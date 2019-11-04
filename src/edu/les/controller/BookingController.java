package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.les.entity.RoomEntity;
import edu.les.entity.BookingEntity;
import edu.les.service.BookingService;
import edu.les.service.UserService;

public class BookingController {
	
	@Autowired 
	BookingService bookingService;
	
	private final String bookingUrl = ("/booking");
	
	@RequestMapping(value = bookingUrl, method = RequestMethod.GET)
}
