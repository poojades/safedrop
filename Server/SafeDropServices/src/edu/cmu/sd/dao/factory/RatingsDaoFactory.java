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

public class RatingsDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return RatingsDao
	 */
	public static RatingsDao create()
	{
		return new RatingsDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return RatingsDao
	 */
	public static RatingsDao create(Connection conn)
	{
		return new RatingsDaoImpl( conn );
	}

}
