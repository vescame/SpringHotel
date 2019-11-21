package edu.les.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.les.entity.BookingEntity;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Integer> {

	@Query("FROM booking WHERE user_cpf = :v_cpf")
	Iterable<BookingEntity> findByCpf(@Param("v_cpf") String cpf);

	@Query("FROM booking WHERE status = \'A\'")
	Iterable<BookingEntity> fetchActive();

	@Query("FROM booking WHERE status = \'A\' AND user_cpf = :v_cpf")
	Iterable<BookingEntity> hasActiveBooking(@Param("v_cpf") String cpf);
}
