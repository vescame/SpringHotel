package edu.les.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Embeddable
@Entity(name = "address")
@Table(name = "address")
public class AddressEntity {
	private String zipCode;
	private String street;
	private String district;
	private String city;
	private String federalUnit;

	@Id
	@Column(name = "zip_code", nullable = false, length = 8)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "street_name", nullable = false, length = 45)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "district_name", nullable = false, length = 45)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "city_name", nullable = false, length = 45)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "federal_unit", nullable = false, length = 2)
	public String getFederalUnit() {
		return federalUnit;
	}

	public void setFederalUnit(String federalUnit) {
		this.federalUnit = federalUnit;
	}
}
