package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.les.security.SpringHotelSession;
import edu.les.service.UserService;

@Controller
public class HomeController {
	@Autowired
	UserService userService;

	private final String viewUrl = "/home";

	@RequestMapping(value = viewUrl, method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.viewUrl);
		final String roleDesc = SpringHotelSession.getLoggedInUser().getUserRole();
		if (roleDesc.equals("ADMINISTRATOR") || roleDesc.equals("EMPLOYEE")) {
			modelAndView.addObject("IS_ADMIN", true);
		}
		return modelAndView;
	}
}
