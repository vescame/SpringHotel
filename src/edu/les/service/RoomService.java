package edu.les.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private EntityManager em;

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
		if (!this.hasErrors(roomEntity)) {
			this.roomRepository.save(roomEntity);
		}
	}

	public Iterable<RoomEntity> fetchDisponible() throws ExceptionHandler {
		@SuppressWarnings("unchecked")
		List<RoomEntity> list = this.em
				.createQuery("select DISTINCT r from room r "
						+ "left join booking b on (b.roomEntity.roomId = r.roomId and b.status = \'I\') "
						+ "where r.roomId not in (select b1.roomEntity.roomId from booking b1 where b1.status = \'A\')")
				.getResultList();
		return list;
	}

	public RoomEntity findById(int roomId) {
		Optional<RoomEntity> room = this.roomRepository.findById(roomId);
		return room.isPresent() ? room.get() : null;
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
}
