/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */

package edu.cmu.sd.factory;

import java.sql.Connection;
import edu.cmu.sd.dao.*;
import edu.cmu.sd.jdbc.*;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating MessagesDao objects.
 */
public class MessagesDaoFactory
{
	
	/**
	 * Method 'create'.
	 *
	 * @return MessagesDao
	 */
	public static MessagesDao create()
	{
		return new MessagesDaoImpl();
	}

	/**
	 * Method 'create'.
	 *
	 * @param conn the conn
	 * @return MessagesDao
	 */
	public static MessagesDao create(Connection conn)
	{
		return new MessagesDaoImpl( conn );
	}

}
