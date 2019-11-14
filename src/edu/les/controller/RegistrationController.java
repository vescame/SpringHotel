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
import edu.les.service.RegistrationService;

@Controller
public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;

	@GetMapping(value = "/registration")
	public ModelAndView registerView(Model model) {
		ModelAndView modelAndView = new ModelAndView("/registration");
		modelAndView.addObject("userEntity", new UserEntity());
		return modelAndView;
	}

	@PostMapping(value = "/registration")
	public ModelAndView newRegister(@ModelAttribute("userEntity") UserEntity userEntity,
			RedirectAttributes redirectAttributes) {
		try {
			this.registrationService.registerUser(userEntity);
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Usuario registrado com sucesso!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return new ModelAndView("redirect:/registration");
	}
}
