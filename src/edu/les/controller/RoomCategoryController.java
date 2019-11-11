package edu.les.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.RoomCategoryEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.service.RoomCategoryService;

@Controller
public class RoomCategoryController {
	@Autowired
	private RoomCategoryService roomCategoryService;

	private final String viewPrefix = "/room-type";
	private final String viewRoomCategoryAdd = viewPrefix + "/room-type-add";
	private final String viewRoomCategorySearch = viewPrefix + "/room-type-search";
	private final String roomCategoryObj = "roomCategoryEntity";
	private final String roomCategoryObjList = "roomCategoryList";
	private final String statusKey = "STATUS_MESSAGE";

	@GetMapping(value = viewRoomCategoryAdd)
	public ModelAndView roomTypeView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.viewRoomCategoryAdd);
		modelAndView.addObject(this.roomCategoryObj, new RoomCategoryEntity());
		return modelAndView;
	}

	@PostMapping(value = viewRoomCategoryAdd)
	public ModelAndView roomTypeSave(@ModelAttribute(roomCategoryObj) RoomCategoryEntity roomCategoryEntity,
			RedirectAttributes redir) {
		try {
			this.roomCategoryService.addOrUpdate(roomCategoryEntity);
			redir.addFlashAttribute(this.statusKey, "Category saved successfully!");
		} catch (ExceptionHandler e) {
			redir.addFlashAttribute(this.statusKey, e.getMessage());
		}
		return new ModelAndView("redirect:" + this.viewRoomCategoryAdd);
	}

	@GetMapping(value = viewRoomCategorySearch)
	public ModelAndView roomTypeSearch(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.viewRoomCategorySearch);
		modelAndView.addObject(this.roomCategoryObj, new RoomCategoryEntity());
		modelAndView.addObject(this.roomCategoryObjList, this.roomCategoryService.fetchAll());
		return modelAndView.addAllObjects(model.asMap());
	}

	@PostMapping(value = viewRoomCategorySearch)
	public ModelAndView roomTypeSearch(@RequestParam("category") String category,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:" + this.viewRoomCategorySearch);
		try {
			RoomCategoryEntity entity = this.roomCategoryService.fetchByCategory(category);
			redirectAttributes.addFlashAttribute(this.roomCategoryObj, entity);
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusKey, e.getMessage());
		}
		return modelAndView;
	}
}
