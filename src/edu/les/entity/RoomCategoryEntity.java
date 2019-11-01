package edu.les.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "room_category")
@Table(name = "room_category")
public class RoomCategoryEntity {
	private int roomCategoryId;
	private String category;
	private float price;
	private int maxPeople;

	@Id
	@GeneratedValue
	@Column(name = "room_category_id", nullable = false)
	public int getRoomCategoryId() {
		return roomCategoryId;
	}

	public void setRoomCategoryId(int roomCategoryId) {
		this.roomCategoryId = roomCategoryId;
	}

	@Column(name = "category", nullable = false, length = 25)
	public String getCategory() {
		return category;
	}

	public void setCategory(String roomCategory) {
		this.category = roomCategory;
	}

	@Column(name = "price", nullable = false)
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Column(name = "max_people", nullable = false)
	public int getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}

}
