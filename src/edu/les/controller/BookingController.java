package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.BookingEntity;
import edu.les.entity.RoomEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.security.SpringHotelSession;
import edu.les.service.BookingService;
import edu.les.service.RoomService;

@Controller
public class BookingController {
	@Autowired
	private BookingService bookingService;

	@Autowired
	private RoomService roomService;

	private final String viewPrefix = "/booking";
	private final String bookingAddUrl = viewPrefix + "/booking-add";
	private final String bookingObj = "bookingEntity";
	private final String loggedInUserObj = "loggedInUser";
	private final String roomListObj = "roomList";
	private final String statusMessage = "STATUS_MESSAGE";

	private Iterable<RoomEntity> disponibleRooms() throws ExceptionHandler {
		return this.roomService.fetchDisponible();
	}
	
	@RequestMapping(value = bookingAddUrl, method = RequestMethod.GET)
	public ModelAndView bookingView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.bookingAddUrl);
		if (!model.containsAttribute(this.bookingObj)) {
			modelAndView.addObject(bookingObj, new BookingEntity());
		}
		try {
			modelAndView.addObject(this.roomListObj, this.disponibleRooms());
		} catch (ExceptionHandler e) {
			modelAndView.addObject(this.statusMessage, e.getMessage());
		}
		modelAndView.addObject(this.loggedInUserObj, SpringHotelSession.getLoggedInUser());
		return modelAndView;
	}

	@RequestMapping(value = bookingAddUrl, method = RequestMethod.POST)
	public ModelAndView bookingRegister(@ModelAttribute(bookingObj) BookingEntity bookingEntity,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:" + this.bookingAddUrl);
		try {
			this.bookingService.add(bookingEntity);
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, "Failed to book room: " + e.getMessage());
			redirectAttributes.addFlashAttribute(this.bookingObj, bookingEntity);
		}
		return modelAndView;
	}
}
