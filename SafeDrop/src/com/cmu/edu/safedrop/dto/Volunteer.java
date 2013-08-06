package com.cmu.edu.safedrop.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class Volunteer implements Parcelable{


	private String text;
	private String receiver;
	private String requestId;
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Volunteer(){
		
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getText() {
		return text;
	}

	public String getReceiver() {
		return receiver;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(text);
		dest.writeString(receiver);
		dest.writeString(requestId);

	}

	public Volunteer(Parcel source){
		/*
		 * Reconstruct from the Parcel
		 */
		text = source.readString();
		receiver = source.readString();
		requestId = source.readString();
	}

	public static final Creator<Volunteer> CREATOR = new Creator<Volunteer>() {
		public Volunteer createFromParcel(Parcel source) {
			return new Volunteer(source);
		}
		public Volunteer[] newArray(int size) {
			return new Volunteer[size];
		}
	};

}
