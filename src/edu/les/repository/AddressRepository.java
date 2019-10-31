package edu.les.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.les.entity.AddressEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, String> {
	
}
