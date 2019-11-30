package edu.les.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.RoomCategoryEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.security.SpringHotelSession;
import edu.les.service.RoomCategoryService;

@Controller
public class RoomCategoryController {
	@Autowired
	private RoomCategoryService roomCategoryService;

	@GetMapping(value = "/room-type/room-type-add")
	public ModelAndView roomTypeView(Model model) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/room-type/room-type-add");
		modelAndView.addObject("roomCategoryEntity", new RoomCategoryEntity());
		return modelAndView;
	}

	@PostMapping(value = "/room-type/room-type-add")
	public ModelAndView roomTypeSave(@ModelAttribute("roomCategoryEntity") RoomCategoryEntity roomCategoryEntity,
			RedirectAttributes redir) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		try {
			this.roomCategoryService.addOrUpdate(roomCategoryEntity);
			redir.addFlashAttribute("STATUS_MESSAGE", "Category saved successfully!");
		} catch (ExceptionHandler e) {
			redir.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return new ModelAndView("redirect:/room-type/room-type-add");
	}

	@GetMapping(value = "/room-type/room-type-search")
	public ModelAndView roomTypeSearch(Model model) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/room-type/room-type-search");
		modelAndView.addObject("roomCategoryList", this.roomCategoryService.fetchAll());
		return modelAndView;
	}

	@GetMapping(value = "/room-type/room-type-update/{id}")
	public ModelAndView update(@PathVariable("id") Optional<Integer> id, Model model) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/room-type/room-type-update");
		try {
			if (id.isPresent()) {
				modelAndView.addObject("roomCategoryEntity", this.roomCategoryService.findById(id.get()));
			}
		} catch (ExceptionHandler e) {
			modelAndView.addObject("STATUS_MESSAGE", "There was a problem with this record");
		}
		return modelAndView;
	}

	@PostMapping(value = "/room-type/room-type-update")
	public ModelAndView update(@ModelAttribute("roomCategoryEntity") RoomCategoryEntity roomCategoryEntity,
			RedirectAttributes redirectAttributes) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		try {
			this.roomCategoryService.addOrUpdate(roomCategoryEntity);
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Category updated!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return new ModelAndView("redirect:/room-type/room-type-search");
	}

	@GetMapping(value = "/room-type/room-type-delete/{id}")
	public ModelAndView delete(@PathVariable("id") Optional<Integer> id, RedirectAttributes redirectAttributes) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/room-type/room-type-search");
		if (id.isPresent()) {
			try {
				this.roomCategoryService.deleteById(id.get());
			} catch (ExceptionHandler e) {
				redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "There was a problem deleting this record");
			}
		}
		return modelAndView;
	}
}
