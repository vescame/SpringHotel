package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.service.RegistrationService;

@Controller
public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;
	
	private final String registerUrl = "/registration";
	private final String statusKey = "STATUS_MESSAGE";
	private final String statusValueSuccess = "Usuario registrado com sucesso!";
	private final String statusValueFailure = "Falha ao cadastrar usuario!";
	private final String userEntityThObj = "userEntity";

	@RequestMapping(value = registerUrl, method = RequestMethod.GET)
	public ModelAndView registerView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.registerUrl);
		modelAndView.addObject(this.userEntityThObj, new UserEntity());
		return modelAndView;
	}

	@RequestMapping(value = registerUrl, method = RequestMethod.POST)
	public ModelAndView newRegister(@ModelAttribute(userEntityThObj) UserEntity userEntity,
			RedirectAttributes redirectAttributes, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		try {
			this.registrationService.registerUser(userEntity);
			redirectAttributes.addFlashAttribute(this.statusKey, this.statusValueSuccess);
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusKey, e.getMessage());
		}
		return new ModelAndView("redirect:" + this.registerUrl, modelMap);
	}
}
