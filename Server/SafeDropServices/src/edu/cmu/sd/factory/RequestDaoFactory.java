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
 * A factory for creating RequestDao objects.
 */
public class RequestDaoFactory
{
	
	/**
	 * Method 'create'.
	 *
	 * @return RequestDao
	 */
	public static RequestDao create()
	{
		return new RequestDaoImpl();
	}

	/**
	 * Method 'create'.
	 *
	 * @param conn the conn
	 * @return RequestDao
	 */
	public static RequestDao create(Connection conn)
	{
		return new RequestDaoImpl( conn );
	}

}
