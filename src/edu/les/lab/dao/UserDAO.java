package edu.les.lab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import edu.les.entity.UserEntity;
import edu.les.entity.UserReportViewModel;
import edu.les.exception.ExceptionHandler;

@Service
public class UserDAO {
	public void insert(UserEntity userEntity) throws ExceptionHandler {
		if (!UserValidation.hasErrors(userEntity)) {
			AddressDAO addressDAO = new AddressDAO();
			addressDAO.insert(userEntity.getAddressEntity());
			try {
				Connection con = ResourceMan.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement("insert into hotel_user(user_cpf, celphone_number,"
						+ " date_of_birth, email, house_number, password,"
						+ " status, telephone_number, user_role, user_name,"
						+ "zip_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, userEntity.getUserCpf());
				pstmt.setString(2, userEntity.getCelphoneNumber());
				Date b = this.formatDate(userEntity.getDateOfBirth());
				pstmt.setDate(3, new java.sql.Date(b.getTime()));
				pstmt.setString(4, userEntity.getEmail());
				pstmt.setInt(5, userEntity.getHouseNumber());
				pstmt.setString(6, userEntity.getPassword());
				pstmt.setString(7, String.valueOf(userEntity.getStatus()));
				pstmt.setString(8, userEntity.getTelephoneNumber());
				pstmt.setString(9, userEntity.getUserRole());
				pstmt.setString(10, userEntity.getUsername());
				pstmt.setString(11, userEntity.getAddressEntity().getZipCode());
				pstmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public UserEntity select(String cpf) throws ExceptionHandler {
		UserEntity u = new UserEntity();
		try {
			if (cpf == null) {
				throw new ExceptionHandler("Cpf é nulo!");
			}
			Connection con = ResourceMan.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement("select * from hotel_user where user_cpf = ?");
			pstmt.setString(1, cpf);
			ResultSet rs = pstmt.executeQuery();
			if (rs.first()) {
				u.setUserCpf(rs.getString(1));
				u.setCelphoneNumber(rs.getString(2));
				u.setDateOfBirth(rs.getDate(3));
				u.setEmail(rs.getString(4));
				u.setHouseNumber(rs.getInt(5));
				u.setPassword(rs.getString(6));
				u.setStatus(new StringBuilder(rs.getString(7)).charAt(0));
				u.setTelephoneNumber(rs.getString(8));
				u.setUserRole(rs.getString(9));
				u.setUsername(rs.getString(10));
				final String zip = rs.getString(11);
				u.setAddressEntity(new AddressDAO().select(zip));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	public UserEntity update(UserEntity userEntity) throws ExceptionHandler {
		if (!UserValidation.hasErrors(userEntity)) {
			try {
				AddressDAO addressDAO = new AddressDAO();
				addressDAO.insert(userEntity.getAddressEntity());
			} catch (ExceptionHandler e) {
				System.out.println("address already exists");
			}
			try {
				Connection con = ResourceMan.getInstance().getConnection();
				PreparedStatement pstmt = con
						.prepareStatement("update hotel_user set " + "celphone_number = ?," + "date_of_birth = ?,"
								+ "email = ?," + "house_number = ?," + "password = ?," + "telephone_number = ?,"
								+ "user_role = ?," + "user_name = ?," + "zip_code = ? where user_cpf = ?");
				pstmt.setString(1, userEntity.getCelphoneNumber());
				Date b = this.formatDate(userEntity.getDateOfBirth());
				pstmt.setDate(2, new java.sql.Date(b.getTime()));
				pstmt.setString(3, userEntity.getEmail());
				pstmt.setInt(4, userEntity.getHouseNumber());
				pstmt.setString(5, userEntity.getPassword());
				pstmt.setString(6, userEntity.getTelephoneNumber());
				pstmt.setString(7, userEntity.getUserRole());
				pstmt.setString(8, userEntity.getUsername());
				pstmt.setString(9, userEntity.getAddressEntity().getZipCode());
				pstmt.setString(10, userEntity.getUserCpf());
				pstmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ExceptionHandler("Failed to update user with cpf " + userEntity.getUserCpf());
			}
		}
		return userEntity;
	}

	public void delete(String cpf) throws ExceptionHandler {
		try {
			if (cpf == null) {
				throw new ExceptionHandler("Cpf é nulo!");
			}
			Connection con = ResourceMan.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement("delete from hotel_user where user_cpf = ?");
			pstmt.setString(1, cpf);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler("Failed to delete user: " + cpf);
		}
	}

	public void inactivateUser(String cpf) throws ExceptionHandler {
		try {
			Connection con = ResourceMan.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareCall("{CALL inactivateUser(?)}");
			pstmt.setString(1, cpf);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler("There\'s no user with CPF: " + cpf);
		}
	}

	public UserReportViewModel report(String cpf) throws ExceptionHandler {
		UserReportViewModel report = new UserReportViewModel();
		try {
			Connection con = ResourceMan.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareCall("{CALL userReport(?)}");
			pstmt.setString(1, cpf);
			ResultSet rs = pstmt.executeQuery();
			if (rs.first()) {
				report.setUserCpf(rs.getString("user_cpf"));
				report.setUserName(rs.getString("user_name"));
				report.setNumOfBookings(rs.getInt("num_of_bookings"));
				report.setTotalValue(rs.getFloat("total_value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler("There\'s no user with CPF: " + cpf);
		}
		return report;
	}

	private Date formatDate(Date d) {
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String nascString = sdf.format(d);
		Date nasc = new Date();
		try {
			nasc = sdf.parse(nascString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nasc;
	}
}
