package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.les.entity.BookingEntity;
import edu.les.entity.RoomEntity;
//import edu.les.service.BookingService;
import edu.les.service.RoomService;

@Controller
public class BookingController {
//	@Autowired 
//	private BookingService bookingService;
	
	@Autowired
	private RoomService roomService;
	
	private final String viewUrl = "booking";
	private final String viewObjectName = "bookingEntity";
	
	@RequestMapping(value = viewUrl, method = RequestMethod.GET)
	public ModelAndView bookingView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.viewUrl);
		modelAndView.addObject(viewObjectName, new BookingEntity());
		Iterable<RoomEntity> disponible = this.roomService.fetchDisponible();
		modelAndView.addObject("disponibleRooms", disponible);
		return modelAndView;
	}
}
