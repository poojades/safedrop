/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package edu.cmu.sd.factory;

import java.sql.Connection;
import edu.cmu.sd.dao.*;
import edu.cmu.sd.jdbc.*;

public class NotificationsDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return NotificationsDao
	 */
	public static NotificationsDao create()
	{
		return new NotificationsDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return NotificationsDao
	 */
	public static NotificationsDao create(Connection conn)
	{
		return new NotificationsDaoImpl( conn );
	}

}
