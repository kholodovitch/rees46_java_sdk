package com.rees46.sdk.data;

public class PurchaseData extends EventData {
	private final int amount;

	public PurchaseData(String item_id, Float price, Boolean is_available, String[] categories, int amount) {
		super(item_id, price, is_available, categories);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}
}
