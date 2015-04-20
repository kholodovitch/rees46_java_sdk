package com.rees46.sdk.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Orders {
	@JsonProperty("shop_id")
	private String shopId;
	@JsonProperty("shop_secret")
	private String shopSecret;
	@JsonProperty("orders")
	private Order[] orders;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopSecret() {
		return shopSecret;
	}

	public void setShopSecret(String shopSecret) {
		this.shopSecret = shopSecret;
	}

	public Order[] getOrders() {
		return orders;
	}

	public void setOrders(Order[] orders) {
		this.orders = orders;
	}
}
