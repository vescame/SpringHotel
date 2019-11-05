package edu.les.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "room")
@Table(name = "room")
public class RoomEntity {
	private int roomId;
	private int roomNumber;
	private RoomCategoryEntity roomCategory;

	@Id
	@GeneratedValue
	@Column(name = "room_id", nullable = false)
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	@Column(name = "room_number", nullable = false)
	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_category_id")
	public RoomCategoryEntity getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(RoomCategoryEntity roomCategory) {
		this.roomCategory = roomCategory;
	}

}
