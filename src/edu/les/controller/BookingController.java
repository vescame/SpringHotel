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
import edu.les.exception.ExceptionHandler;
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

	@GetMapping(value = "/booking/booking-add")
	public ModelAndView bookingView(Model model) {
		ModelAndView modelAndView = new ModelAndView("/booking/booking-add");
		try {
			modelAndView.addObject("roomList", this.disponibleRooms());
		} catch (ExceptionHandler e) {
			modelAndView.addObject("STATUS_MESSAGE", e.getMessage());
		}
		modelAndView.addObject("bookingEntity", new BookingEntity());
		return modelAndView;
	}

	@PostMapping(value = "/booking/booking-add")
	public ModelAndView bookingRegister(@ModelAttribute("bookingEntity") BookingEntity bookingEntity,
			RedirectAttributes redirectAttributes) {
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
		ModelAndView modelAndView = new ModelAndView("/booking/booking-search");
		Iterable<BookingEntity> list = this.bookingService.fetchAll();
		modelAndView.addObject("bookingEntityList", list);
		return modelAndView;
	}

	// TODO: corrigir -> erro ao converter array para string????
	// WARNING: Resolved [org.springframework.beans.TypeMismatchException: Failed to convert value of type
	// 'java.util.ArrayList' to required type 'java.lang.String'; nested exception is
	// org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.util.ArrayList<?>]
	// to type [java.lang.String] for value '[edu.les.entity.BookingEntity@4ae5c1d8]'; nested exception is
	// org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type
	// [edu.les.entity.BookingEntity] to type [java.lang.String]]
	@PostMapping(value = "/booking/booking-search")
	public ModelAndView search(@RequestParam("userCpf") Optional<String> cpf, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:/booking/booking-search");
		if (cpf.isPresent()) {
			Iterable<BookingEntity> list = this.bookingService.fetchByCpf(cpf.get());
			modelAndView.addObject("bookingEntityList", list);
		}
		return modelAndView;
	}

	@GetMapping(value = "/booking/booking-checkout/{id}")
	public ModelAndView checkout(@PathVariable("id") Optional<Integer> bookingId) {
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
		try {
			this.bookingService.checkOut(bookingEntity.getBookingId());
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", "Successfully Checked Out!");
		} catch (ExceptionHandler e) {
			redirectAttributes.addFlashAttribute("STATUS_MESSAGE", e.getMessage());
		}
		return new ModelAndView("redirect:/booking/booking-search");
	}

}
