package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.security.SpringHotelSession;
import edu.les.service.UserService;

@Controller
public class ProfileController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/profile")
	public ModelAndView loginView(Model model) {
		UserEntity user = SpringHotelSession.getLoggedInUser();
		if (user == null) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("/profile").addObject("userEntity", user);
	}

	@PostMapping(value = "/profile")
	public ModelAndView loginView(@ModelAttribute("userEntity") UserEntity userEntity, RedirectAttributes redirectAttributes) {
		try {
			System.out.println(userEntity.getUserRole());
			this.userService.update(userEntity);
			redirectAttributes.addFlashAttribute("PROFILE_STATUS", "Your profile have been updated!");
			SpringHotelSession.loginUser(this.userService.findByCpf(userEntity.getUserCpf()));
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("PROFILE_STATUS", e.getMessage());
		}
		return new ModelAndView("redirect:/profile");
	}
}
