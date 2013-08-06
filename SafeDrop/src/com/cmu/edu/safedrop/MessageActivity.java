package com.cmu.edu.safedrop;
import java.util.ArrayList;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cmu.edu.safedrop.dto.Message;
import com.cmu.edu.safedrop.dto.Notification;
import com.cmu.edu.safedrop.dto.Notifications;
import com.cmu.edu.safedrop.dto.RequestMethod;
import com.cmu.edu.safedrop.util.AwesomeAdapter;
import com.cmu.edu.safedrop.util.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * to use:
 * pass username to MessageActivity.sender(String)
 * pass my email to this.myEmail,replace MY_EMAIL
 * padd requestID to this.mRequestId, replace REQ_ID
 */


/**
 * MessageActivity is a main Activity to show a ListView containing Message items
 *
 */
public class MessageActivity extends ListActivity {
	
	final static String SEND_MSG_ADDR = "http://128.2.204.85:6080/SafeDropServices/rest/service/sendMessage/";
	final static String REQ_MSG_ADDR = "http://128.2.204.85:6080/SafeDropServices/rest/service/getMessages/";
/*	final static String SANKHA_EMAIL = "sankhasp@cmu.edu";
	final static String POOJA_EMAIL = "poojadesai@cmu.edu";
	final static String DONNA_EMAIL = "donnatxj@gmail.com";
*/
	
	/** Called when the activity is first created. */

	ArrayList<Message> messages;
	AwesomeAdapter adapter;
	EditText text;
	static Random rand = new Random();
	static String sender;
	
//	final static int UPDATE_INTERVAL = 10000;
	
	final static String TAG_SEND = "Sending message:";
	final static String TAG_REQ = "Requesting update";
	//final static String MY_EMAIL = "sankhasp@cmu.edu";
	
	//final static String REQ_ID = "254";// should be a member
	
	//Timer
	//private Timer mTimer;
    //private TimerTask mTimerTask;
    //private Handler mTimerHandler = new Handler();
    
    // last message id
    private String lastMessageId;
    // curent user email
    //private String myEmail;
    // requestID
    //private String mRequestId;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		text = (EditText) this.findViewById(R.id.text);
		
		//guy I am talking to
		sender = ProfileActivity.user.getFirstname();
		this.setTitle(sender);
		messages = new ArrayList<Message>();
		messages.add(new Message("Hello", false));
		messages.add(new Message("Hi!", true));
		adapter = new AwesomeAdapter(this, messages);
		setListAdapter(adapter);

		// set up last Message Id
		lastMessageId = "0";
		// set up myEmail
		//myEmail = this.getString(R.string.EMAIL);
		
		// set up handler
		//mTimerHandler = new Handler();
		
	}
	
	
	@Override
	protected void onPause(){
        super.onPause();
        // close timer and timertask
      //  this.stopTimer();
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	//    this.startTimer();
	}
	
/*	*//**
	 * stop timer
	 *//*

	private void stopTimer(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer.purge();
        }
    }*/
