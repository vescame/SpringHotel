package edu.les.controller;

import java.util.Optional;

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

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.security.SpringHotelSession;
import edu.les.service.UserService;

@Controller
public class ProfileController {
	@Autowired
	UserService userService;

	private final String profileUrl = "/profile";
	private final String statusKey = "PROFILE_STATUS";

	@RequestMapping(value = profileUrl, method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		ModelAndView modelAndView = new ModelAndView("profile");
		Optional<UserEntity> user = this.userService.findByCpf(SpringHotelSession.getLoggedInUser().getUserCpf());
		if (user.isPresent()) {
			modelAndView.addObject("userEntity", null);
		} else {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return modelAndView;
	}

	@RequestMapping(value = profileUrl, method = RequestMethod.POST)
	public ModelAndView loginView(@ModelAttribute() UserEntity userEntity, HttpServletRequest request, Model model) {
		ModelAndView modelAndView = new ModelAndView("profile");
		try {
			if (request.getAttribute("btnAction").equals("save")) {
				this.userService.updateUser(userEntity);
			}
		} catch (ExceptionHandler e) {
			this.injectErrorMessage(modelAndView, e.getMessage());
		}
		return modelAndView;
	}
	
	private void injectErrorMessage(ModelAndView modelAndView, String message) {
		modelAndView.addObject(statusKey, message);
	}
}
