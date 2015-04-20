package com.rees46.sdk;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rees46.sdk.data.Order;
import com.rees46.sdk.data.Orders;

public class Sdk {
	private final String UrlImportOrders = "http://api.rees46.com/import/orders";

	private String shopCode;
	private String shopSecretKey;

	public Sdk(String shopCode) {
		this.shopCode = shopCode;
	}

	public void setShopSecretKey(String shopSecretKey) {
		this.shopSecretKey = shopSecretKey;
	}

	public boolean importOrders(Order[] orders) throws IOException {
		Orders ordersRequest = new Orders();
		ordersRequest.setOrders(orders);
		ordersRequest.setShopId(shopCode);
		ordersRequest.setShopSecret(shopSecretKey);

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(ordersRequest);
		HttpURLConnection conn = post(UrlImportOrders, "application/json", body);

		int status = conn.getResponseCode();
		if (status == 200) {
			String responseBody = getAndClose(conn.getInputStream());
			System.out.println(responseBody);
			return true;
		}

		return false;
	}

	protected HttpURLConnection post(String url, String contentType, String body) throws IOException {
		if (url == null || body == null)
			throw new IllegalArgumentException("arguments cannot be null");

		byte[] bytes = body.getBytes();
		HttpURLConnection conn = getConnection(url);

		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setFixedLengthStreamingMode(bytes.length);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", contentType);

		OutputStream out = conn.getOutputStream();
		try {
			out.write(bytes);
		} finally {
			close(out);
		}
		return conn;
	}

	protected HttpURLConnection getConnection(String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		return conn;
	}

	protected static String getString(InputStream stream) throws IOException {
		if (stream == null) {
			return "";
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder content = new StringBuilder();
		String newLine;
		do {
			newLine = reader.readLine();
			if (newLine != null) {
				content.append(newLine).append('\n');
			}
		} while (newLine != null);
		if (content.length() > 0) {
			// strip last newline
			content.setLength(content.length() - 1);
		}
		return content.toString();
	}

	private static String getAndClose(InputStream stream) throws IOException {
		try {
			return getString(stream);
		} finally {
			if (stream != null) {
				close(stream);
			}
		}
	}

	private static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				// ignore error
			}
		}
	}
}
