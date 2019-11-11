package edu.les.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.les.entity.RoomCategoryEntity;

@Repository
public interface RoomCategoryRepository extends CrudRepository<RoomCategoryEntity, Integer> {
	@Query("FROM room_category where category = :v_category")
	public Optional<RoomCategoryEntity> fetchByCategory(@Param("v_category") String category);
}
