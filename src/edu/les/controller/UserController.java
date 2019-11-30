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
import edu.les.entity.UserReportViewModel;
import edu.les.exception.ExceptionHandler;
import edu.les.lab.dao.UserDAO;
import edu.les.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	private final String userObj = "userEntity";
	private final String userObjForUpdate = "userEntityForUpdate";
	private final String userObjList = "userEntityList";
	private final String oldPasswdParam = "OLD_PASSWORD";
	private final String statusMessage = "STATUS_MESSAGE";

	public List<String> roleList() {
		List<String> roles = new ArrayList<>();
		roles.add("ADMINISTRATOR");
		roles.add("EMPLOYEE");
		roles.add("USER");
		return roles;
	}

	@GetMapping(value = "user/user-add")
	public ModelAndView add(Model model) {
		ModelAndView modelAndView = new ModelAndView("user/user-add");
		if (!model.containsAttribute(this.userObj)) {
			modelAndView.addObject(this.userObj, new UserEntity());
		}
		return modelAndView;
	}

	@PostMapping(value = "user/user-add")
	public ModelAndView add(@ModelAttribute(userObj) UserEntity userEntity, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user/user-add");
		try {
			new UserDAO().insert(userEntity);
			redirectAttributes.addFlashAttribute(this.statusMessage, "User created successfully!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, "Failed to create user!" + e.getMessage());
			redirectAttributes.addFlashAttribute(this.userObj, userEntity);
		}
		return modelAndView;
	}

	@GetMapping(value = "user/user-search")
	public ModelAndView search(Model model) {
		ModelAndView modelAndView = new ModelAndView("user/user-search");
		Iterable<UserEntity> userEntityList = this.userService.fetchAll();
		modelAndView.addObject(this.userObjList, userEntityList);
		return modelAndView;
	}

	@PostMapping(value = "user/user-search")
	public ModelAndView search(@RequestParam("userCpf") String userCpf, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user/user-search");
		try {
			UserEntity u = new UserDAO().select(userCpf);
			redirectAttributes.addFlashAttribute(this.userObj, u);
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = "user/user-search/{id}")
	public ModelAndView inactivateUser(@PathVariable("id") Optional<String> cpf, RedirectAttributes redirAttr) {
		try {
			if (cpf.isPresent()) {
				UserReportViewModel report = new UserDAO().report(cpf.get());
				redirAttr.addFlashAttribute("report", report);
			}
		} catch (ExceptionHandler e) {
			redirAttr.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return new ModelAndView("redirect:/user/user-report");
	}

	@GetMapping(value = "user/user-delete/{id}")
	public ModelAndView delete(@PathVariable("id") Optional<String> cpf, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user/user-search");
		try {
			if (cpf.isPresent()) {
				new UserDAO().delete(cpf.get());
				redirectAttributes.addFlashAttribute(this.statusMessage, "User deleted!");
			}
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = "user/user-update/{id}")
	public ModelAndView update(@PathVariable("id") Optional<String> cpf, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("/user/user-update");
		try {
			if (cpf.isPresent()) {
				UserEntity user = this.userService.findById(cpf.get());
				modelAndView.addObject(this.oldPasswdParam, user.getPassword());
				modelAndView.addObject(this.userObjForUpdate, user);
				modelAndView.addObject("rolesList", this.roleList());
			}
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, e.getMessage());
		}
		return modelAndView;
	}

	@PostMapping(value = "user/user-update")
	public ModelAndView update(@ModelAttribute(userObjForUpdate) UserEntity userEntityUpdated,
			@RequestParam(oldPasswdParam) String oldPassword, RedirectAttributes redirectAttributes) {
		try {
			// if old user didn't specified a password,
			// we assume he doesn't want to update it, so still the same
			if (userEntityUpdated.getPassword().length() == 0) {
				userEntityUpdated.setPassword(oldPassword);
			}
			new UserDAO().update(userEntityUpdated);
			redirectAttributes.addFlashAttribute(this.statusMessage, "User Updated!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, e.getMessage());
		}
		return new ModelAndView("redirect:/user/user-search");
	}
}
