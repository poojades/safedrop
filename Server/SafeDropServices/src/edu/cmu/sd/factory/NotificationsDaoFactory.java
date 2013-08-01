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
 * A factory for creating NotificationsDao objects.
 */
public class NotificationsDaoFactory
{
	
	/**
	 * Method 'create'.
	 *
	 * @return NotificationsDao
	 */
	public static NotificationsDao create()
	{
		return new NotificationsDaoImpl();
	}

	/**
	 * Method 'create'.
	 *
	 * @param conn the conn
	 * @return NotificationsDao
	 */
	public static NotificationsDao create(Connection conn)
	{
		return new NotificationsDaoImpl( conn );
	}

}
