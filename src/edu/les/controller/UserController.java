package edu.les.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.security.SpringHotelSession;
import edu.les.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	public List<String> roleList() {
		List<String> roles = new ArrayList<>();
		roles.add("ADMINISTRATOR");
		roles.add("EMPLOYEE");
		roles.add("USER");
		return roles;
	}

	@GetMapping(value = "/user/user-add")
	public ModelAndView add(Model model) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/user/user-add");
		if (!model.containsAttribute("userEntity")) {
			modelAndView.addObject("userEntity", new UserEntity());
		}
		modelAndView.addObject("rolesList", this.roleList());
		return modelAndView;
	}

	@PostMapping(value = "/user/user-add")
	public ModelAndView add(@ModelAttribute("userEntity") UserEntity userEntity,
			RedirectAttributes redirectAttributes) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/user/user-add");
		try {
			this.userService.add(userEntity);
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "User successfully created!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Failed to create user!" + e.getMessage());
			redirectAttributes.addFlashAttribute("userEntity", userEntity);
		}
		return modelAndView;
	}

	@GetMapping(value = "/user/user-search")
	public ModelAndView search(Model model) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/user/user-search");
		Iterable<UserEntity> userEntityList = this.userService.fetchAll();
		modelAndView.addObject("userEntityList", userEntityList);
		return modelAndView;
	}

	@PostMapping(value = "/user/user-search")
	public ModelAndView search(@RequestParam("userCpf") Optional<String> userCpf,
			RedirectAttributes redirectAttributes) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/user/user-search");
		try {
			if (userCpf.isPresent()) {
				UserEntity userEntity = this.userService.findByCpf(userCpf.get());
				redirectAttributes.addFlashAttribute("userEntity", userEntity);
			}
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = "/user/user-update/{id}")
	public ModelAndView update(@PathVariable("id") Optional<String> cpf, RedirectAttributes redirectAttributes) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/user/user-update");
		try {
			if (cpf.isPresent()) {
				UserEntity userEntity = this.userService.findByCpf(cpf.get());
				modelAndView.addObject("userEntity", userEntity);
				modelAndView.addObject("OLD_PASSWORD", userEntity.getPassword());
				modelAndView.addObject("rolesList", this.roleList());
			}
		} catch (ExceptionHandler e) {
			redirectAttributes.addAttribute("STATUS_MESSAGE", e.getMessage());
			return new ModelAndView("redirect:/user/user-search");
		}
		return modelAndView;
	}

	@PostMapping(value = "/user/user-update")
	public ModelAndView update(@ModelAttribute("userEntity") UserEntity userEntity,
			@RequestParam("OLD_PASSWORD") Optional<String> password, RedirectAttributes redirectAttributes) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/user/user-search");
		try {
			if (userEntity.getPassword().isEmpty() && password.isPresent()) {
				userEntity.setPassword(password.get());
				this.userService.update(userEntity);
				redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "User Updated!");
			}
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
			redirectAttributes.addFlashAttribute("userEntity", userEntity);
			return new ModelAndView("redirect:/user/user-update/" + userEntity.getUserCpf());
		}
		return modelAndView;
	}

	@GetMapping(value = "/user/user-delete/{id}")
	public ModelAndView delete(@PathVariable("id") Optional<String> cpf, RedirectAttributes redirectAttributes) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/user/user-search");
		try {
			if (cpf.isPresent()) {
				this.userService.delete(cpf.get());
			}
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return modelAndView;
	}
}
