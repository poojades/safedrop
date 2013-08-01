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
 * A factory for creating UsersDao objects.
 */
public class UsersDaoFactory
{
	
	/**
	 * Method 'create'.
	 *
	 * @return UsersDao
	 */
	public static UsersDao create()
	{
		return new UsersDaoImpl();
	}

	/**
	 * Method 'create'.
	 *
	 * @param conn the conn
	 * @return UsersDao
	 */
	public static UsersDao create(Connection conn)
	{
		return new UsersDaoImpl( conn );
	}

}
