package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.les.entity.UserEntity;
import edu.les.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	private final String userUrl = "/user";

	@RequestMapping(value = userUrl, method = RequestMethod.GET)
	public ModelAndView loginView(Model model) {
		ModelAndView modelAndView = new ModelAndView("user");
		Iterable<UserEntity> userEntityList = this.userService.fetchAll();
		modelAndView.addObject("userEntityList", userEntityList);
		modelAndView.addObject("userEntity", new UserEntity());
		return modelAndView;
	}
}
