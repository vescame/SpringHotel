package edu.les.controller;

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

import edu.les.entity.BookingEntity;
import edu.les.entity.RoomEntity;
import edu.les.entity.UserEntity;
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

	private Iterable<RoomEntity> disponibleRooms() throws ExceptionHandler {
		return this.roomService.fetchDisponible();
	}
	
	private boolean isLogged() {
		return SpringHotelSession.getLoggedInUser() != null;
	}
	
	private boolean isAdmin() {
		return SpringHotelSession.isAdmin();
	}

	@GetMapping(value = "/booking/booking-add")
	public ModelAndView bookingView(Model model, RedirectAttributes redirectAttributes) {
		if (!this.isLogged()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/booking/booking-add");
		try {
			String cpf = SpringHotelSession.getLoggedInUser().getUserCpf();
			if (!this.isAdmin()){
				if (this.bookingService.hasActiveBooking(cpf)) {
					redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "You already have an active booking!");
					return new ModelAndView("redirect:/home");
				}
 				modelAndView.addObject("IS_ADMIN", false);
			} else {
				modelAndView.addObject("IS_ADMIN", true);
			}
			modelAndView.addObject("roomList", this.disponibleRooms());
			modelAndView.addObject("bookingEntity", this.userBooking(cpf));
		} catch (ExceptionHandler e) {
			modelAndView.addObject("STATUS_MESSAGE", e.getMessage());
		}
		return modelAndView;
	}
	
	private BookingEntity userBooking(String cpf) {
		BookingEntity booking = new BookingEntity();
		UserEntity userEntity = new UserEntity();
		userEntity.setUserCpf(cpf);
		booking.setUserEntity(userEntity);
		return booking;
	}

	@PostMapping(value = "/booking/booking-add")
	public ModelAndView bookingRegister(@ModelAttribute("bookingEntity") BookingEntity bookingEntity,
			RedirectAttributes redirectAttributes) {
		if (!this.isLogged()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/booking/booking-add");
		try {
			this.bookingService.add(bookingEntity);
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "User Successfully Booked!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Failed to book room: " + e.getMessage());
			redirectAttributes.addFlashAttribute("bookingEntity", bookingEntity);
		}
		return modelAndView;
	}

	@GetMapping(value = "/booking/booking-search")
	public ModelAndView search(Model model) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/booking/booking-search");
		Iterable<BookingEntity> list = this.bookingService.fetchAll();
		modelAndView.addObject("bookingEntityList", list);
		return modelAndView;
	}

	@PostMapping(value = "/booking/booking-search")
	public ModelAndView search(@RequestParam("userCpf") Optional<String> cpf, RedirectAttributes redirectAttributes) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/booking/booking-search");
		if (cpf.isPresent()) {
			Iterable<BookingEntity> list = this.bookingService.fetchByCpf(cpf.get());
			modelAndView.addObject("bookingEntityList", list);
		}
		return modelAndView;
	}

	@GetMapping(value = "/booking/booking-checkout/{id}")
	public ModelAndView checkout(@PathVariable("id") Optional<Integer> bookingId) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("/booking/booking-checkout");
		try {
			if (bookingId.isPresent()) {
				BookingEntity booking = this.bookingService.fetchById(bookingId.get());
				modelAndView.addObject("bookingEntity", booking);
			}
		} catch (ExceptionHandler e) {
			modelAndView.addObject("STATUS_MESSAGE", e.getMessage());
		}
		return modelAndView;
	}

	@PostMapping(value = "/booking/booking-checkout")
	public ModelAndView checkout(@ModelAttribute("bookingEntity") BookingEntity bookingEntity,
			RedirectAttributes redirectAttributes) {
		if (!SpringHotelSession.isAdmin()) {
			return new ModelAndView("redirect:/login");
		}
		try {
			this.bookingService.checkOut(bookingEntity.getBookingId());
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Successfully Checked Out!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return new ModelAndView("redirect:/booking/booking-search");
	}

}
