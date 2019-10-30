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

import edu.les.entity.AddressEntity;
import edu.les.entity.CredentialEntity;
import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.service.RegistrationService;

@Controller
public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;
	
	private final String registerUrl = "/register";
	private final String failureStatusKey = "FAILURE_STATUS";
	private final String successfulStatusKey = "SUCCESSFUL_STATUS";
	private final String statusValueSuccess = "Usuario registrado com sucesso!";
	private final String statusValueFailure = "Falha ao cadastrar usuario!";
	private final String userEntityThObj = "userEntity";
	private final String addressEntityThObj = "addressEntity";
	private final String credentialEntityThObj = "credentialEntity";

	@RequestMapping(value = registerUrl, method = RequestMethod.GET)
	public ModelAndView registerView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.registerUrl);
		modelAndView.addObject(this.userEntityThObj, new UserEntity());
		modelAndView.addObject(this.addressEntityThObj, new AddressEntity());
		modelAndView.addObject(this.credentialEntityThObj, new CredentialEntity());
		if (model.containsAttribute(this.successfulStatusKey)) {
			modelAndView.addObject(this.successfulStatusKey, this.statusValueSuccess);
		} else if (model.containsAttribute(this.failureStatusKey)) {
			modelAndView.addObject(this.failureStatusKey, this.statusValueFailure);
		}
		return modelAndView;
	}

	@RequestMapping(value = registerUrl, method = RequestMethod.POST)
	public ModelAndView addRegister(@ModelAttribute(userEntityThObj) UserEntity userEntity,
			@ModelAttribute(addressEntityThObj) AddressEntity addressEntity,
			@ModelAttribute(credentialEntityThObj) CredentialEntity credentialEntity,
			RedirectAttributes redirectAttributes, BindingResult result) {
		ModelMap modelMap = new ModelMap();
		try {
			registrationService.newUser(userEntity, credentialEntity);
			redirectAttributes.addFlashAttribute(this.successfulStatusKey, this.statusValueSuccess);
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.failureStatusKey, this.statusValueFailure);
			System.out.println(e.getMessage());
		}
		return new ModelAndView("redirect:" + this.registerUrl, modelMap);
	}

}
