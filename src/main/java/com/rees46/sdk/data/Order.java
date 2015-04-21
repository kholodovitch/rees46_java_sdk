package com.rees46.sdk.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("user_id")
	private Integer userId;
	@JsonProperty("user_email")
	private String userEmail;
	@JsonProperty("date")
	private Date date;
	@JsonProperty("items")
	private OrderItem[] items;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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
