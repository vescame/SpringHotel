package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.les.entity.BookingEntity;
import edu.les.security.SpringHotelSession;
import edu.les.service.BookingService;

@Controller
public class HomeController {
	@Autowired
	private BookingService bookingService;

	private final String viewUrl = "/home";

	@RequestMapping(value = viewUrl, method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		if (SpringHotelSession.getLoggedInUser() == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView(this.viewUrl);
		final String roleDesc = SpringHotelSession.getLoggedInUser().getUserRole();
		if (roleDesc.equals("ADMINISTRATOR") || roleDesc.equals("EMPLOYEE")) {
			modelAndView.addObject("IS_ADMIN", true);
		} else {
			modelAndView.addObject("IS_ADMIN", false);
		}
		Iterable<BookingEntity> userBookings = this.bookingService
				.fetchByCpf(SpringHotelSession.getLoggedInUser().getUserCpf());
		modelAndView.addObject("userBookings", userBookings);
		modelAndView.addObject("user", SpringHotelSession.getLoggedInUser());
		return modelAndView;
	}
}
