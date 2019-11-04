package edu.les.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Embeddable
@Entity(name = "credential")
@Table(name = "credential")
public class CredentialEntity {
	private String email;
	private String password;

	@Id
	@Column(name = "email", nullable = false, unique = true, length = 35)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = false, length = 35)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		try {
			CredentialEntity c1;
			c1 = obj instanceof CredentialEntity ? (CredentialEntity) obj : new CredentialEntity();
			if (this.getEmail().equals(c1.getEmail()) && this.getPassword().equals(c1.getPassword())) {
				result = true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Register not found");
		}
		return result;
	}

}
