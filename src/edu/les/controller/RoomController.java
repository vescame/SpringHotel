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
import edu.les.entity.RoomEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.service.RoomCategoryService;
import edu.les.service.RoomService;

@Controller
public class RoomController {
	@Autowired
	private RoomService roomService;

	@Autowired
	private RoomCategoryService roomCategoryService;

	@GetMapping(value = { "/room", "/room/room-add" })
	public ModelAndView add(Model model) {
		ModelAndView modelAndView = new ModelAndView("/room/room-add");
		this.injectCategoryList(modelAndView);
		modelAndView.addObject(new RoomEntity());
		return modelAndView;
	}

	private void injectCategoryList(ModelAndView m) {
		Iterable<RoomCategoryEntity> roomCategoryList = this.roomCategoryService.fetchAll();
		m.addObject("roomCategories", roomCategoryList);
	}

	@PostMapping(value = "/room/room-add")
	public ModelAndView add(@ModelAttribute RoomEntity roomEntity, RedirectAttributes redirectAttributes) {
		try {
			this.roomService.addOrUpdate(roomEntity);
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Room created!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return new ModelAndView("redirect:/room/room-add");
	}

	@GetMapping(value = "/room/room-search")
	public ModelAndView search(Model model) {
		ModelAndView modelAndView = new ModelAndView("/room/room-search");
		modelAndView.addObject("roomEntityList", this.roomService.fetchAll());
		return modelAndView;
	}

	@GetMapping(value = "/room/room-update/{id}")
	public ModelAndView update(@PathVariable("id") Optional<Integer> roomId, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("/room/room-update");
		try {
			if (roomId.isPresent()) {
				RoomEntity room = this.roomService.findById(roomId.get());
				modelAndView.addObject("roomEntity", room);
				this.injectCategoryList(modelAndView);
			}
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
			return new ModelAndView("redirect:/room/room-search");
		}
		return modelAndView;
	}

	@PostMapping(value = "/room/room-update")
	public ModelAndView update(@ModelAttribute("roomEntity") RoomEntity roomEntity,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("/room/room-search");
		try {
			this.roomService.addOrUpdate(roomEntity);
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Room updated!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = "/room/room-delete/{id}")
	public ModelAndView delete(@PathVariable("id") Optional<Integer> roomId, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/room/room-search");
		try {
			if (roomId.isPresent()) {
				this.roomService.deleteByID(roomId.get());
			}
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return modelAndView;
	}
}
