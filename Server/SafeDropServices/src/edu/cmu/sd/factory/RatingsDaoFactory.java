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
 * A factory for creating RatingsDao objects.
 */
public class RatingsDaoFactory
{
	
	/**
	 * Method 'create'.
	 *
	 * @return RatingsDao
	 */
	public static RatingsDao create()
	{
		return new RatingsDaoImpl();
	}

	/**
	 * Method 'create'.
	 *
	 * @param conn the conn
	 * @return RatingsDao
	 */
	public static RatingsDao create(Connection conn)
	{
		return new RatingsDaoImpl( conn );
	}

}
