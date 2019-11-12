package edu.les.lab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.les.entity.AddressEntity;
import edu.les.exception.ExceptionHandler;

public class AddressDAO {
	
	public void insert(AddressEntity addressEntity) throws ExceptionHandler {
		try {
			Connection con = ResourceMan.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(
					"insert into address values (?, ?, ?, ?, ?)");
			pstmt.setString(1, addressEntity.getZipCode());
			pstmt.setString(2, addressEntity.getCity());
			pstmt.setString(3, addressEntity.getDistrict());
			pstmt.setString(4, addressEntity.getFederalUnit());
			pstmt.setString(5, addressEntity.getStreet());
			pstmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionHandler("Erro ao salvar endereco!");
		}
	}
	
	public AddressEntity select(String zip) throws ExceptionHandler {
		AddressEntity a = new AddressEntity();
		try {
			Connection con = ResourceMan.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement("select * from address where zip_code = ?");
			pstmt.setString(1, zip);
			ResultSet rs = pstmt.executeQuery();
			if (rs.first()) {
				a.setZipCode(rs.getString(1));
				a.setCity(rs.getString(2));
				a.setDistrict(rs.getString(3));
				a.setFederalUnit(rs.getString(4));
				a.setStreet(rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
}
