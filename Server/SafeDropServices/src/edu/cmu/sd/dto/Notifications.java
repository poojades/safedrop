/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */

package edu.cmu.sd.dto;

import edu.cmu.sd.dao.*;
import edu.cmu.sd.factory.*;
import edu.cmu.sd.exceptions.*;

import java.io.Serializable;
import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class Notifications.
 */

@XmlRootElement
public class Notifications implements Serializable
{
	/** 
	 * This attribute maps to the column id in the notifications table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column text in the notifications table.
	 */
	protected String text;

	/** 
	 * This attribute maps to the column requestid in the notifications table.
	 */
	protected int requestid;

	/** 
	 * This attribute maps to the column created in the notifications table.
	 */
	protected Date created;

	/** 
	 * This attribute maps to the column receiver in the notifications table.
	 */
	protected String receiver;

	/** 
	 * This attribute maps to the column sender in the notifications table.
	 */
	protected String sender;

	/** 
	 * This attribute maps to the column type in the notifications table.
	 */
	protected String type;

	/**
	 * Method 'Notifications'.
	 */
	public Notifications()
	{
	}

	/**
	 * Method 'getId'.
	 *
	 * @return int
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Method 'setId'.
	 *
	 * @param id the new id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Method 'getText'.
	 *
	 * @return String
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Method 'setText'.
	 *
	 * @param text the new text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Method 'getRequestid'.
	 *
	 * @return int
	 */
	public int getRequestid()
	{
		return requestid;
	}

	/**
	 * Method 'setRequestid'.
	 *
	 * @param requestid the new requestid
	 */
	public void setRequestid(int requestid)
	{
		this.requestid = requestid;
	}

	/**
	 * Method 'getCreated'.
	 *
	 * @return Date
	 */
	public Date getCreated()
	{
		return created;
	}

	/**
	 * Method 'setCreated'.
	 *
	 * @param created the new created
	 */
	public void setCreated(Date created)
	{
		this.created = created;
	}

	/**
	 * Method 'getReceiver'.
	 *
	 * @return String
	 */
	public String getReceiver()
	{
		return receiver;
	}

	/**
	 * Method 'setReceiver'.
	 *
	 * @param receiver the new receiver
	 */
	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	/**
	 * Method 'getSender'.
	 *
	 * @return String
	 */
	public String getSender()
	{
		return sender;
	}

	/**
	 * Method 'setSender'.
	 *
	 * @param sender the new sender
	 */
	public void setSender(String sender)
	{
		this.sender = sender;
	}

	/**
	 * Method 'getType'.
	 *
	 * @return String
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Method 'setType'.
	 *
	 * @param type the new type
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Method 'equals'.
	 *
	 * @param _other the _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof Notifications)) {
			return false;
		}
		
		final Notifications _cast = (Notifications) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (text == null ? _cast.text != text : !text.equals( _cast.text )) {
			return false;
		}
		
		if (requestid != _cast.requestid) {
			return false;
		}
		
		if (created == null ? _cast.created != created : !created.equals( _cast.created )) {
			return false;
		}
		
		if (receiver == null ? _cast.receiver != receiver : !receiver.equals( _cast.receiver )) {
			return false;
		}
		
		if (sender == null ? _cast.sender != sender : !sender.equals( _cast.sender )) {
			return false;
		}
		
		if (type == null ? _cast.type != type : !type.equals( _cast.type )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'.
	 *
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		if (text != null) {
			_hashCode = 29 * _hashCode + text.hashCode();
		}
		
		_hashCode = 29 * _hashCode + requestid;
		if (created != null) {
			_hashCode = 29 * _hashCode + created.hashCode();
		}
		
		if (receiver != null) {
			_hashCode = 29 * _hashCode + receiver.hashCode();
		}
		
		if (sender != null) {
			_hashCode = 29 * _hashCode + sender.hashCode();
		}
		
		if (type != null) {
			_hashCode = 29 * _hashCode + type.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'.
	 *
	 * @return NotificationsPk
	 */
	public NotificationsPk createPk()
	{
		return new NotificationsPk(id);
	}

	/**
	 * Method 'toString'.
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "edu.cmu.sd.dto.Notifications: " );
		ret.append( "id=" + id );
		ret.append( ", text=" + text );
		ret.append( ", requestid=" + requestid );
		ret.append( ", created=" + created );
		ret.append( ", receiver=" + receiver );
		ret.append( ", sender=" + sender );
		ret.append( ", type=" + type );
		return ret.toString();
	}

}
