/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */

package edu.cmu.sd.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/** 
 * This class represents the primary key of the ratings table.
 */
@XmlRootElement
public class RatingsPk implements Serializable
{
	
	/** The id. */
	protected int id;

	/** 
	 * This attribute represents whether the primitive attribute id is null.
	 */
	protected boolean idNull;

	/**
	 * Sets the value of id.
	 *
	 * @param id the new id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Gets the value of id.
	 *
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Method 'RatingsPk'.
	 */
	public RatingsPk()
	{
	}

	/**
	 * Method 'RatingsPk'.
	 *
	 * @param id the id
	 */
	public RatingsPk(final int id)
	{
		this.id = id;
	}

	/**
	 * Sets the value of idNull.
	 *
	 * @param idNull the new id null
	 */
	public void setIdNull(boolean idNull)
	{
		this.idNull = idNull;
	}

	/**
	 * Gets the value of idNull.
	 *
	 * @return true, if is id null
	 */
	public boolean isIdNull()
	{
		return idNull;
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
		
		if (!(_other instanceof RatingsPk)) {
			return false;
		}
		
		final RatingsPk _cast = (RatingsPk) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (idNull != _cast.idNull) {
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
		_hashCode = 29 * _hashCode + (idNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'toString'.
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "edu.cmu.sd.dto.RatingsPk: " );
		ret.append( "id=" + id );
		return ret.toString();
	}

}
