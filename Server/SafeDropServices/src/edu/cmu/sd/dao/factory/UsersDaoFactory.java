/*
 * 
 * 
 *
 * 
 * 
 */

package edu.cmu.sd.dao.factory;

import java.sql.Connection;
import edu.cmu.sd.dao.dao.*;
import edu.cmu.sd.dao.jdbc.*;

public class UsersDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return UsersDao
	 */
	public static UsersDao create()
	{
		return new UsersDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return UsersDao
	 */
	public static UsersDao create(Connection conn)
	{
		return new UsersDaoImpl( conn );
	}

}
