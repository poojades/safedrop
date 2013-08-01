/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.jdbc;

import java.sql.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceManager.
 */
public class ResourceManager
{
    
    /** The jdbc driver. */
    private static String JDBC_DRIVER   = "com.mysql.jdbc.Driver";
    
    /** The jdbc url. */
    private static String JDBC_URL      = "jdbc:mysql://128.2.204.85:4406/mysql";

    /** The jdbc user. */
    private static String JDBC_USER     = "safedrop";
    
    /** The jdbc password. */
    private static String JDBC_PASSWORD = "safedrop";

    /** The driver. */
    private static Driver driver = null;

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws SQLException the sQL exception
     */
    public static synchronized Connection getConnection()
	throws SQLException
    {
        if (driver == null)
        {
            try
            {
                Class jdbcDriverClass = Class.forName( JDBC_DRIVER );
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver( driver );
            }
            catch (Exception e)
            {
                System.out.println( "Failed to initialise JDBC driver" );
                e.printStackTrace();
            }
        }

        return DriverManager.getConnection(
                JDBC_URL,
                JDBC_USER,
                JDBC_PASSWORD
        );
    }


	/**
	 * Close.
	 *
	 * @param conn the conn
	 */
	public static void close(Connection conn)
	{
		try {
			if (conn != null) conn.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	/**
	 * Close.
	 *
	 * @param stmt the stmt
	 */
	public static void close(PreparedStatement stmt)
	{
		try {
			if (stmt != null) stmt.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	/**
	 * Close.
	 *
	 * @param rs the rs
	 */
	public static void close(ResultSet rs)
	{
		try {
			if (rs != null) rs.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}

	}

}
