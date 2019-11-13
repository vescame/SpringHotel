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

	public void addOrUpdate(RoomEntity roomEntity) throws ExceptionHandler {
		if (!this.hasErrors(roomEntity)) {
			roomEntity.setRoomCategory(
					this.roomCategoryService.fetchByCategory(roomEntity.getRoomCategory().getCategory()));
			this.roomRepository.save(roomEntity);
		}
	}
	
	public void deleteByID(int id) throws ExceptionHandler {
		this.roomRepository.deleteById(id);
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

	public RoomEntity findById(int roomId) throws ExceptionHandler {
		Optional<RoomEntity> room = this.roomRepository.findById(roomId);
		if (room.isPresent()) {
			return room.get();
		}
		throw new ExceptionHandler("Room not found!");
	}

	public boolean hasErrors(RoomEntity r) throws ExceptionHandler {
		boolean result = true;
		List<String> fieldsWithError = new ArrayList<String>();

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
