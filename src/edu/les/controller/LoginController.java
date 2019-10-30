package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.CredentialEntity;
import edu.les.entity.UserEntity;
import edu.les.repository.CredentialRepository;

@Controller
public class LoginController {
	@Autowired
	CredentialRepository credentialRepository;

	private final String defaultUrlRedirectsLogin = "/";
	private final String loginUrl = "/login";
	private final String logoutUrl = "/logout";
	private final String homeUrl = "/home";
	private final String credentialEntityThObj = "credentialEntity";
	private final String statusKey = "LOGIN_STATUS";
	private final String statusValueInvalidCredentials = "Credenciais invalidas, tente novamente.";

	@RequestMapping(value = loginUrl, method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.loginUrl);
		if (model.containsAttribute(this.statusKey)) {
			modelAndView.addObject(this.statusKey, this.statusValueInvalidCredentials);
		}
		modelAndView.addObject(this.credentialEntityThObj, new CredentialEntity());
		return modelAndView;
	}

	@RequestMapping(value = defaultUrlRedirectsLogin, method = RequestMethod.GET)
	public ModelAndView redirectToLoginView() {
		ModelMap modelMap = new ModelMap();
		return new ModelAndView("redirect:" + this.loginUrl, modelMap);
	}

	@RequestMapping(value = loginUrl, method = RequestMethod.POST)
	public ModelAndView loginUser(@ModelAttribute(credentialEntityThObj) CredentialEntity credentialEntity,
			RedirectAttributes redirAttr) {
		ModelMap modelMap = new ModelMap();
		String url = "redirect:" + this.loginUrl;
		CredentialEntity c = this.credentialRepository.findLoginAndPassword(credentialEntity.getEmail(),
				credentialEntity.getPassword());
		if (c.equals(credentialEntity)) {
			url = "redirect:" + this.homeUrl;
		} else {
			redirAttr.addFlashAttribute(this.statusKey, this.statusValueInvalidCredentials);
		}
		return new ModelAndView(url, modelMap);
	}

	@RequestMapping(value = logoutUrl, method = RequestMethod.POST)
	public ModelAndView logoutUser(@ModelAttribute("userEntity") UserEntity userEntity) {
		ModelMap modelMap = new ModelMap();
		return new ModelAndView("redirect:" + this.loginUrl, modelMap);
	}

}
