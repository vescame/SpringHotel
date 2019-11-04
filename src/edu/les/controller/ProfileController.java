package edu.les.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.security.SpringHotelSession;
import edu.les.service.UserService;

@Controller
public class ProfileController {
	@Autowired
	UserService userService;

	private final String viewUrl = "/profile";
	private final String statusKey = "PROFILE_STATUS";

	@RequestMapping(value = viewUrl, method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.viewUrl);
		UserEntity user = SpringHotelSession.getLoggedInUser();
		if (user != null) {
			modelAndView.addObject("userEntity", user);
		} else {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return modelAndView;
	}

	@RequestMapping(value = viewUrl, params = "save", method = RequestMethod.POST)
	public ModelAndView loginView(@ModelAttribute() UserEntity userEntity, HttpServletRequest request,
			RedirectAttributes redir) {
		try {
			this.userService.update(userEntity);
			redir.addFlashAttribute(this.statusKey,
					"Your profile have been updated, logout and login to apply changes to the system.");
		} catch (ExceptionHandler e) {
			redir.addFlashAttribute(statusKey, e.getMessage());
		}
		return new ModelAndView("redirect:" + this.viewUrl);
	}
}
