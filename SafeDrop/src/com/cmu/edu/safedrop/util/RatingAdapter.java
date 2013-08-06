package com.cmu.edu.safedrop.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.cmu.edu.safedrop.R;
import com.cmu.edu.safedrop.RatingActivity;
import com.cmu.edu.safedrop.R.id;
import com.cmu.edu.safedrop.R.layout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RatingAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater=null;

	public RatingAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data=d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if(convertView==null)
			vi = inflater.inflate(R.layout.rating_list_row, null);

		TextView rater = (TextView)vi.findViewById(R.id.rater); 
		TextView comments = (TextView)vi.findViewById(R.id.comments); 
		TextView rating = (TextView)vi.findViewById(R.id.rating); 

		HashMap<String, String> ratings = new HashMap<String, String>();
		ratings = data.get(position);

		// Setting all values in list view
		rater.setText(ratings.get(RatingActivity.RATER));
		comments.setText(ratings.get(RatingActivity.COMMENTS));
		rating.setText(ratings.get(RatingActivity.RATING));

		return vi;
	}
}