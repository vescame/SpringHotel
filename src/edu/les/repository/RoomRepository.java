package edu.les.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.les.entity.RoomEntity;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Integer> {
	@Query("FROM room WHERE room_id in :v_list")
	public Iterable<RoomEntity> fetchIn(@Param("v_list") List<Integer> list);
}
