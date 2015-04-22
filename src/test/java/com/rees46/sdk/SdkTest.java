package com.rees46.sdk;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.rees46.sdk.data.EventData;
import com.rees46.sdk.data.EventType;
import com.rees46.sdk.data.Order;
import com.rees46.sdk.data.OrderItem;
import com.rees46.sdk.data.PurchaseData;
import com.rees46.sdk.data.UserInfo;

public class SdkTest extends TestCase {
	private Sdk sdk;

	public SdkTest(String testName) {
		super(testName);
		String shopCode = System.getenv("REES46_SHOP_CODE");
		assertTrue(shopCode != null);

		sdk = new Sdk(shopCode);
	}

	public static Test suite() {
		return new TestSuite(SdkTest.class);
	}

	public void testGenedateSsid() throws IOException {
		assertNotNull(sdk.generateSSID());
	}

	public void testTrackPurchase() throws IOException {
		UUID generatedSsid = sdk.generateSSID();
		assertNotNull(generatedSsid);

		UserInfo userInfo = createTestUserInfo();
		PurchaseData[] eventDataArray = new PurchaseData[1];
		eventDataArray[0] = getGoodItem();
		assertTrue(sdk.trackPurchase(generatedSsid, userInfo, eventDataArray, UUID.randomUUID().toString()));
	}

	public void testTrackView() throws IOException {
		UUID generatedSsid = sdk.generateSSID();
		assertNotNull(generatedSsid);

		UserInfo userInfo = createTestUserInfo();
		EventData[] eventDataArray = new EventData[1];
		eventDataArray[0] = getBadItem();
		assertTrue(sdk.track(EventType.view, generatedSsid, userInfo, eventDataArray));
	}

	public void testTrackCart() throws IOException {
		UUID generatedSsid = sdk.generateSSID();
		assertNotNull(generatedSsid);

		UserInfo userInfo = createTestUserInfo();
		EventData[] eventDataArray = new EventData[1];
		eventDataArray[0] = getBadItem();
		assertTrue(sdk.track(EventType.cart, generatedSsid, userInfo, eventDataArray));
	}

	public void testTrackRemoveFromCart() throws IOException {
		UUID generatedSsid = sdk.generateSSID();
		assertNotNull(generatedSsid);

		UserInfo userInfo = createTestUserInfo();
		EventData[] eventDataArray = new EventData[1];
		eventDataArray[0] = getBadItem();
		assertTrue(sdk.track(EventType.remove_from_cart, generatedSsid, userInfo, eventDataArray));
	}

	public void testImportOrders() throws IOException {
		String shopSecretKey = System.getenv("REES46_SHOP_SECRET_KEY");
		assertTrue(shopSecretKey != null);
		sdk.setShopSecretKey(shopSecretKey);

		OrderItem[] items = new OrderItem[1];
		items[0] = new OrderItem();
		items[0].setId(new UUID(0, 2).toString());
		items[0].setAmount(1);
		items[0].setPrice("0");

		Order[] orders = new Order[1];
		orders[0] = new Order();
		orders[0].setDate(new Date());
		orders[0].setId(new UUID(1, 1).toString());
		orders[0].setUserId(new UUID(0, 1).toString());
		orders[0].setItems(items);
		assertTrue(sdk.importOrders(orders));
	}

	private UserInfo createTestUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(new UUID(0, 1).toString());
		userInfo.setUserEmail("kholodovitch@gmail.com");
		return userInfo;
	}

	private PurchaseData getGoodItem() {
		return new PurchaseData(new UUID(0, 2).toString(), 0f, true, new String[] { new UUID(1000, 1).toString() }, 1);
	}

	private EventData getBadItem() {
		return new EventData(new UUID(0, 4).toString(), 0f, true, new String[] { new UUID(1000, 1).toString() });
	}
}
