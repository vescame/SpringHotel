package edu.les.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.les.entity.BookingEntity;
import edu.les.entity.RoomEntity;
import edu.les.exception.ExceptionHandler;
import edu.les.repository.RoomRepository;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private RoomCategoryService roomCategoryService;
	
	@Autowired
	private BookingService bookingService;

	public Iterable<RoomEntity> fetchAll() {
		return this.roomRepository.findAll();
	}

	public void add(RoomEntity roomEntity) throws ExceptionHandler {
		if (!this.hasErrors(roomEntity)) {
			roomEntity.setRoomCategory(
					this.roomCategoryService.fetchByCategory(roomEntity.getRoomCategory().getCategory()));
			this.roomRepository.save(roomEntity);
		}
	}

	public void update(RoomEntity roomEntity) throws ExceptionHandler {
//		this.roomCategoryService.findById();
		if (!this.hasErrors(roomEntity)) {
			this.roomRepository.save(roomEntity);
		}
	}

	public boolean hasErrors(RoomEntity r) throws ExceptionHandler {
		boolean result = true;
		List<String> fieldsWithError = new ArrayList<String>();
		if (r.getRoomId() == 0 || r.getRoomId() < 1) {
			fieldsWithError.add("Room Id");
		}

		// TODO: check if need any value validation

		if (r.getRoomNumber() == 0 || r.getRoomNumber() < 1) {
			fieldsWithError.add("Room Number");
		}

		if (fieldsWithError.isEmpty()) {
			result = false;
		} else {
			throw new ExceptionHandler(fieldsWithError);
		}

		return result;
	}

	public Iterable<RoomEntity> fetchDisponible() throws ExceptionHandler {
		Iterable<RoomEntity> rooms = this.roomRepository.findAll();
		Iterable<BookingEntity> bookings;
		List<RoomEntity> disponibleRoomsForBooking = new ArrayList<RoomEntity>();
		if (rooms != null) {
			bookings = this.bookingService.fetchAll();
			if (!bookings.iterator().hasNext()) {
				for (RoomEntity r : rooms) {
					disponibleRoomsForBooking.add(r);
				}
			}
			for (BookingEntity b : bookings) {
				for (RoomEntity r : rooms) {
					if (r.getRoomId() == b.getRoomEntity().getRoomId()) {
						if (b.getCheckOut() == null) {
							disponibleRoomsForBooking.add(r);
						}
					}
				}
			}
		}
		if (disponibleRoomsForBooking.isEmpty()) {
			throw new ExceptionHandler("There's disponible room");
		}
		return disponibleRoomsForBooking;
	}

	public RoomEntity findById(int roomId) {
		Optional<RoomEntity> room = this.roomRepository.findById(roomId);
		return room.isPresent() ? room.get() : null;
	}
}
