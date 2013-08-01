/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */

package edu.cmu.sd.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class DaoException.
 */
public class DaoException extends Exception
{
	
	/** The throwable. */
	protected Throwable throwable;

	/**
	 * Method 'DaoException'.
	 *
	 * @param message the message
	 */
	public DaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'DaoException'.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public DaoException(String message, Throwable throwable)
	{
		super(message);
		this.throwable = throwable;
	}

	/**
	 * Method 'getCause'.
	 *
	 * @return Throwable
	 */
	public Throwable getCause()
	{
		return throwable;
	}

}
