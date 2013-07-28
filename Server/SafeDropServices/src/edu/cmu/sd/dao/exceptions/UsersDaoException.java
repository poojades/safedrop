/*
 * 
 * 
 *
 * 
 * 
 */

package edu.cmu.sd.dao.exceptions;

public class UsersDaoException extends DaoException
{
	/**
	 * Method 'UsersDaoException'
	 * 
	 * @param message
	 */
	public UsersDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'UsersDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public UsersDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
