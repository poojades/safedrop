package com.cmu.edu.safedrop.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cmu.edu.safedrop.NotificationsActivity;
import com.cmu.edu.safedrop.R;
 
public class Adapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
   
    public Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView volunteer = (TextView)vi.findViewById(R.id.volunteer); 
        TextView status = (TextView)vi.findViewById(R.id.accept_status); 
        
        HashMap<String, String> notification = new HashMap<String, String>();
        notification = data.get(position);
 
        // Setting all values in list view
        volunteer.setText(notification.get(NotificationsActivity.TEXT));
        status.setText(notification.get(NotificationsActivity.RECEIVER));
    
        return vi;
    }
}