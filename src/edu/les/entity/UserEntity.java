package edu.les.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "hotel_user")
@Table(name = "hotel_user")
public class UserEntity {
	private String userCpf;
	private String username;
	private int userRoleId;
	private String zipCode;
	private int houseNumber;
	private String telephoneNumber;
	private String celphoneNumber;
	private String email;
	private Date dateOfBirth;
	private char status;
//	private CredentialEntity credentialEntity;

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

	@Column(name = "user_role_id", nullable = false)
	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	@Column(name = "zip_code", nullable = false, length = 8)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	@Column(name = "email", nullable = false, length = 35)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Column(name = "status", nullable = false, columnDefinition = "DEFAULT A")
	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "email", referencedColumnName = "email")
//	public CredentialEntity getCredentialEntity() {
//		return credentialEntity;
//	}
//
//	public void setCredentialEntity(CredentialEntity credentialEntity) {
//		this.credentialEntity = credentialEntity;
//	}

}
