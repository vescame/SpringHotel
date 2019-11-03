package edu.les.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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
	private UserRoleEntity userRole;
	private int houseNumber;
	private String telephoneNumber;
	private String celphoneNumber;
	private Date dateOfBirth;
	private char status = 'A';
	private AddressEntity addressEntity;
	private CredentialEntity credentialEntity;

	public UserEntity() {
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setId(1);
		userRole.setRoleDescription("Usuario");
		this.userRole = userRole;
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
	
	@Embedded
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	public UserRoleEntity getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoleEntity userRoleId) {
		this.userRole = userRoleId;
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

	@Embedded
	@OneToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "zip_code")
	public AddressEntity getAddressEntity() {
		return addressEntity;
	}

	public void setAddressEntity(AddressEntity addressEntity) {
		this.addressEntity = addressEntity;
	}

	@Embedded
	@OneToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "credential_id")
	public CredentialEntity getCredentialEntity() {
		return credentialEntity;
	}

	public void setCredentialEntity(CredentialEntity credentialEntity) {
		this.credentialEntity = credentialEntity;
	}

}
