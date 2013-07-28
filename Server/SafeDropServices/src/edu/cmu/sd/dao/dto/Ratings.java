/*
 * 
 * 
 *
 * 
 * 
 */

package edu.cmu.sd.dao.dto;

import edu.cmu.sd.dao.dao.*;
import edu.cmu.sd.dao.factory.*;
import edu.cmu.sd.dao.exceptions.*;
import java.io.Serializable;
import java.util.*;

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
	 * This attribute maps to the column by in the ratings table.
	 */
	protected String by;

	/** 
	 * This attribute maps to the column for in the ratings table.
	 */
	protected String aFor;

	/** 
	 * This attribute maps to the column value in the ratings table.
	 */
	protected float value;

	/** 
	 * This attribute maps to the column text in the ratings table.
	 */
	protected String text;

	/**
	 * Method 'Ratings'
	 * 
	 */
	public Ratings()
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
	 * Method 'getBy'
	 * 
	 * @return String
	 */
	public String getBy()
	{
		return by;
	}

	/**
	 * Method 'setBy'
	 * 
	 * @param by
	 */
	public void setBy(String by)
	{
		this.by = by;
	}

	/**
	 * Method 'getAFor'
	 * 
	 * @return String
	 */
	public String getAFor()
	{
		return aFor;
	}

	/**
	 * Method 'setAFor'
	 * 
	 * @param aFor
	 */
	public void setAFor(String aFor)
	{
		this.aFor = aFor;
	}

	/**
	 * Method 'getValue'
	 * 
	 * @return float
	 */
	public float getValue()
	{
		return value;
	}

	/**
	 * Method 'setValue'
	 * 
	 * @param value
	 */
	public void setValue(float value)
	{
		this.value = value;
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
		
		if (by == null ? _cast.by != by : !by.equals( _cast.by )) {
			return false;
		}
		
		if (aFor == null ? _cast.aFor != aFor : !aFor.equals( _cast.aFor )) {
			return false;
		}
		
		if (value != _cast.value) {
			return false;
		}
		
		if (text == null ? _cast.text != text : !text.equals( _cast.text )) {
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
		_hashCode = 29 * _hashCode + requestid;
		if (by != null) {
			_hashCode = 29 * _hashCode + by.hashCode();
		}
		
		if (aFor != null) {
			_hashCode = 29 * _hashCode + aFor.hashCode();
		}
		
		_hashCode = 29 * _hashCode + Float.floatToIntBits(value);
		if (text != null) {
			_hashCode = 29 * _hashCode + text.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return RatingsPk
	 */
	public RatingsPk createPk()
	{
		return new RatingsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "edu.cmu.sd.dao.dto.Ratings: " );
		ret.append( "id=" + id );
		ret.append( ", requestid=" + requestid );
		ret.append( ", by=" + by );
		ret.append( ", aFor=" + aFor );
		ret.append( ", value=" + value );
		ret.append( ", text=" + text );
		return ret.toString();
	}

}
