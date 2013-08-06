package com.cmu.edu.safedrop;

import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.cmu.edu.safedrop.dto.RequestMethod;
import com.cmu.edu.safedrop.dto.Users;
import com.cmu.edu.safedrop.util.RestClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class ProfileActivity extends FragmentActivity 
implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {


	private static String TAG = "ProfileActivity";
	private TextView name;
	private TextView phone;
	private TextView address;
	private TextView eta;
	protected GoogleMap mMap;
	private LocationClient mLocationClient;
	private Marker otherMarker;
	public static Users user =null;
	public static String requestToAccept = null;

	
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000)         // 5 seconds
			.setFastestInterval(16)    // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		setUpLocationClientIfNeeded();
		setUpMapIfNeeded();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Intent intent = getIntent();  
		String userProfile = intent.getStringExtra("profile_info");
		requestToAccept = intent.getStringExtra("request_Id");
		Gson gson = new Gson();
		try{
			user = gson.fromJson(userProfile, Users.class);
		}catch(JsonParseException je){
			Log.v(TAG, "Error while parsing usr object");
		}

		name=(TextView)findViewById(R.id.name);
		phone=(TextView)findViewById(R.id.phone_number);
		address=(TextView)findViewById(R.id.address);
	
		name.setText(user.getFirstname()+ " " +user.getLastname());
		phone.setText(user.getMobile());

		String lastSeen = getAddress(user.getLastlat(),user.getLastlong());
		address.setText(lastSeen);

		LatLng userLocation =  new LatLng(Double.parseDouble(user.getLastlat()), Double.parseDouble(user.getLastlong()));

		mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));


		otherMarker = mMap.addMarker(new MarkerOptions().position(userLocation)
				.title(user.getFirstname()+"'s Location"));
		otherMarker.showInfoWindow();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/**
	 * This method sets up the map if needed
	 */
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_1))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				mMap.setMyLocationEnabled(true);

			}
		}
	}

	/**
	 * This method gets the address of user from lat-long
	 * @param userLocation
	 * @return String address
	 */
	private String getAddress(String lat, String lang){
		Geocoder geocoder;
		List<Address> addresses;

		StringBuffer address = new StringBuffer();
		StringBuffer city = new StringBuffer();
		StringBuffer country = new StringBuffer();

		try{
			geocoder = new Geocoder(this, Locale.getDefault());

			addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lang), 1);

			address = new StringBuffer(addresses.get(0).getAddressLine(0));
			city = new StringBuffer(addresses.get(0).getAddressLine(1));
			country = new StringBuffer(addresses.get(0).getAddressLine(2));
			Log.v(TAG, city.toString());
			Log.v(TAG, country.toString());

		}
		catch(Exception e){
			e.printStackTrace();			
		}

		return address.append(" "+city).toString();
	}

	public void backToMainActivity(View view){

		String result=null;
		try {
			RestClient restClient = new RestClient();
			
			if(null == MainActivity.requestId){
				restClient.setUrl("http://128.2.204.85:6080/SafeDropServices/rest/service/acceptRequester/");
				restClient.addParam("volunteerEmail", this.getString(R.string.EMAIL));
				restClient.addParam("requestId", requestToAccept);
				MainActivity.role="Volunteer";
			}else{
				restClient.setUrl("http://128.2.204.85:6080/SafeDropServices/rest/service/acceptVolunteer/");
				restClient.addParam("volunteerEmail", user.getEmail());
				restClient.addParam("requestId", MainActivity.requestId);
			}
		
			try {
				restClient.execute(RequestMethod.POST);
				result = restClient.get();

				Log.v(TAG, "Current status of the request is "+result);

			} catch (Exception e) {
				Log.v(TAG, "Error : Request Pick Up responded with the status code" + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Intent intent =  new Intent(this,MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent); 
	}

	@Override
	public void onLocationChanged(Location location) {

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		// Showing the current location in Google Map
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

	}
}

