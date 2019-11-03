package edu.les.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.RoomCategoryEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.service.RoomCategoryService;

@Controller
public class RoomCategoryController {
	@Autowired
	private RoomCategoryService roomCategoryService;

	private final String viewUrl = "/room-type";
	private final String viewObjectName = "roomCategoryEntity";
	private final String viewObjectListName = "roomCategoryList";
	private final String statusKey = "STATUS_MESSAGE";
	private final String successfulStatusValue = "Success!";
	private final String notFoundStatusValue = "No Room Category found with id: ";

	@RequestMapping(value = viewUrl, method = RequestMethod.GET)
	public ModelAndView roomTypeView(Model model) {
		ModelAndView modelAndView = new ModelAndView(viewUrl);
		Iterable<RoomCategoryEntity> roomCategoryList = this.roomCategoryService.fetchAll();
		modelAndView.addObject(viewObjectListName, roomCategoryList);
		if (!model.containsAttribute(this.viewObjectName)) {
			modelAndView.addObject(viewObjectName, new RoomCategoryEntity());
		}
		return modelAndView;
	}

	@RequestMapping(value = viewUrl, params = "save", method = RequestMethod.POST)
	public ModelAndView roomTypeSave(@ModelAttribute(viewObjectName) RoomCategoryEntity roomCategoryEntity,
			HttpServletRequest request, RedirectAttributes redir) {
		try {
			this.roomCategoryService.addOrUpdate(roomCategoryEntity);
			redir.addFlashAttribute(statusKey, this.successfulStatusValue);
		} catch (ExceptionHandler e) {
			redir.addFlashAttribute(statusKey, e.getMessage());
		}
		return new ModelAndView("redirect:" + this.viewUrl);
	}

	@RequestMapping(value = viewUrl, params = "search", method = RequestMethod.POST)
	public ModelAndView roomTypeSearch(@ModelAttribute(viewObjectName) RoomCategoryEntity roomCategoryEntity,
			HttpServletRequest request, RedirectAttributes redir) {
		RoomCategoryEntity entity = this.getById(roomCategoryEntity.getRoomCategoryId());
		if (entity != null) {
			redir.addFlashAttribute(viewObjectName, entity);
		} else {
			redir.addFlashAttribute(this.statusKey, this.notFoundStatusValue + roomCategoryEntity.getRoomCategoryId());
		}
		return new ModelAndView("redirect:" + viewUrl);
	}

	private RoomCategoryEntity getById(int id) {
		Optional<RoomCategoryEntity> entity;
		try {
			entity = this.roomCategoryService.findById(id);
			if (entity.isPresent()) {
				return entity.get();
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
		}
		return null;
	}
}
