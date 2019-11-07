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

import edu.les.entity.UserEntity;
import edu.les.security.SpringHotelSession;
import edu.les.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	LoginService loginService;

	private final String defaultUrlRedirectsLogin = "/";
	private final String loginUrl = "/login";
	private final String homeUrl = "/home";
	private final String statusKey = "STATUS_MESSAGE";
	private final String statusValueInvalidCredentials = "Credenciais invalidas, tente novamente.";

	@RequestMapping(value = loginUrl, method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.loginUrl);
		if (model.containsAttribute(this.statusKey)) {
			modelAndView.addObject(this.statusKey, this.statusValueInvalidCredentials);
		}
		modelAndView.addObject("userEntity", new UserEntity());
		return modelAndView;
	}

	@RequestMapping(value = defaultUrlRedirectsLogin, method = RequestMethod.GET)
	public ModelAndView redirectToLoginView() {
		ModelMap modelMap = new ModelMap();
		return new ModelAndView("redirect:" + this.loginUrl, modelMap);
	}

	@RequestMapping(value = loginUrl, method = RequestMethod.POST)
	public ModelAndView loginUser(@ModelAttribute("userEntity") UserEntity userEntity,
			RedirectAttributes redirAttr) {
		ModelMap modelMap = new ModelMap();
		String url = "redirect:" + this.loginUrl;
		UserEntity userSessionInstance = this.loginService.login(userEntity);
		if (userSessionInstance != null) {
			url = "redirect:" + this.homeUrl;
			SpringHotelSession.loginUser(userSessionInstance);
		} else {
			redirAttr.addFlashAttribute(this.statusKey, this.statusValueInvalidCredentials);
		}
		return new ModelAndView(url, modelMap);
	}
}
