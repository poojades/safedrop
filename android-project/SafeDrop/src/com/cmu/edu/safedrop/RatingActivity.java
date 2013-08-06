package com.cmu.edu.safedrop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;

import com.cmu.edu.safedrop.dto.Rating;
import com.cmu.edu.safedrop.dto.Ratings;
import com.cmu.edu.safedrop.dto.RequestMethod;
import com.cmu.edu.safedrop.util.RatingAdapter;
import com.cmu.edu.safedrop.util.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class RatingActivity extends Activity {

	public static final String RATER = "rater";
	public static final String COMMENTS="comments";
	public static final String RATING="rating";
	private static final String TAG="RatingActivity";
	RatingAdapter adapter;
	ListView listview;
	private Rating[] ratingList;
	private Rating rate;
	EditText comments;
	ImageButton enter;
	RatingBar value;
	ArrayList<Ratings> list = null;
	ArrayList<HashMap<String, String>> ratingMapList =null;
	String email = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifications);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		/*Intent intent = getIntent();
		email = intent.getStringExtra("getRatingsFor");

		comments=(EditText)findViewById(R.id.editText1);
		enter=(ImageButton)findViewById(R.id.enter);
		value=(RatingBar)findViewById(R.id.ratingBar1);

		//close the reqeust
		
		RestClient restClient = new RestClient();

		restClient.setUrl("http://128.2.204.85:6080/SafeDropServices/rest/service/closeRequest");
		restClient.addParam("requestId", MainActivity.requestId);

		try {
			restClient.execute(RequestMethod.POST);
			String result = restClient.get();
			Log.v(TAG, "Current status of the request is "+result);

		} catch (Exception e) {
			Log.v(TAG, "Error : get Ratings responded with the status code" + e.getMessage());
		}

		
		//get the ratings
		String result=null;
		try {
			
			restClient.setUrl("http://128.2.204.85:6080/SafeDropServices/rest/service/getRatings/");
			restClient.addParam("email", email);

			try {
				restClient.execute(RequestMethod.GET);
				result = restClient.get();
				Gson gson = new Gson();
				try{
					ratingList = gson.fromJson(result, Rating[].class);
					if(null == ratingList){
						rate = gson.fromJson(result, Rating.class);
						if(rate!=null){
							ratingList = new Rating[1];
							ratingList[0]= new Rating();
							ratingList[0].setRatings(rate.getRatings());
						}
					}
				}catch(JsonParseException e){
					rate = gson.fromJson(result, Rating.class);
					if(rate!=null){
						ratingList = new Rating[1];
						ratingList[0]= new Rating();
						ratingList[0].setRatings(rate.getRatings());
					}
					Log.v(TAG, "value of  rate"+rate);
				}
				Log.v(TAG, "Current status of the request is "+result);

			} catch (Exception e) {
				Log.v(TAG, "Error : get Ratings responded with the status code" + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		ratingMapList = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < ratingList.length; i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();

			Rating currrent = ratingList[i];
			ArrayList<Ratings> list = (ArrayList<Ratings>)currrent.getRatings();
			for(int j=0;j<list.size();j++){

				Ratings obj = list.get(j);
				map.put(RATER, obj.getRequester());
				map.put(COMMENTS, obj.getText());
				map.put(RATING,obj.getValue());

				// adding HashList to ArrayList
				ratingMapList.add(map);
			}
		}

		listview=(ListView)findViewById(R.id.listView1);

		// Getting adapter by passing xml data ArrayList
		adapter=new RatingAdapter(this, ratingMapList);
		listview.setAdapter(adapter);

		// Click event for single list row
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	public void updateRating(View view){
		String text = comments.getText().toString();
		String outOfFiveRating = String.valueOf(value.getRating());
		comments.setText("");

		HashMap<String, String> map = new HashMap<String, String>();

		map.put(RATER, this.getString(R.string.EMAIL));
		map.put(COMMENTS, text);
		map.put(RATING,outOfFiveRating);

		adapter.notifyDataSetChanged();

		RestClient restClient = new RestClient();

		restClient.setUrl("http://128.2.204.85:6080/SafeDropServices/rest/service/addRating/");
		restClient.addParam("requestId", MainActivity.requestId);
		restClient.addParam("requesterEmail", this.getString(R.string.EMAIL));
		restClient.addParam("volunteerEmail", email );
		restClient.addParam("text", text);
		restClient.addParam("value", outOfFiveRating);

		try {
			restClient.execute(RequestMethod.POST);
			String result = restClient.get();

		}catch (InterruptedException e) {
			Log.v(TAG, "error in RatingActivity while updating the rating"+e.getMessage());
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.v(TAG, "error in RatingActivity while updating the rating"+e.getMessage());
			e.printStackTrace();
		}*/
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("SafeDrop");

		// set dialog message
		alertDialogBuilder
		.setMessage("Thank you for using SafeDrop.")
		.setCancelable(false)
		.setPositiveButton("Close",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close activity and exit
				RatingActivity.this.finish();
			}
		});
		alertDialogBuilder.show();
	}
	
}
