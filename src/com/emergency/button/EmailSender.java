package com.emergency.button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class EmailSender {
	public static void send(String to, String message)  {
		sendWithEmailbyweb(to, message);
	}
	
	public static boolean sendWithEmailbyweb(String to, String message) {
		
		String responseBody = "";
		
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("https://emailbyweb.appspot.com/email");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("to", to));
			nameValuePairs.add(new BasicNameValuePair("from", "Emergency Button <EmergencyButtonApp@gmail.com>"));
			nameValuePairs.add(new BasicNameValuePair("subject", "Emergency"));
			nameValuePairs.add(new BasicNameValuePair("message", message));
			nameValuePairs.add(new BasicNameValuePair("secret", Config.secret));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			//HttpResponse response = httpclient.execute(httppost);
			
			// Create a response handler
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        responseBody = httpclient.execute(httppost, responseHandler);
	        
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("EmailSender", e.getMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("EmailSender", e.getMessage(), e);
		}
		
		if ("success" == responseBody) {
			return true;
		} else {
			Log.e("EmailSender", "Failed sending email: response \"" + responseBody + "\"");
			return false;
		}
	}
}