package com.rees46.sdk.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItem {
	@JsonProperty("id")
	private String id;
	@JsonProperty("price")
	private String price;
	@JsonProperty("categories")
	private String[] categories;
	@JsonProperty("is_available")
	private Boolean is_available;
	@JsonProperty("amount")
	private Integer amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public Boolean getIsAvailable() {
		return is_available;
	}

	public void setIsAvailable(Boolean is_available) {
		this.is_available = is_available;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}