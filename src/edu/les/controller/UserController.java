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
import edu.les.lab.dao.UserDAO;
import edu.les.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	private final String viewPrefix = "/user";
	private final String userObj = "userEntity";
	private final String userObjForUpdate = "userEntityForUpdate";
	private final String userObjList = "userEntityList";
	private final String userAddUrl = viewPrefix + "/user-add";
	private final String userUpdateUrl = viewPrefix + "/user-update";
	private final String userSearchUrl = viewPrefix + "/user-search";
	private final String userDeleteUrl = viewPrefix + "/user-delete";
	private final String oldPasswdParam = "OLD_PASSWORD";
	private final String statusMessage = "STATUS_MESSAGE";

	public List<String> roleList() {
		List<String> roles = new ArrayList<>();
		roles.add("ADMINISTRATOR");
		roles.add("EMPLOYEE");
		roles.add("USER");
		return roles;
	}

	@GetMapping(value = userAddUrl)
	public ModelAndView add(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.userAddUrl);
		if (!model.containsAttribute(this.userObj)) {
			modelAndView.addObject(this.userObj, new UserEntity());
		}
		return modelAndView;
	}

	@PostMapping(value = userAddUrl)
	public ModelAndView add(@ModelAttribute(userObj) UserEntity userEntity, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:" + this.userAddUrl);
		try {
			new UserDAO().insert(userEntity);
			redirectAttributes.addFlashAttribute(this.statusMessage, "User created successfully!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, "Failed to create user!" + e.getMessage());
			redirectAttributes.addFlashAttribute(this.userObj, userEntity);
		}
		return modelAndView;
	}

	@GetMapping(value = userSearchUrl)
	public ModelAndView search(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.userSearchUrl);
		Iterable<UserEntity> userEntityList = this.userService.fetchAll();
		modelAndView.addObject(this.userObjList, userEntityList);
		return modelAndView;
	}

	@PostMapping(value = userSearchUrl)
	public ModelAndView search(@RequestParam("userCpf") String userCpf, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:" + this.userSearchUrl);
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
				new UserDAO().inactivateUser(cpf.get());
				redirAttr.addFlashAttribute("STATUS_MESSAGE", "User with CPF:" + cpf.get() + " is now inactive!");
			}
		} catch (ExceptionHandler e) {
			redirAttr.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return new ModelAndView("redirect:" + this.userSearchUrl);
	}

	@GetMapping(value = userDeleteUrl)
	public ModelAndView delete(Model model) {
		return new ModelAndView(this.userDeleteUrl);
	}

	@PostMapping(value = userDeleteUrl)
	public ModelAndView delete(@RequestParam("userCpf") String userCpf, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:" + this.userDeleteUrl);
		try {
			this.userService.delete(userCpf);
			redirectAttributes.addFlashAttribute(this.statusMessage, "User deleted!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = userUpdateUrl)
	public ModelAndView update(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.userUpdateUrl);
		if (!model.containsAttribute(this.userObjForUpdate)) {
			modelAndView.addObject(this.userObjForUpdate, new UserEntity());
		}
		if (!model.containsAttribute("rolesList")) {
			modelAndView.addObject("rolesList", this.roleList());
		}
		return modelAndView.addAllObjects(model.asMap());
	}

	@PostMapping(value = userUpdateUrl, params = "search")
	public ModelAndView update(@RequestParam("userCpf") String userCpf, RedirectAttributes redirectAttributes) {
		try {
			UserEntity user = this.userService.findById(userCpf);
			redirectAttributes.addFlashAttribute(this.oldPasswdParam, user.getPassword());
			redirectAttributes.addFlashAttribute(this.userObjForUpdate, user);
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, e.getMessage());
		}
		return new ModelAndView("redirect:" + this.userUpdateUrl);
	}

	@PostMapping(value = userUpdateUrl, params = "save")
	public ModelAndView update(@ModelAttribute(userObjForUpdate) UserEntity userEntityUpdated,
			@RequestParam(oldPasswdParam) String oldPassword, RedirectAttributes redirectAttributes) {
		try {
			// if old user didn't specified a password,
			// we assume he doesn't want to update it, so still the same
			if (userEntityUpdated.getPassword().length() == 0) {
				userEntityUpdated.setPassword(oldPassword);
			}
			this.userService.update(userEntityUpdated);
			redirectAttributes.addFlashAttribute(this.statusMessage, "User Updated!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, e.getMessage());
		}
		return new ModelAndView("redirect:" + this.userUpdateUrl);
	}
}
