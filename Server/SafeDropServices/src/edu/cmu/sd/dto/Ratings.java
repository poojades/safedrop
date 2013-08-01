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
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Ratings.
 */
public class Ratings implements Serializable
{
	/** 
	 * This attribute maps to the column id in the ratings table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column requestid in the ratings table.
	 */
	protected int requestid;

	/** 
	 * This attribute maps to the column requester in the ratings table.
	 */
	protected String requester;

	/** 
	 * This attribute maps to the column volunteer in the ratings table.
	 */
	protected String volunteer;

	/** 
	 * This attribute maps to the column value in the ratings table.
	 */
	protected float value;

	/** 
	 * This attribute maps to the column text in the ratings table.
	 */
	protected String text;

	/** 
	 * This attribute maps to the column created in the ratings table.
	 */
	protected Date created;

	/**
	 * Method 'Ratings'.
	 */
	public Ratings()
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
	 * Method 'getRequester'.
	 *
	 * @return String
	 */
	public String getRequester()
	{
		return requester;
	}

	/**
	 * Method 'setRequester'.
	 *
	 * @param requester the new requester
	 */
	public void setRequester(String requester)
	{
		this.requester = requester;
	}

	/**
	 * Method 'getVolunteer'.
	 *
	 * @return String
	 */
	public String getVolunteer()
	{
		return volunteer;
	}

	/**
	 * Method 'setVolunteer'.
	 *
	 * @param volunteer the new volunteer
	 */
	public void setVolunteer(String volunteer)
	{
		this.volunteer = volunteer;
	}

	/**
	 * Method 'getValue'.
	 *
	 * @return float
	 */
	public float getValue()
	{
		return value;
	}

	/**
	 * Method 'setValue'.
	 *
	 * @param value the new value
	 */
	public void setValue(float value)
	{
		this.value = value;
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
		
		if (!(_other instanceof Ratings)) {
			return false;
		}
		
		final Ratings _cast = (Ratings) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (requestid != _cast.requestid) {
			return false;
		}
		
		if (requester == null ? _cast.requester != requester : !requester.equals( _cast.requester )) {
			return false;
		}
		
		if (volunteer == null ? _cast.volunteer != volunteer : !volunteer.equals( _cast.volunteer )) {
			return false;
		}
		
		if (value != _cast.value) {
			return false;
		}
		
		if (text == null ? _cast.text != text : !text.equals( _cast.text )) {
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
		_hashCode = 29 * _hashCode + requestid;
		if (requester != null) {
			_hashCode = 29 * _hashCode + requester.hashCode();
		}
		
		if (volunteer != null) {
			_hashCode = 29 * _hashCode + volunteer.hashCode();
		}
		
		_hashCode = 29 * _hashCode + Float.floatToIntBits(value);
		if (text != null) {
			_hashCode = 29 * _hashCode + text.hashCode();
		}
		
		if (created != null) {
			_hashCode = 29 * _hashCode + created.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'.
	 *
	 * @return RatingsPk
	 */
	public RatingsPk createPk()
	{
		return new RatingsPk(id);
	}

	/**
	 * Method 'toString'.
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "edu.cmu.sd.dto.Ratings: " );
		ret.append( "id=" + id );
		ret.append( ", requestid=" + requestid );
		ret.append( ", requester=" + requester );
		ret.append( ", volunteer=" + volunteer );
		ret.append( ", value=" + value );
		ret.append( ", text=" + text );
		ret.append( ", created=" + created );
		return ret.toString();
	}

}
