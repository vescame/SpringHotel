package edu.les.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.les.entity.RoomEntity;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Integer> {
}
