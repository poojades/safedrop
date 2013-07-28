/*
 * 
 * 
 *
 * 
 * 
 */

package edu.cmu.sd.dao.exceptions;

public class MqueueDaoException extends DaoException
{
	/**
	 * Method 'MqueueDaoException'
	 * 
	 * @param message
	 */
	public MqueueDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'MqueueDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public MqueueDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
