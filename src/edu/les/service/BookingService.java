package edu.les.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.BookingEntity;
import edu.les.entity.RoomEntity;
import edu.les.entity.UserEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.BookingRepository;

@Service
public class BookingService {
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

	public void add(BookingEntity bookingEntity) throws ExceptionHandler {
		if (!this.hasUserCpf(bookingEntity)) {
			throw new ExceptionHandler("You must write the user CPF");
		}
		if (this.hasSelectedRoom(bookingEntity)) {
			RoomEntity rentedRoom = this.roomService.findById(bookingEntity.getRoomEntity().getRoomId());
			bookingEntity.setRoomEntity(rentedRoom);
			UserEntity userEntity = this.userService.findByCpf(bookingEntity.getUserEntity().getUserCpf());
			bookingEntity.setUserEntity(userEntity);
			if (!this.hasErrors(bookingEntity)) {
				this.bookingRepository.save(bookingEntity);
			}
		}
	}
	
	public boolean hasActiveBooking(String cpf) {
		List<BookingEntity> bookings = new ArrayList<BookingEntity>();
		this.bookingRepository.hasActiveBooking(cpf).forEach(bookings::add);
		return bookings.size() >= 1;
	}

	public Iterable<BookingEntity> fetchAll() {
		return this.bookingRepository.findAll();
	}

	public Iterable<BookingEntity> fetchByCpf(String cpf) {
		return this.bookingRepository.findByCpf(cpf);
	}

	public Iterable<BookingEntity> fetchActive() {
		return this.bookingRepository.fetchActive();
	}

	private boolean hasUserCpf(BookingEntity b) throws ExceptionHandler {
		if (b.getUserEntity() != null && !b.getUserEntity().getUserCpf().isEmpty()
				&& b.getUserEntity().getUserCpf().length() == 11) {
			return true;
		}
		return false;
	}

	private boolean hasSelectedRoom(BookingEntity b) throws ExceptionHandler {
		if (b.getRoomEntity() == null) {
			throw new ExceptionHandler("Room was not selected!");
		}
		return true;
	}

	public BookingEntity fetchActiveBookingByCpf(String userCpf) throws ExceptionHandler {
		Iterable<BookingEntity> activeBookings = this.bookingRepository.fetchActive();
		BookingEntity userBooking = new BookingEntity();
		for (BookingEntity b : activeBookings) {
			if (b.getUserEntity().getUserCpf().contentEquals(userCpf)) {
				userBooking = b;
			}
		}
		if (userBooking.getUserEntity() == null) {
			throw new ExceptionHandler("There is no active booking for CPF:" + userCpf);
		}
		return userBooking;
	}
	
	public BookingEntity fetchById(int bookingId) throws ExceptionHandler {
		Optional<BookingEntity> booking = this.bookingRepository.findById(bookingId);
		if (booking.isPresent()) {
			return booking.get();
		}
		throw new ExceptionHandler("Booking not found!");
	}

	public void checkOut(int bookingId) throws ExceptionHandler {
		Optional<BookingEntity> booking = this.bookingRepository.findById(bookingId);
		if (booking.isPresent()) {
			BookingEntity bookingEntity = booking.get();
			bookingEntity.setCheckOut(new Date());
			bookingEntity.setStatus("I");
			this.bookingRepository.save(bookingEntity);
		}
	}

	private boolean hasErrors(BookingEntity b) throws ExceptionHandler {
		boolean result = true;
		List<String> errorFields = new ArrayList<>();

		if (b.getCheckIn() == null) {
			errorFields.add("Check In Date");
		}

		// checkout < checkin : < 0
		// checkout > checkin : > 0
		// checkout == checkin : == 0
		// checkout can be null
		if (b.getCheckOut() != null && b.getCheckOut().compareTo(b.getCheckIn()) < 0) {
			errorFields.add("Check Out Date");
		}

		if (b.getStatus() == null) {
			errorFields.add("Status");
		}

		try {
			this.userService.hasErrors(b.getUserEntity());
			this.roomService.hasErrors(b.getRoomEntity());
		} catch (ExceptionHandler e) {
			errorFields.addAll(e.getErrors());
		}

		if (errorFields.isEmpty()) {
			result = false;
		} else {
			throw new ExceptionHandler(errorFields);
		}

		return result;
	}
}