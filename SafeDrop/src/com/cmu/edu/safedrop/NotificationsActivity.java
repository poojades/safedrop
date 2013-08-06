package com.cmu.edu.safedrop;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cmu.edu.safedrop.dto.RequestMethod;
import com.cmu.edu.safedrop.dto.Volunteer;
import com.cmu.edu.safedrop.util.Adapter;
import com.cmu.edu.safedrop.util.RestClient;

public class NotificationsActivity extends Activity {
	ListView list;
	Adapter adapter;

	public static final String TEXT = "text";
	public static final String RECEIVER="receiver";
	public static final String REQUEST_TO_ACCEPT="requestid";
	private static final String TAG="NotificationsActivity";
	private static ArrayList<Volunteer> notificationList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifications);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Intent intent = getIntent();  
		notificationList = intent.getParcelableArrayListExtra("message");

		ArrayList<HashMap<String, String>> notificationMapList = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < notificationList.size(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();

			Volunteer currrent = notificationList.get(i);

			map.put(RECEIVER, currrent.getReceiver());
			map.put(TEXT, currrent.getText());
			map.put(REQUEST_TO_ACCEPT,currrent.getRequestId());

			// adding HashList to ArrayList
			notificationMapList.add(map);
		}

		list=(ListView)findViewById(R.id.listView);

		// Getting adapter by passing xml data ArrayList
		adapter=new Adapter(this, notificationMapList);
		list.setAdapter(adapter);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int i =(Integer) list.getAdapter().getItem(position);
				Volunteer  vol =  notificationList.get(i);

				String result=null;
				try {
					RestClient restClient = new RestClient();

					restClient.setUrl("http://128.2.204.85:6080/SafeDropServices/rest/service/getUserInfo/");
					restClient.addParam("email", vol.getReceiver());

					try {
						restClient.execute(RequestMethod.GET);
						result = restClient.get();

						Log.v(TAG, "Current status of the request is "+result);

					} catch (Exception e) {
						Log.v(TAG, "Error : get User Info responded with the status code" + e.getMessage());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					Intent intent = new Intent(NotificationsActivity.this, ProfileActivity.class);
					intent.putExtra("profile_info", result);
					intent.putExtra("request_Id", vol.getRequestId());
					startActivity(intent);
				}
			}
		});
	}
}
