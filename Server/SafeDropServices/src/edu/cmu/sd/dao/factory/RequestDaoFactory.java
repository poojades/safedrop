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

public class RequestDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return RequestDao
	 */
	public static RequestDao create()
	{
		return new RequestDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return RequestDao
	 */
	public static RequestDao create(Connection conn)
	{
		return new RequestDaoImpl( conn );
	}

}
