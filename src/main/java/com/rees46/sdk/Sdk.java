package com.rees46.sdk;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rees46.sdk.data.EventData;
import com.rees46.sdk.data.EventType;
import com.rees46.sdk.data.Order;
import com.rees46.sdk.data.Orders;
import com.rees46.sdk.data.PurchaseData;
import com.rees46.sdk.data.UserInfo;
import com.rees46.sdk.data.internal.ApiResult;

public class Sdk {
	private final String UrlImportOrders = "http://api.rees46.com/import/orders";
	private final String UrlBase = "http://api.rees46.com/";

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
			return "OK".equals(responseBody);
		}

		return false;
	}

	public UUID generateSSID() throws IOException {
		HttpURLConnection conn = get(UrlBase + "generate_ssid" + "?shop_id=" + shopCode);

		int status = conn.getResponseCode();
		if (status == 200) {
			String responseBody = getAndClose(conn.getInputStream());
			if (responseBody != null && responseBody.length() == 36)
				return UUID.fromString(responseBody);
		}

		return null;
	}

	public boolean trackPurchase(UUID sessionId, UserInfo user_info, PurchaseData[] eventDataArray, String order_id) throws IOException {
		Map<String, String> fields = prepareTrackData(EventType.purchase, sessionId, user_info, eventDataArray);

		if (order_id != null)
			fields.put("order_id", order_id);

		for (int i = 0; i < eventDataArray.length; i++) {
			PurchaseData eventData = eventDataArray[i];
			String indexStr = "[" + i + "]";
			if (eventData.getAmount() != null)
				fields.put("amount" + indexStr, Integer.toString(eventData.getAmount()));
		}

		return processPushEvent(fields);
	}

	public boolean track(EventType event, UUID sessionId, UserInfo user_info, EventData[] eventDataArray) throws IOException {
		Map<String, String> fields = prepareTrackData(EventType.purchase, sessionId, user_info, eventDataArray);

		return processPushEvent(fields);
	}

	protected Map<String, String> prepareTrackData(EventType event, UUID sessionId, UserInfo user_info, EventData[] eventDataArray) throws IOException {
		Map<String, String> paramsInput = new Hashtable<String, String>();
		paramsInput.put("event", event.toString());
		paramsInput.put("shop_id", shopCode);
		paramsInput.put("ssid", sessionId.toString());

		if (user_info != null) {
			if (user_info.getUserId() != null)
				paramsInput.put("user_id", user_info.getUserId());

			if (user_info.getUserEmail() != null)
				paramsInput.put("user_email", user_info.getUserEmail());
		}

		for (int i = 0; i < eventDataArray.length; i++) {
			EventData eventData = eventDataArray[i];
			String indexStr = "[" + i + "]";
			if (eventData.getItemId() != null)
				paramsInput.put("item_id" + indexStr, eventData.getItemId());
			if (eventData.getPrice() != null)
				paramsInput.put("price" + indexStr, Float.toString(eventData.getPrice()));
			if (eventData.getIsAvailable() != null)
				paramsInput.put("is_available" + indexStr, Boolean.toString(eventData.getIsAvailable()));
			if (eventData.getCategories() != null)
				paramsInput.put("categories" + indexStr, joinStrings(eventData.getCategories(), ","));
		}
		return paramsInput;
	}

	private boolean processPushEvent(Map<String, String> fields) throws IOException {
		String res = post(UrlBase + "push", fields);
		if (res != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				ApiResult resObj = mapper.readValue(res, ApiResult.class);
				return "success".equals(resObj.getStatus());
			} catch (Exception e) {
			}
		}
		return false;
	}

	protected String post(String url, Map<String, String> paramsInput) throws IOException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>(paramsInput.size());
		for (String key : paramsInput.keySet())
			params.add(new BasicNameValuePair(key, paramsInput.get(key)));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		// Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					return getAndClose(instream);
				} finally {
					instream.close();
				}
			}
		}
		return null;
	}

	protected HttpURLConnection get(String url) throws IOException {
		if (url == null)
			throw new IllegalArgumentException("arguments cannot be null");

		HttpURLConnection conn = getConnection(url);

		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("GET");
		return conn;
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
		if (stream == null)
			return "";

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

	private String joinStrings(String[] categories, String splitter) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < categories.length; i++) {
			if (i != 0)
				builder.append(splitter);
			builder.append(categories[i]);
		}
		return builder.toString();
	}
}
