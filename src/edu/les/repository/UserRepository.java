package edu.les.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.les.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
	@Query("FROM hotel_user WHERE email = :v_email AND password = :v_password")
	public Optional<UserEntity> findByCredential(@Param("v_email") String email, @Param("v_password") String password);
}
