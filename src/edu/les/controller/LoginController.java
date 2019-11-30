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
import edu.les.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;

	@GetMapping(value = { "/", "/login" })
	public ModelAndView loginView(Model model) {
		ModelAndView modelAndView = new ModelAndView("/login");
		modelAndView.addObject("userEntity", new UserEntity());
		return modelAndView.addAllObjects(model.asMap());
	}

	@PostMapping(value = "/login")
	public ModelAndView loginUser(@ModelAttribute("userEntity") UserEntity userEntity,
			RedirectAttributes redirectAttributes) {
		String url = "redirect:/login";
		try {
			UserEntity userSessionInstance = this.loginService.login(userEntity);
			url = "redirect:/home";
			SpringHotelSession.loginUser(userSessionInstance);
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return new ModelAndView(url);
	}

	@GetMapping(value = "/logout")
	public ModelAndView logout(RedirectAttributes redirectAttributes) {
		SpringHotelSession.logoutUser();
		redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Youre logged out!");
		return new ModelAndView("redirect:/login");
	}
}
