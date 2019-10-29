package edu.les.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.les.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
}
