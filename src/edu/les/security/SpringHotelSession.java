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
//		SpringHotelSession.mock();
		// not passing the session controlled one
		UserEntity e = SpringHotelSession.UserEntity;
		return e;
	}

	public static String getLoggedInTime() {
		return DateFormatter.format(SpringHotelSession.loginDate);
	}

	private static void mock() {
		// mock
		UserEntity user = new UserEntity();
		user.setUserCpf("12345678900");
		user.setUsername("Vinicius Escame");
		user.setUserRole("USER");
		user.setEmail("v.escame@gmail.com");
		user.setPassword("spring");
		user.setHouseNumber(12);
		SpringHotelSession.UserEntity = user;
	}
}
