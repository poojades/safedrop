/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */

package edu.cmu.sd.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

// TODO: Auto-generated Javadoc
/** 
 * This class represents the primary key of the users table.
 */
public class UsersPk implements Serializable
{
	
	/** The email. */
	protected String email;

	/**
	 * Sets the value of email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Gets the value of email.
	 *
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Method 'UsersPk'.
	 */
	public UsersPk()
	{
	}

	/**
	 * Method 'UsersPk'.
	 *
	 * @param email the email
	 */
	public UsersPk(final String email)
	{
		this.email = email;
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
		
		if (!(_other instanceof UsersPk)) {
			return false;
		}
		
		final UsersPk _cast = (UsersPk) _other;
		if (email == null ? _cast.email != email : !email.equals( _cast.email )) {
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
		if (email != null) {
			_hashCode = 29 * _hashCode + email.hashCode();
		}
		
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
		ret.append( "edu.cmu.sd.dto.UsersPk: " );
		ret.append( "email=" + email );
		return ret.toString();
	}

}
