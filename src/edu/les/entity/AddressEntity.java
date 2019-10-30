package edu.les.entity;

public class AddressEntity {
	private String zipCode;
	private String street;
	private String district;
	private String city;
	private String federalUnit;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFederalUnit() {
		return federalUnit;
	}

	public void setFederalUnit(String federalUnit) {
		this.federalUnit = federalUnit;
	}
}
