package com.rees46.sdk.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItem {
	@JsonProperty("id")
	@JsonInclude(Include.NON_NULL)
	private String id;

	@JsonProperty("price")
	@JsonInclude(Include.NON_NULL)
	private Float price;

	@JsonProperty("categories")
	@JsonInclude(Include.NON_NULL)
	private String[] categories;

	@JsonProperty("is_available")
	@JsonInclude(Include.NON_NULL)
	private Boolean is_available;

	@JsonProperty("amount")
	@JsonInclude(Include.NON_NULL)
	private Integer amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
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