package com.cmu.edu.safedrop.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.cmu.edu.safedrop.dto.RequestMethod;

public class RestClient  extends AsyncTask <RequestMethod, Void, String>
{


	private static final String TAG = "RestClient";

	private ArrayList <NameValuePair> params;
	private ArrayList <NameValuePair> headers;
	private String url;
	private HttpUriRequest request;
	private int responseCode;
	private String message;

	private String response;

	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@SuppressLint("NewApi")
	public RestClient(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public void addParam(String name, String value){
		params.add(new BasicNameValuePair(name, value));
	}

	public void addHeader(String name, String value){
		headers.add(new BasicNameValuePair(name, value));
	}

	private static String convertStreamToString(HttpEntity entity) throws IllegalStateException, IOException {
		InputStream is = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	@Override
	public String doInBackground(RequestMethod... method) {

		HttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse;

		switch(method[0]) 
		{
		case GET:
		{
			//add parameters
			StringBuffer combinedParams = new StringBuffer();
			if(!params.isEmpty()){
				//	combinedParams.append("?");
				for(NameValuePair p : params){
					String paramString;
					try {
						paramString = URLEncoder.encode(p.getValue(),"UTF-8");

						if(combinedParams.length() > 1){
							combinedParams.append("/");
							combinedParams.append(paramString);
						}
						else{
							combinedParams.append(paramString);
						}
					} catch (UnsupportedEncodingException e) {
						Log.e(TAG,e.getMessage());
					}
				}
			}

			request = new HttpGet(url + combinedParams);
			Log.v(TAG,"URL of GET request" + url + combinedParams);
			//add headers
			for(NameValuePair h : headers){
				request.addHeader(h.getName(), h.getValue());
			}

			break;
		}
		case POST:{
			HttpPost postRequest = new HttpPost(url);

			//add headers
			for(NameValuePair h : headers){
				postRequest.addHeader(h.getName(), h.getValue());
			}

			if(!params.isEmpty()){
				try {
					postRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG,e.getMessage());
				}
			}

			request=(HttpUriRequest)postRequest;
			Log.v(TAG,"POST REQUEST" + request.getRequestLine());
			break;
		}
		}

		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();
			Log.v(TAG,"Entity"+ entity);

			if (entity != null) {
				response = convertStreamToString(entity);
			}

		} catch (ClientProtocolException e)  {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
		return response;
	}

	protected void onPostExecute(String results) {
		if (results!=null) {
			Log.v(TAG, results);
		}
	}
}