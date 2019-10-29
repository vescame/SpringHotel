package edu.les.security;

import java.util.Date;

import edu.les.entity.UserEntity;
import edu.les.utils.DateFormatter;

public class SpringHotelSession {
	private static Date loginDate;
	private static UserEntity UserEntity;

	public static void loginUser(UserEntity userEntity) {
		SpringHotelSession.loginDate = new Date();
		SpringHotelSession.UserEntity = userEntity;
	}

	public static void logoutUser() {
		SpringHotelSession.loginDate = null;
		SpringHotelSession.UserEntity = null;
	}

	public static UserEntity getLoggedInUser() {
		// not passing the session controlled one
		UserEntity e = SpringHotelSession.UserEntity;
		return e;
	}

	public static String getLoggedInTime() {
		return DateFormatter.format(SpringHotelSession.loginDate);
	}

}
