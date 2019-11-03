package edu.les.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.les.entity.RoomCategoryEntity;
import edu.les.service.RoomCategoryService;

@Controller
public class RoomCategoryController {
	@Autowired
	private RoomCategoryService roomCategoryService;
	
	private final String viewUrl = "/room-type"; 
	private final String viewObjectName = "roomCategoryEntity";
	private final String viewObjectListName = "roomCategoryList";

	@RequestMapping(value = viewUrl, method = RequestMethod.GET)
	public ModelAndView roomTypeView(Model model) {
		ModelAndView modelAndView = new ModelAndView(viewUrl);
		Iterable<RoomCategoryEntity> roomCategoryList = this.roomCategoryService.fetchAll();
		modelAndView.addObject(viewObjectListName, roomCategoryList);
		modelAndView.addObject(viewObjectName, new RoomCategoryEntity());
		return modelAndView;
	}

	@RequestMapping(value = "/room-type", method = RequestMethod.GET)
	public ModelAndView roomTypePost(@ModelAttribute(viewObjectName) RoomCategoryEntity roomCategoryEntity,
			HttpServletRequest request, BindingResult result, RedirectAttributes redir) {
		return new ModelAndView();
	}
}
