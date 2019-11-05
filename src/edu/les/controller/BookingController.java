package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;

import edu.les.service.BookingService;

public class BookingController {
	
	@Autowired 
	BookingService bookingService;
	
	private final String bookingUrl = ("/booking");
	
	// @RequestMapping(value = bookingUrl, method = RequestMethod.GET)
}
