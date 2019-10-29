package edu.les.repository;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.les.entity.CredentialEntity;

@Repository
public interface CredentialRepository extends CrudRepository<CredentialEntity, Integer> {
//	@Query("FROM credential t1 inner join hotel_user t2 on (t1.email = t2.email) "
//			+ "WHERE t2.email = :v_email AND t1.password = :v_password")
//	public CredentialEntity findLoginAndPassword(@Param("v_email") String username,
//			@Param("v_password") String password);
}
