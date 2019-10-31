package edu.les.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "address")
@Table(name = "address")
public class AddressEntity {
	private char[] zipCode = new char[8];
	private String street;
	private String district;
	private String city;
	private char[] federalUnit = new char[2];

	@Id
	@Column(name = "zip_code", nullable = false, length = 8)
	public char[] getZipCode() {
		return zipCode;
	}

	public void setZipCode(char[] zipCode) {
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
	public char[] getFederalUnit() {
		return federalUnit;
	}

	public void setFederalUnit(char[] federalUnit) {
		this.federalUnit = federalUnit;
	}
}
