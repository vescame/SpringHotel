package edu.les.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.les.entity.RoomCategoryEntity;

@Repository
public interface RoomCategoryRepository extends CrudRepository<RoomCategoryEntity, Integer> {
}
