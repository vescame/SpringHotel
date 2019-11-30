package edu.les.entity;

public class UserReportViewModel {
	private String userCpf;
	private String userName;
	private int numOfBookings;
	private float totalValue;
	
	public String getUserCpf() {
		return userCpf;
	}
	public void setUserCpf(String userCpf) {
		this.userCpf = userCpf;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getNumOfBookings() {
		return numOfBookings;
	}
	public void setNumOfBookings(int numOfBookings) {
		this.numOfBookings = numOfBookings;
	}
	public float getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(float totalValue) {
		this.totalValue = totalValue;
	}
	
}
