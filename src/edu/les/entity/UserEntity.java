package edu.les.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
@Entity(name = "hotel_user")
@Table(name = "hotel_user")
public class UserEntity {
	private String userCpf;
	private String username;
	private String userRole;
	private int houseNumber;
	private String telephoneNumber;
	private String celphoneNumber;
	private Date dateOfBirth;
	private char status = 'A';
	private AddressEntity addressEntity;
	private String email;
	private String password;
	
	public UserEntity() {
		this.userRole = "USER";
	}
	
	@Id
	@Column(name = "user_cpf", length = 11)
	public String getUserCpf() {
		return userCpf;
	}

	public void setUserCpf(String cpf) {
		this.userCpf = cpf;
	}

	@Column(name = "user_name", nullable = false, length = 35)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "user_role", nullable = false, length = 15)
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Column(name = "house_number", nullable = false)
	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	@Column(name = "telephone_number", nullable = true, length = 15)
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	@Column(name = "celphone_number", nullable = true, length = 15)
	public String getCelphoneNumber() {
		return celphoneNumber;
	}

	public void setCelphoneNumber(String celphoneNumber) {
		this.celphoneNumber = celphoneNumber;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth", nullable = false)
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "status", nullable = false)
	public char getStatus() {
		return Character.toUpperCase(status);
	}

	public void setStatus(char status) {
		this.status = Character.toUpperCase(status);
	}

	@OneToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "zip_code")
	public AddressEntity getAddressEntity() {
		return addressEntity;
	}

	public void setAddressEntity(AddressEntity addressEntity) {
		this.addressEntity = addressEntity;
	}

	@Column(name = "email", nullable = false, length = 35)
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

}
