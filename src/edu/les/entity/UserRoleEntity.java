package edu.les.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "user_role")
@Table(name = "user_role")
public class UserRoleEntity {
	private int id;
	private String roleDescription;

	@Id
	@GeneratedValue
	@Column(name = "role_id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "role_desc", nullable = false, length = 15)
	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

}
