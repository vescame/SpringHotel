package edu.les.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.les.entity.BookingEntity;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Integer> {
}
