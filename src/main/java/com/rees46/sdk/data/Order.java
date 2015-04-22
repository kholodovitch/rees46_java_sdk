package com.rees46.sdk.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Order {
	@JsonProperty("id")
	@JsonInclude(Include.NON_NULL)
	private String id;

	@JsonProperty("user_id")
	@JsonInclude(Include.NON_NULL)
	private String userId;

	@JsonProperty("user_email")
	@JsonInclude(Include.NON_NULL)
	private String userEmail;

	@JsonProperty("date")
	private long date;

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

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		if (date > 95617584000000L)
			throw new RuntimeException("Too large time value. Time must be in seconds.");

		this.date = date;
	}

	public OrderItem[] getItems() {
		return items;
	}

	public void setItems(OrderItem[] items) {
		this.items = items;
	}
}