/*	*//**
	 * start timer
	 *//*

	private void startTimer(){
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        //TODO: update messages
                    	updateMessages();
                    	
                    }
                });
            }
        };
        mTimer.scheduleAtFixedRate(mTimerTask, 0, UPDATE_INTERVAL);
	}
	*/
	/*
	*//**
	 * to test Timer
	 *//*
	private void testTimer(){
		addNewMessage(new Message("Timer Triggers", false));
	}*/

	/**
	 * updateMessages on UI thread
	 * 
	 */
	private void updateMessages() {
		
		// get all messages from server
		Notification nMessages = this.getMessages();
		if(nMessages == null) {
			return;
		}
		// update UI
		/* clear current message list */
		messages.clear();
		/* rebuild listview */
		int messageCount = nMessages.getNotifications().size();
		for(int i = 0; i < messageCount; ++i) {
			Message aMessage;
			Notifications thisMessage = nMessages.getNotifications().get(i);
			String text = thisMessage.getText();
			String email = thisMessage.getSender();
			if(email.equals(this.getString(R.string.EMAIL)) ) {
				aMessage = new Message(text, true);
			}
			else
				aMessage = new Message(text, false);
			
			messages.add(aMessage);
			
		}
		// update view
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
		
	}

	private Notification getMessages() {

		Notification notificationsList=null;
		Notification notify;
		
		// TODO: send message to server
		String strRequestPath = REQ_MSG_ADDR;// + MainActivity.requestId + "/" + "0";
		RestClient restClient = new RestClient();
		restClient.setUrl(strRequestPath);
		restClient.addParam("afterMessageId", "0");
		
		if(MainActivity.role!=null && MainActivity.role.equalsIgnoreCase("Volunteer")){
			restClient.addParam("requestId", NotificationsActivity.REQUEST_TO_ACCEPT);//---------------------------not passing this parameter

		}else{
			restClient.addParam("requestId", MainActivity.requestId);//---------------------------not passing this parameter

		}
	
		try {
			restClient.execute(RequestMethod.GET);
			String result=restClient.get();
			int code = restClient.getResponseCode();
			Log.v(TAG_REQ, String.valueOf(code));
			Log.v(TAG_REQ, result);
			if(code == 200) {
				Log.v(TAG_REQ, "http 200 ok, successfully returned messages");
				// parse http response JSON to notifications
				
				
				Gson gson = new Gson();
				try{
					notificationsList = gson.fromJson(result, Notification.class);//IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2
					
					if(null == notificationsList){
						notify = gson.fromJson(result, Notification.class);
						if(notify!=null){
							//notificationsList = new Notification[1];
							notificationsList= new Notification();
							notificationsList.setNotifications(notify.getNotifications());
						}
					}
				}catch(JsonParseException e){
					notify = gson.fromJson(result, Notification.class); 
					if(notify!=null){
						//notificationsList = new Notification[1];
						notificationsList= new Notification();
						notificationsList.setNotifications(notify.getNotifications());
					}
					Log.v(this.TAG_REQ, "value of  notify"+notify);
				}
				catch(Exception e){

					Log.v(this.TAG_REQ, "error"+e.getMessage());
				}
				// end of parsing JSON
				
			}
			else {
				Log.v(TAG_SEND, "failed to get messages" + Integer.toString(code));
				return null;
			}

		} catch (Exception e) {			
			Log.v(TAG_SEND, "Error : Request get messages failed " + e.toString());
		}		
		
		return  notificationsList;
	}
	
	
	/**
	 * sendMessage: action of button Send
	 * @param v
	 */
	public void sendMessage(View v)
	{
		String newMessage = text.getText().toString().trim(); 
		if(newMessage.length() > 0)
		{
			text.setText("");
			addNewMessage(new Message(newMessage, true));
			// add status bubble
			addStatusBubble("Sending...");
			// send message to server
			int status = sendMessageToServer(newMessage);
			//new SendMessage().execute();
			if(status == 200) {
				addStatusBubble("Successfully sent");
			}
			else {
				addStatusBubble("Failed to send message");
			}
			
		}
	}
	
	/**
	 * addNewMessage, use this to add a line to the listview
	 * @param m
	 */
	void addNewMessage(Message m)
	{
		messages.add(m);
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
	}
	
	/**
	 * sendMessageToServer
	 * @param inputMessage
	 * @return HTTP status code
	 */
	private int sendMessageToServer (String inputMessage) {
		int result = 0;
		String requestId;
		RestClient restClient = new RestClient();
		restClient.setUrl(SEND_MSG_ADDR);
		
		if(MainActivity.role!=null && MainActivity.role.equalsIgnoreCase("Volunteer")){
			
			restClient.addParam("from", this.getString(R.string.EMAIL));
			restClient.addParam("to", ProfileActivity.user.getEmail());
			restClient.addParam("requestId", NotificationsActivity.REQUEST_TO_ACCEPT);//---------------------------not passing this parameter
		}else{
			restClient.addParam("from", this.getString(R.string.EMAIL));
			restClient.addParam("to", ProfileActivity.user.getEmail());
			restClient.addParam("requestId", MainActivity.requestId);//---------------------------not passing this parameter	
		}
	
		restClient.addParam("message", inputMessage);

		try {
			restClient.execute(RequestMethod.POST);
			requestId = restClient.get();
			int code = restClient.getResponseCode();
			Log.v(TAG_SEND, String.valueOf(code));
			Log.v(TAG_SEND, requestId);
			if(code == 200) {
				result = 200;
				Log.v(TAG_SEND, "http 200 ok, sent successfully");
			}
			else {
				Log.v(TAG_SEND, "failed to send message" + Integer.toString(code));
				return code;
			}

		} catch (Exception e) {			
			Log.v(TAG_SEND, "Error : Request Pick Up responded with the status code " + e.toString());
		}
	return result;
	}
	
	/**
	 * addStatusBubble: add a status on the 
	 * @param status
	 */
	private void addStatusBubble (String status) {
		if(messages.get(messages.size()-1).isStatusMessage)//check wether we have already added a status message
		{
			messages.get(messages.size()-1).setMessage(status); //update the status for that
			adapter.notifyDataSetChanged(); 
			getListView().setSelection(messages.size()-1);
		}
		else{
			addNewMessage(new Message(true,status, true)); //last param: boolean isMine
		}
	}
}