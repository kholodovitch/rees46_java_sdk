package com.rees46.sdk.data;

public class PurchaseData extends EventData {
	private final Integer amount;

	public PurchaseData(String item_id, Float price, Boolean is_available, String[] categories, Integer amount) {
		super(item_id, price, is_available, categories);
		this.amount = amount;
	}

	public Integer getAmount() {
		return amount;
	}
}
