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

public class MqueueDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return MqueueDao
	 */
	public static MqueueDao create()
	{
		return new MqueueDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return MqueueDao
	 */
	public static MqueueDao create(Connection conn)
	{
		return new MqueueDaoImpl( conn );
	}

}
