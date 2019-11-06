package edu.les.controller;

import javax.servlet.http.HttpServletRequest;

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
//import edu.les.service.BookingService;
import edu.les.service.RoomService;

@Controller
public class BookingController {
	@Autowired 
	private BookingService bookingService;

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
		modelAndView.addObject("loggedUser", SpringHotelSession.getLoggedInUser());
		return modelAndView;
	}

	@RequestMapping(value = viewUrl, method = RequestMethod.POST)
	public ModelAndView bookingRegister(@ModelAttribute(viewObjectName) BookingEntity bookingEntity,
			HttpServletRequest request, RedirectAttributes redir) {
		ModelAndView modelAndView = new ModelAndView(this.viewUrl);
		try {
			this.bookingService.add(bookingEntity);
		} catch (ExceptionHandler e) {
			redir.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return modelAndView;
	}
}
