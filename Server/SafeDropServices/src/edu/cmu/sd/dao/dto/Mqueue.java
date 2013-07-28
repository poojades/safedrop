/*
 * 
 * 
 *
 * 
 * 
 */

package edu.cmu.sd.dao.dto;

import java.io.Serializable;

public class Mqueue implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * This attribute maps to the column id in the mqueue table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column type in the mqueue table.
	 */
	protected String type;

	/** 
	 * This attribute maps to the column text in the mqueue table.
	 */
	protected String text;

	/** 
	 * This attribute maps to the column from in the mqueue table.
	 */
	protected String from;

	/** 
	 * This attribute maps to the column to in the mqueue table.
	 */
	protected String to;

	/** 
	 * This attribute maps to the column requestid in the mqueue table.
	 */
	protected int requestid;

	/**
	 * Method 'Mqueue'
	 * 
	 */
	public Mqueue()
	{
	}

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Method 'getType'
	 * 
	 * @return String
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Method 'setType'
	 * 
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Method 'getText'
	 * 
	 * @return String
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Method 'setText'
	 * 
	 * @param text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Method 'getFrom'
	 * 
	 * @return String
	 */
	public String getFrom()
	{
		return from;
	}

	/**
	 * Method 'setFrom'
	 * 
	 * @param from
	 */
	public void setFrom(String from)
	{
		this.from = from;
	}

	/**
	 * Method 'getTo'
	 * 
	 * @return String
	 */
	public String getTo()
	{
		return to;
	}

	/**
	 * Method 'setTo'
	 * 
	 * @param to
	 */
	public void setTo(String to)
	{
		this.to = to;
	}

	/**
	 * Method 'getRequestid'
	 * 
	 * @return int
	 */
	public int getRequestid()
	{
		return requestid;
	}

	/**
	 * Method 'setRequestid'
	 * 
	 * @param requestid
	 */
	public void setRequestid(int requestid)
	{
		this.requestid = requestid;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
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
		
		if (!(_other instanceof Mqueue)) {
			return false;
		}
		
		final Mqueue _cast = (Mqueue) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (type == null ? _cast.type != type : !type.equals( _cast.type )) {
			return false;
		}
		
		if (text == null ? _cast.text != text : !text.equals( _cast.text )) {
			return false;
		}
		
		if (from == null ? _cast.from != from : !from.equals( _cast.from )) {
			return false;
		}
		
		if (to == null ? _cast.to != to : !to.equals( _cast.to )) {
			return false;
		}
		
		if (requestid != _cast.requestid) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		if (type != null) {
			_hashCode = 29 * _hashCode + type.hashCode();
		}
		
		if (text != null) {
			_hashCode = 29 * _hashCode + text.hashCode();
		}
		
		if (from != null) {
			_hashCode = 29 * _hashCode + from.hashCode();
		}
		
		if (to != null) {
			_hashCode = 29 * _hashCode + to.hashCode();
		}
		
		_hashCode = 29 * _hashCode + requestid;
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return MqueuePk
	 */
	public MqueuePk createPk()
	{
		return new MqueuePk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "edu.cmu.sd.dao.dto.Mqueue: " );
		ret.append( "id=" + id );
		ret.append( ", type=" + type );
		ret.append( ", text=" + text );
		ret.append( ", from=" + from );
		ret.append( ", to=" + to );
		ret.append( ", requestid=" + requestid );
		return ret.toString();
	}

}
