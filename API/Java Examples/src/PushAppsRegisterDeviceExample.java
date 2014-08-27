import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class PushAppsRegisterDeviceExample {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String apiSecretToken = "98d45829-de15-4bd9-aa68-5154a7d7cdae";

		JSONObject params = new JSONObject();
		params.put("SecretToken", apiSecretToken); // the token received from PushApps for using the Remote API
		params.put("PushToken", "This is my push token"); // the token received from Google or Apple by the device
		params.put("DeviceId", "This is my device ID");// a unique string within this app to identify this device. Could be the IMEI in Android for example or UDID on iOS
		params.put("DeviceType", 2); // 1 for Android, 2 for iOS
		params.put("CustomId", "This is my custom ID"); //optional, set your own custom ID 
		params.put("OSVersion", "6.1.4"); //optional, this device operating system version
		params.put("SDKVersion", "1.5"); //optional, Pushapps sdk version
		params.put("AppVersion", "1.2"); //optional, This app current version
		params.put("DeviceDescription", "iPhone 5"); //optional, the hardware of the device, i.e "iPhone 5"
		params.put("TimeZone", 120); //optional, The device's offset from UTC in minutes. For example, UTC +01:00 will be 60
		params.put("AppIdentifier", "com.pushapps.example"); //optional, The Package Name (Android) / Bundle Id (iOS) of the application


		PostMethod post = new PostMethod(
				"https://ws.pushapps.mobi/RemoteAPI/RegisterDevice");
		try {
			JSONObject data = new JSONObject();
			data.put("apiKey", apiSecretToken);
			data.put("params", params);
			String json = params.toJSONString();
			// Create HTTP request to GreenInvoice API
			HttpClient httpClient = new HttpClient();
			StringRequestEntity requestEntity = new StringRequestEntity(
				    json, //content
				    "application/json", //content type
				    "UTF-8"); //charset
			post.setRequestEntity(requestEntity);
			httpClient.executeMethod(post);

			// Parse response
			String responseBody = post.getResponseBodyAsString();
			Object obj = JSONValue.parse(responseBody);
			JSONObject response = (JSONObject) obj;
			String errorCode = String.valueOf(response.get("Code"));
			if (errorCode.equals("100")) {
				// Ok
			} else  {
				System.out.println("Error code: "  + errorCode + " , error message : " + response.get("Message"));
			}

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
	}
}
