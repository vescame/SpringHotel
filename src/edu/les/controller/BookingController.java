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

import edu.les.entity.BookingEntity;
import edu.les.entity.RoomEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.security.SpringHotelSession;
import edu.les.service.BookingService;
import edu.les.service.RoomService;

@Controller
public class BookingController {
	@Autowired
	private BookingService bookingService;

	@Autowired
	private RoomService roomService;

	private final String viewPrefix = "/booking";
	private final String bookingAddUrl = viewPrefix + "/booking-add";
	private final String bookingSearchUrl = viewPrefix + "/booking-search";
	private final String bookingCheckoutUrl = viewPrefix + "/booking-checkout";
	private final String bookingObj = "bookingEntity";
	private final String bookingListObj = "bookingEntityList";
	private final String loggedUserCpf = "userCpf";
	private final String loggedUserName = "userName";
	private final String roomListObj = "roomList";
	private final String statusMessage = "STATUS_MESSAGE";

	private Iterable<RoomEntity> disponibleRooms() throws ExceptionHandler {
		return this.roomService.fetchDisponible();
	}

	@GetMapping(value = bookingAddUrl)
	public ModelAndView bookingView(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.bookingAddUrl);
		if (!model.containsAttribute(this.bookingObj)) {
			modelAndView.addObject(bookingObj, new BookingEntity());
		}
		if (model.containsAttribute(this.statusMessage)) {
			modelAndView.addObject(this.statusMessage, model.asMap().get(this.statusMessage));
		}
		try {
			modelAndView.addObject(this.roomListObj, this.disponibleRooms());
		} catch (ExceptionHandler e) {
			modelAndView.addObject(this.statusMessage, e.getMessage());
		}
		modelAndView.addObject(this.loggedUserCpf, SpringHotelSession.getLoggedInUser().getUserCpf());
		modelAndView.addObject(this.loggedUserName, SpringHotelSession.getLoggedInUser().getUsername());
		return modelAndView;
	}

	@PostMapping(value = bookingAddUrl)
	public ModelAndView bookingRegister(@ModelAttribute(bookingObj) BookingEntity bookingEntity,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:" + this.bookingAddUrl);
		try {
			this.bookingService.add(bookingEntity);
			redirectAttributes.addFlashAttribute(this.statusMessage, "Booking Successfully Completed!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, "Failed to book room: " + e.getMessage());
			redirectAttributes.addFlashAttribute(this.bookingObj, bookingEntity);
		}
		return modelAndView;
	}

	@GetMapping(value = bookingSearchUrl)
	public ModelAndView search(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.bookingSearchUrl);
		if (!model.containsAttribute(this.bookingListObj)) {
			Iterable<BookingEntity> list = this.bookingService.fetchAll();
			modelAndView.addObject(this.bookingListObj, list);
		}
		return modelAndView;
	}

	@PostMapping(value = bookingSearchUrl)
	public ModelAndView search(@RequestParam("userCpf") String userCpf, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:" + this.bookingSearchUrl);
		Iterable<BookingEntity> list = this.bookingService.fetchByCpf(userCpf);
		modelAndView.addObject(this.bookingListObj, list);
		return modelAndView;
	}

	@GetMapping(value = bookingCheckoutUrl)
	public ModelAndView checkout(Model model) {
		ModelAndView modelAndView = new ModelAndView(this.bookingCheckoutUrl);
		if (SpringHotelSession.getLoggedInUser().getUserRole().equals("ADMINISTRATOR")) {
			modelAndView.addObject("IS_ADMIN", true);
		} else {
			final String userCpf = SpringHotelSession.getLoggedInUser().getUserCpf();
			modelAndView.addObject("cpf", userCpf);
			BookingEntity booking;
			try {
				booking = this.bookingService.fetchActiveBookingByCpf(userCpf);
				modelAndView.addObject(this.bookingObj, booking);
			} catch (ExceptionHandler e) {
				modelAndView.addObject(this.statusMessage, e.getMessage());
			}
		}
		return modelAndView;
	}

	@PostMapping(value = bookingCheckoutUrl)
	public ModelAndView checkout(@RequestParam("bookingId") String bookingId,
			RedirectAttributes redirectAttributes) {
		try {
			this.bookingService.checkOut(Integer.parseInt(bookingId));
			redirectAttributes.addFlashAttribute(this.statusMessage, "Successfully Checked Out!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute(this.statusMessage, e.getMessage());
		}
		return new ModelAndView("redirect:" + this.bookingCheckoutUrl);
	}

}
