package com.cmu.edu.safedrop.dto;


import java.util.List;

public class Notification{
   	@Override
	public String toString() {
		return "Notification [notifications=" + notifications + "]";
	}
	private List<Notifications> notifications;

 	public List<Notifications> getNotifications(){
		return this.notifications;
	}
	public void setNotifications( List<Notifications> notifications){
		this.notifications = notifications;
	}
}
