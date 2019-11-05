package edu.les.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	private final String viewUrl = "/room";
	private final String viewObjectName = "roomEntity";
	private final String viewObjectListName = "roomList";
	private final String statusKey = "STATUS_MESSAGE";
	private final String successfulStatusValue = "Success!";

	@RequestMapping(value = viewUrl, method = RequestMethod.GET)
	public ModelAndView roomTypeView(Model model) {
		ModelAndView modelAndView = new ModelAndView(viewUrl);
		Iterable<RoomCategoryEntity> roomCategoryList = this.roomCategoryService.fetchAll();
		Iterable<RoomEntity> roomList = this.roomService.fetchAll();
		modelAndView.addObject(viewObjectListName, roomList);
		modelAndView.addObject("categorias", roomCategoryList);
		if (!model.containsAttribute(this.viewObjectName)) {
			modelAndView.addObject(viewObjectName, new RoomEntity());
		}
		return modelAndView;
	}

	@RequestMapping(value = viewUrl, params = "save", method = RequestMethod.POST)
	public ModelAndView roomTypeSave(@ModelAttribute(viewObjectName) RoomEntity roomEntity, HttpServletRequest request,
			RedirectAttributes redir) {
		try {
			this.roomService.add(roomEntity);
			redir.addFlashAttribute(statusKey, this.successfulStatusValue);
		} catch (ExceptionHandler e) {
			redir.addFlashAttribute(statusKey, e.getMessage());
		}
		return new ModelAndView("redirect:" + this.viewUrl);
	}

	@RequestMapping(value = viewUrl, params = "delete", method = RequestMethod.POST)
	public ModelAndView roomTypeDelete(@PathVariable int roomId, HttpServletRequest request, RedirectAttributes redir) {
		System.out.println("deleted: " + roomId);
		return new ModelAndView("redirect:" + this.viewUrl);
	}
}
