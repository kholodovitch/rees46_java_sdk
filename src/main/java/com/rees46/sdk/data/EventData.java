package com.rees46.sdk.data;

public class EventData {
	private final String item_id;
	private final Float price;
	private final Boolean is_available;
	private final String[] categories;
	private String name;
	private String url;
	private String image_url;
	private String[] locations;

	public EventData(String item_id, Float price, Boolean is_available, String[] categories) {
		this.item_id = item_id;
		this.price = price;
		this.is_available = is_available;
		this.categories = categories;
	}

	public String getItemId() {
		return item_id;
	}

	public Float getPrice() {
		return price;
	}

	public Boolean getIsAvailable() {
		return is_available;
	}

	public String[] getCategories() {
		return categories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return image_url;
	}

	public void setImageUrl(String image_url) {
		this.image_url = image_url;
	}

	public String[] getLocations() {
		return locations;
	}

	public void setLocations(String[] locations) {
		this.locations = locations;
	}
}