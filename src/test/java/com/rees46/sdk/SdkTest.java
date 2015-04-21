package com.rees46.sdk;

import java.io.IOException;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.rees46.sdk.data.Order;
import com.rees46.sdk.data.OrderItem;

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

	public void testApp() throws IOException {
		String shopSecretKey = System.getenv("REES46_SHOP_SECRET_KEY");
		assertTrue(shopSecretKey != null);
		sdk.setShopSecretKey(shopSecretKey);

		OrderItem[] items = new OrderItem[1];
		items[0] = new OrderItem();
		items[0].setId(2);
		items[0].setAmount(1);
		items[0].setPrice(0);

		Order[] orders = new Order[1];
		orders[0] = new Order();
		orders[0].setDate(new Date());
		orders[0].setId(100);
		orders[0].setUserId(1);
		orders[0].setItems(items);
		assertTrue(sdk.importOrders(orders));
	}
}
