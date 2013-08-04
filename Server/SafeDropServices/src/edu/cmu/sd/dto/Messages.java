/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */

package edu.cmu.sd.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class Messages.
 */
@XmlRootElement
public class Messages implements Serializable
{
	/** 
	 * This attribute maps to the column id in the messages table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column text in the messages table.
	 */
	protected String text;

	/** 
	 * This attribute maps to the column sender in the messages table.
	 */
	protected String sender;

	/** 
	 * This attribute maps to the column receiver in the messages table.
	 */
	protected String receiver;

	/** 
	 * This attribute maps to the column created in the messages table.
	 */
	protected Date created;

	/**
	 * Method 'Messages'.
	 */
	public Messages()
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
		
		if (!(_other instanceof Messages)) {
			return false;
		}
		
		final Messages _cast = (Messages) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (text == null ? _cast.text != text : !text.equals( _cast.text )) {
			return false;
		}
		
		if (sender == null ? _cast.sender != sender : !sender.equals( _cast.sender )) {
			return false;
		}
		
		if (receiver == null ? _cast.receiver != receiver : !receiver.equals( _cast.receiver )) {
			return false;
		}
		
		if (created == null ? _cast.created != created : !created.equals( _cast.created )) {
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
		
		if (sender != null) {
			_hashCode = 29 * _hashCode + sender.hashCode();
		}
		
		if (receiver != null) {
			_hashCode = 29 * _hashCode + receiver.hashCode();
		}
		
		if (created != null) {
			_hashCode = 29 * _hashCode + created.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'.
	 *
	 * @return MessagesPk
	 */
	public MessagesPk createPk()
	{
		return new MessagesPk(id);
	}

	/**
	 * Method 'toString'.
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "edu.cmu.sd.dto.Messages: " );
		ret.append( "id=" + id );
		ret.append( ", text=" + text );
		ret.append( ", sender=" + sender );
		ret.append( ", receiver=" + receiver );
		ret.append( ", created=" + created );
		return ret.toString();
	}

}