package edu.les.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.les.entity.CredentialEntity;

@Repository
public interface CredentialRepository extends CrudRepository<CredentialEntity, Integer> {
	/* exemplo
	 * select t1.email, t1.password FROM credential t1 inner join hotel_user t2 on (t1.email = t2.email) WHERE t2.email =
	 * 'v.escame@outlook.com' AND t1.password = 'viny0102'
	 */
	@Query("FROM credential WHERE email = :v_email AND password = :v_password")
	public CredentialEntity fetchCredential(@Param("v_email") String email,@Param("v_password") String password);
}
