package com.rees46.sdk.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
	@JsonProperty("id")
	private String id;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("user_email")
	private String userEmail;
	@JsonProperty("date")
	private Date date;
	@JsonProperty("items")
	private OrderItem[] items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OrderItem[] getItems() {
		return items;
	}

	public void setItems(OrderItem[] items) {
		this.items = items;
	}
}
