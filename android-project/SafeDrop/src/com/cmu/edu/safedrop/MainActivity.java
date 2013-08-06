package com.cmu.edu.safedrop;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmu.edu.safedrop.dto.Notification;
import com.cmu.edu.safedrop.dto.Notifications;
import com.cmu.edu.safedrop.dto.RequestMethod;
import com.cmu.edu.safedrop.dto.RequestStatus;
import com.cmu.edu.safedrop.dto.Users;
import com.cmu.edu.safedrop.dto.Volunteer;
import com.cmu.edu.safedrop.util.RestClient;
import com.cmu.edu.safedrop.util.Route;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity 
implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

	public  static final String EXTRA_MESSAGE = "message";
	private static final String TAG = "MainActivity";
	protected GoogleMap mMap;
	private LocationClient mLocationClient;
	private TextView requestStatusTextView;
	protected static String requestId;
	private RequestStatus currentRequestStatus = null;
	private Notification[] notificationList = null;
	private Button button1;
	private ImageButton button2;
	private ImageButton button3;
	private Marker selfMarker;
	private Marker otherMarker;
	private Users volunteer = null;
	public static String role = null;
	final static private long ONE_SECOND = 1000;
	final static private long TWENTY_SECONDS = ONE_SECOND * 5;
	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;
	private static LatLng selfLatLng;
	private static LatLng volLatLng;

	/** The timer. */
	private Timer timer;

	// These settings are the same as the settings for the map. They will in fact give you updates at
	// the maximal rates currently possible.
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000)         // 5 seconds
			.setFastestInterval(16)    // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setUpLocationClientIfNeeded();
		setUpMapIfNeeded();

		//set up alarm manager
		setup();

		requestStatusTextView = (TextView)findViewById(R.id.request_status);
		button1 = (Button)findViewById(R.id.button1);
		button2 = (ImageButton)findViewById(R.id.imageButton2);
		
		//notifications button
		button3 = (ImageButton)findViewById(R.id.imageButton1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * This method sets up the location client if needed
	 */
	private void setUpLocationClientIfNeeded() {
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(
					getApplicationContext(),
					this,  // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
		setUpLocationClientIfNeeded();
		mLocationClient.connect();

		if(role!=null && role.equalsIgnoreCase("Volunteer")){
			am.setRepeating( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 
					TWENTY_SECONDS,TWENTY_SECONDS, pi );
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mLocationClient != null) {
			mLocationClient.disconnect();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// Do nothing
	}

	@Override
	public void onConnected(Bundle arg0) {
		mLocationClient.requestLocationUpdates(
				REQUEST,
				this);  // LocationListener
	}

	@Override
	public void onDisconnected() {
		// Do nothing
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_done:
			if(currentRequestStatus!=null &&  role!=null && role.equalsIgnoreCase("requester") && currentRequestStatus.equals(RequestStatus.InProgress)){
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

				// set title
				alertDialogBuilder.setTitle("Close SafeDrop");

				// set dialog message
				alertDialogBuilder
				.setMessage("Have you reached your destination?")
				.setCancelable(false)
				.setPositiveButton("Reached",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, call ratings activity
						Intent intent =  new Intent(MainActivity.this,RatingActivity.class);
						intent.putExtra("getRatingsFor", ProfileActivity.user.getEmail());
						startActivity(intent);
						// set dialog message
						
					}
				})
				.setNegativeButton("Not Yet",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				})
				.setNeutralButton("Emergency",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {

						//ideally you should call emergency number associated with the contact.
						//for now 0- hardcode
						Intent intent = new Intent(Intent.ACTION_CALL);

						intent.setData(Uri.parse("tel:" + "7656378076"));
						startActivity(intent);
						dialog.cancel();
					}
				});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public void onLocationChanged(Location location) {

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		selfLatLng = new LatLng(latitude, longitude);

		// Showing the current location in Google Map
		mMap.moveCamera(CameraUpdateFactory.newLatLng(selfLatLng));

		// Zoom in the Google Map
		mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

		selfMarker = mMap.addMarker(new MarkerOptions().position(selfLatLng)
				.title("Your Location")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_marker)));
		selfMarker.showInfoWindow();

		//sendLocationToServer(latLng);
	}

	/**
	 * This method sets up the map if needed
	 */
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				mMap.setMyLocationEnabled(true);
			}
		}
	}


	/**
	 * Set up the alarm manager - it gets the request status and monitors it. 
	 */
	private void setup() {
		br = new BroadcastReceiver() {
			@Override
			public void onReceive(Context c, Intent i) {
				//Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();

				String reqStatus = getRequestStatus();

				/*for a new request status*/
				if(reqStatus.equalsIgnoreCase("N")){

					currentRequestStatus=RequestStatus.New;
					button1.setText(R.string.request_created);
					button1.setEnabled(false);
					//enable cancel button
					button2.setVisibility(View.VISIBLE);
					requestStatusTextView.setText(R.string.seekingVolunteers);
					Log.v(TAG,"Request status is new");

				}
				/*for pending status*/
				else if(reqStatus.equalsIgnoreCase("P")){
					currentRequestStatus=RequestStatus.Pending;
					button1.setText(R.string.request_pending);
					requestStatusTextView.setText(R.string.waiting);
					button1.setEnabled(false);
					button2.setVisibility(View.VISIBLE);
					Log.v(TAG,"Request status is Pending");

					/*
					 *For status accepted  
					 */
				}else if(reqStatus.equalsIgnoreCase("A")){

					currentRequestStatus = RequestStatus.Accepted;
					button1.setEnabled(false);
					button2.setVisibility(View.VISIBLE);
					button1.setText(R.string.request_accepted);
					requestStatusTextView.setText(R.string.check_notifications);
					Log.v(TAG,"Request status is Accepted");

				}
				/*For status in progress */
				else if(reqStatus.equalsIgnoreCase("I")){

					currentRequestStatus = RequestStatus.InProgress;
					button1.setEnabled(false);
					button1.setText(R.string.request_inprogress);
					button2.setVisibility(View.VISIBLE);
					button3.setImageDrawable(getResources().getDrawable(R.drawable.msg));
					button3.setVisibility(View.VISIBLE);
					button3.setEnabled(true);
					requestStatusTextView.setText(R.string.safedrop_in_session);
					Log.v(TAG,"Request status is in progress");

					String success=null;
					RestClient restClient = new RestClient();

					//get info about other user and display marker at his current location
					restClient.setUrl("http://128.2.204.85:6080/SafeDropServices/rest/service/getUserInfo/");
					restClient.addParam("email",ProfileActivity.user.getEmail());

					try {
						restClient.execute(RequestMethod.GET);
						success = restClient.get();
						Log.v(TAG,success);
						Gson gson = new Gson();
						try{
							volunteer = gson.fromJson(success, Users.class);
						}catch(JsonParseException je){
							Log.v(TAG, "Error while parsing usr object");
						}

					} catch (Exception e) {
						Log.v(TAG, "Error getting user info responded with the error" + e.getMessage());
					}

					volLatLng = new LatLng(Double.parseDouble(volunteer.getLastlat()),Double.parseDouble(volunteer.getLastlong()));
					otherMarker = mMap.addMarker(new MarkerOptions().position(volLatLng).title(volunteer.getFirstname() + "'s Location"));
					otherMarker.showInfoWindow();

					ArrayList<LatLng> points = new ArrayList<LatLng>();
					points.add(selfLatLng);
					points.add(volLatLng);

					//draw route between both the locations
					new Route().drawRoute(mMap, MainActivity.this, points, "en", true);

				}

				//request is done
				else if(reqStatus.equalsIgnoreCase("D")){
					currentRequestStatus = RequestStatus.Done;
					button1.setEnabled(false);
					button1.setText(R.string.request_done);
					button2.setVisibility(View.INVISIBLE);
					button3.setImageDrawable(getResources().getDrawable(R.drawable.msg));
					requestStatusTextView.setText(R.string.request_completed);
					Log.v(TAG,"Request status is done");

					Intent intentstop = new Intent("com.authorwjf.wakeywakey");
					PendingIntent senderstop = PendingIntent.getBroadcast(MainActivity.this, 0, intentstop, 0);
					AlarmManager alarmManagerstop = (AlarmManager) getSystemService(ALARM_SERVICE);

					alarmManagerstop.cancel(senderstop);

					//if the role is volunteer, and request is done - display alert box to say thank u
					if(role!=null && role.equalsIgnoreCase("Volunteer")){
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
						alertDialogBuilder.setTitle("SafeDrop");

						// set dialog message
						alertDialogBuilder
						.setMessage("Thank you for using SafeDrop.")
						.setCancelable(false)
						.setPositiveButton("Reached",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close activity and exit
								MainActivity.this.finish();
							}
						});
					}

				}
				//reqeust status is archived
				else if(reqStatus.equalsIgnoreCase("R")){
					currentRequestStatus = RequestStatus.Archived;
					button1.setEnabled(false);
					button1.setText(R.string.request_archived);
					button2.setVisibility(View.INVISIBLE);
					requestStatusTextView.setText(R.string.request_is_archived);
					Log.v(TAG,"Request status is archived");
				}

				//request is cancelled
				else if(reqStatus.equalsIgnoreCase("C")){
					currentRequestStatus = RequestStatus.Cancel;
					button1.setEnabled(false);
					button1.setText(R.string.request_cancelled);
					button2.setVisibility(View.INVISIBLE);
					requestStatusTextView.setText(R.string.intimating_volunteers);
					Log.v(TAG,"Request is cancelled");
				}
				else{
					currentRequestStatus = RequestStatus.Cancel;
					button1.setEnabled(false);
					button1.setText(R.string.request_pickup);
					button2.setVisibility(View.INVISIBLE);
					requestStatusTextView.setText(R.string.create_request);
					Log.v(TAG,"this is the request status " +reqStatus);
				}
			}

			/**
			 * Get the request status  by calling up the web service
			 * @return status 
			 */
			public String getRequestStatus(){
				String result=null;
				try {
					RestClient restClient = new RestClient();
					restClient.setUrl("http://128.2.204.85:6080/SafeDropServices/rest/service/getRequestStatus/");

					if(role!=null && role.equalsIgnoreCase("Volunteer")){
						restClient.addParam("requestId", ProfileActivity.requestToAccept);
					}else{
						restClient.addParam("requestId", requestId);
					}
					try {
						restClient.execute(RequestMethod.GET);
						result = restClient.get();

						Log.v(TAG, "Current status of the request is "+result);

					} catch (Exception e) {
						Log.v(TAG, "Error : getrequeststatus responded with the status code" + e.getMessage());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;

			};

		};
		registerReceiver(br, new IntentFilter("com.authorwjf.wakeywakey") );
		pi = PendingIntent.getBroadcast( this, 0, new Intent("com.authorwjf.wakeywakey"),0 );
		am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
	}




	/**
	 * This method returns zip code from user location
	 * @param userLocation
	 * @return String - zip code
	 */
	private String getZip(LatLng userLocation){
		Geocoder geocoder;
		List<Address> addresses;

		StringBuffer zip = new StringBuffer();

		try{
			geocoder = new Geocoder(this, Locale.getDefault());

			addresses = geocoder.getFromLocation(userLocation.latitude, userLocation.longitude, 1);

			zip = new StringBuffer(addresses.get(0).getPostalCode());
		}
		catch(Exception e){
			e.printStackTrace();			
		}

		return zip.toString();
	}

	/**
	 * This web service calls updates the current location of user on the server
	 * @param location
	 * @return success
	 */
	protected String sendLocationToServer(LatLng location){

		String success=null;
		RestClient restClient = new RestClient();

		restClient.setUrl("http://128.2.204.85:6080/SafeDropServices/rest/service/setUserInfo/");
		restClient.addParam("email",this.getString(R.string.EMAIL));
		restClient.addParam("zip",getZip(location));
		restClient.addParam("lastlat",String.valueOf(location.latitude));
		restClient.addParam("lastlong",String.valueOf(location.longitude));

		try {
			restClient.execute(RequestMethod.POST);
			success = restClient.get();
			Log.v(TAG,success);
		} catch (Exception e) {
			Log.v(TAG, "Error : Send Location to Server responded with the error" + e.getMessage());
		}
		return success;
	}

	/**
	 * This method cancels the pick up request
	 */
	public void cancelPickupRequestToServer(View view){

		RestClient restClient = new RestClient();
		restClient.setUrl(this.getString(R.string.CANCEL_REQUEST_SERVICE));
		restClient.addParam("requestId", requestId);

		try {
			restClient.execute(RequestMethod.POST);
			String success = restClient.get();

			Log.v(TAG,success);

			role = "Requester";
			//disable cancel button
			button2.setVisibility(View.GONE);
			currentRequestStatus = RequestStatus.Cancel;
			button1.setText(R.string.request_pickup);
			requestStatusTextView.setText(R.string.create_request);
			button1.setEnabled(true);

		} catch (Exception e) {
			Log.v(TAG, "Error :Cancel Pickup responded with error" + e.getMessage());
		}
	}

	/**
	 * This method will be called on click of request button
	 * @param view
	 */
	public void onClickRequestButton(View view){
		if(null==currentRequestStatus){
			currentRequestStatus = RequestStatus.New;
		}

		switch(currentRequestStatus){
		case New:
			createPickupRequestToServer();

			am.setRepeating( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 
					TWENTY_SECONDS,TWENTY_SECONDS, pi );
			break;
		default:
			break;
		}
	}


	@Override
	protected void onDestroy() {
		am.cancel(pi);
		unregisterReceiver(br);
		super.onDestroy();
	}

	/**
	 * This method starts the new activity and populate the notification
	 * @param view
	 */
	public void populateNotifications(View view){

		//call for notifications
		if(role!=null && currentRequestStatus.equals(RequestStatus.Accepted)
				|| (role==null)){

			Intent intent = new Intent(this, NotificationsActivity.class);
			notificationList= getNotification();

			ArrayList<Volunteer> vList =  new ArrayList<Volunteer>();

			List<Notifications> list = new ArrayList<Notifications>();

			if(notificationList!=null){
				for(int i=0;i<notificationList.length;i++){
					list = notificationList[i].getNotifications();
					for(int j=0;j<list.size();j++){
						Volunteer v = new Volunteer();
						Notifications n = new Notifications();
						n = list.get(j);
						v.setText(n.getText());
						v.setReceiver(n.getSender());
						v.setRequestId(n.getRequestid());
						vList.add(v);
					}
				}
			}

			intent.putParcelableArrayListExtra(EXTRA_MESSAGE, vList);
			// pass notification list to the new activity
			startActivity(intent);
			
		//for messages
		}else{
			Intent intent = new Intent(this, MessageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		}
	}


	/**
	 * this method fetches list of notifications from the server and returns it
	 * @return ArrayList<Notification>
	 */
	public Notification[] getNotification(){
		Log.v(TAG,"in get notification");
		Notification[] notificationsList=null;
		Notification notify;
		RestClient restClient = new RestClient();
		restClient.setUrl(this.getString(R.string.GET_NOTIFICATION_SERVICE));
		restClient.addParam("email",this.getString(R.string.EMAIL));
		restClient.addParam("afterMessageId", String.valueOf(0));

		try {
			restClient.execute(RequestMethod.GET);
			String result=restClient.get();

			Gson gson = new Gson();
			try{
				notificationsList = gson.fromJson(result, Notification[].class);
				if(null == notificationsList){
					notify = gson.fromJson(result, Notification.class);
					if(notify!=null){
						notificationsList = new Notification[1];
						notificationsList[0]= new Notification();
						notificationsList[0].setNotifications(notify.getNotifications());
					}
				}
			}catch(JsonParseException e){
				notify = gson.fromJson(result, Notification.class);
				if(notify!=null){
					notificationsList = new Notification[1];
					notificationsList[0]= new Notification();
					notificationsList[0].setNotifications(notify.getNotifications());
				}
				Log.v(TAG, "value of  notify"+notify);
			}
			catch(Exception e){

				Log.v(TAG, "error"+e.getMessage());
			}

		} catch (Exception e) {

			Log.v(TAG, "Error : getNotifications responded with the status code" + e.getMessage());
		}

		return notificationsList;
	}

	/**
	 * This method creates pick up service request to the server 
	 */
	public void createPickupRequestToServer(){

		RestClient restClient = new RestClient();
		restClient.setUrl(this.getString(R.string.CREATE_PICKUP_REQUEST_SERVICE));
		restClient.addParam("email", this.getString(R.string.EMAIL));

		try {
			restClient.execute(RequestMethod.POST);
			requestId = restClient.get();
			int code = restClient.getResponseCode();
			Log.v(TAG, String.valueOf(code));

		} catch (Exception e) {
			requestStatusTextView.setText(R.string.failed);
			Log.v(TAG, "Error : Request Pick Up responded with the status code " + e.toString());
		}finally{
			if(!requestId.equals("0")){
				currentRequestStatus=RequestStatus.New;
				button1.setText("Request Created");
				button1.setEnabled(false);

				//enable cancel button
				button2.setVisibility(View.VISIBLE);
				requestStatusTextView.setText(R.string.seekingVolunteers);
				Log.v(TAG, "request created - request id is "+ requestId);
				role="requester";
			}
		}
	}
}


