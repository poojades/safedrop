package com.cmu.edu.safedrop.dto;


public class Notifications{
   	@Override
	public String toString() {
		return "Notifications [created=" + created + ", id=" + id
				+ ", receiver=" + receiver + ", requestid=" + requestid
				+ ", sender=" + sender + ", text=" + text + ", type=" + type
				+ "]";
	}
	private String created;
   	private String id;
   	private String receiver;
   	private String requestid;
   	private String sender;
   	private String text;
   	private String type;

 	public String getCreated(){
		return this.created;
	}
	public void setCreated(String created){
		this.created = created;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getReceiver(){
		return this.receiver;
	}
	public void setReceiver(String receiver){
		this.receiver = receiver;
	}
 	public String getRequestid(){
		return this.requestid;
	}
	public void setRequestid(String requestid){
		this.requestid = requestid;
	}
 	public String getSender(){
		return this.sender;
	}
	public void setSender(String sender){
		this.sender = sender;
	}
 	public String getText(){
		return this.text;
	}
	public void setText(String text){
		this.text = text;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
}
