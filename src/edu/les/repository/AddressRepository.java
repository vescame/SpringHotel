package edu.les.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.les.entity.CredentialEntity;

@Repository
public interface AddressRepository extends CrudRepository<CredentialEntity, Integer> {
	
}
