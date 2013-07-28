/*
 * 
 * 
 *
 * 
 * 
 */

package edu.cmu.sd.dao.jdbc;

import edu.cmu.sd.dao.dao.*;
import edu.cmu.sd.dao.factory.*;
import java.util.Date;
import edu.cmu.sd.dao.dto.*;
import edu.cmu.sd.dao.exceptions.*;
import java.sql.Connection;
import java.util.Collection;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class UsersDaoImpl extends AbstractDAO implements UsersDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( UsersDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT email, firstname, lastname, mobile, econtact, ename, status, username, lastactive, lastlat, lastlong FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( email, firstname, lastname, mobile, econtact, ename, status, username, lastactive, lastlat, lastlong ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET email = ?, firstname = ?, lastname = ?, mobile = ?, econtact = ?, ename = ?, status = ?, username = ?, lastactive = ?, lastlat = ?, lastlong = ? WHERE email = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE email = ?";

	/** 
	 * Index of column email
	 */
	protected static final int COLUMN_EMAIL = 1;

	/** 
	 * Index of column firstname
	 */
	protected static final int COLUMN_FIRSTNAME = 2;

	/** 
	 * Index of column lastname
	 */
	protected static final int COLUMN_LASTNAME = 3;

	/** 
	 * Index of column mobile
	 */
	protected static final int COLUMN_MOBILE = 4;

	/** 
	 * Index of column econtact
	 */
	protected static final int COLUMN_ECONTACT = 5;

	/** 
	 * Index of column ename
	 */
	protected static final int COLUMN_ENAME = 6;

	/** 
	 * Index of column status
	 */
	protected static final int COLUMN_STATUS = 7;

	/** 
	 * Index of column username
	 */
	protected static final int COLUMN_USERNAME = 8;

	/** 
	 * Index of column lastactive
	 */
	protected static final int COLUMN_LASTACTIVE = 9;

	/** 
	 * Index of column lastlat
	 */
	protected static final int COLUMN_LASTLAT = 10;

	/** 
	 * Index of column lastlong
	 */
	protected static final int COLUMN_LASTLONG = 11;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 11;

	/** 
	 * Index of primary-key column email
	 */
	protected static final int PK_COLUMN_EMAIL = 1;

	/** 
	 * Inserts a new row in the users table.
	 */
	public UsersPk insert(Users dto) throws UsersDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			stmt = conn.prepareStatement( SQL_INSERT );
			int index = 1;
			stmt.setString( index++, dto.getEmail() );
			stmt.setString( index++, dto.getFirstname() );
			stmt.setString( index++, dto.getLastname() );
			stmt.setString( index++, dto.getMobile() );
			stmt.setString( index++, dto.getEcontact() );
			stmt.setString( index++, dto.getEname() );
			stmt.setString( index++, dto.getStatus() );
			stmt.setString( index++, dto.getUsername() );
			stmt.setTimestamp(index++, dto.getLastactive()==null ? null : new java.sql.Timestamp( dto.getLastactive().getTime() ) );
			stmt.setString( index++, dto.getLastlat() );
			stmt.setString( index++, dto.getLastlong() );
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_INSERT + " with DTO: " + dto);
			}
		
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
			reset(dto);
			return dto.createPk();
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new UsersDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the users table.
	 */
	public void update(UsersPk pk, Users dto) throws UsersDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_UPDATE + " with DTO: " + dto);
			}
		
			stmt = conn.prepareStatement( SQL_UPDATE );
			int index=1;
			stmt.setString( index++, dto.getEmail() );
			stmt.setString( index++, dto.getFirstname() );
			stmt.setString( index++, dto.getLastname() );
			stmt.setString( index++, dto.getMobile() );
			stmt.setString( index++, dto.getEcontact() );
			stmt.setString( index++, dto.getEname() );
			stmt.setString( index++, dto.getStatus() );
			stmt.setString( index++, dto.getUsername() );
			stmt.setTimestamp(index++, dto.getLastactive()==null ? null : new java.sql.Timestamp( dto.getLastactive().getTime() ) );
			stmt.setString( index++, dto.getLastlat() );
			stmt.setString( index++, dto.getLastlong() );
			stmt.setString( 12, pk.getEmail() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new UsersDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the users table.
	 */
	public void delete(UsersPk pk) throws UsersDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_DELETE + " with PK: " + pk);
			}
		
			stmt = conn.prepareStatement( SQL_DELETE );
			stmt.setString( 1, pk.getEmail() );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new UsersDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the users table that matches the specified primary-key value.
	 */
	public Users findByPrimaryKey(UsersPk pk) throws UsersDaoException
	{
		return findByPrimaryKey( pk.getEmail() );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'email = :email'.
	 */
	public Users findByPrimaryKey(String email) throws UsersDaoException
	{
		Users ret[] = findByDynamicSelect( SQL_SELECT + " WHERE email = ?", new Object[] { email } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the users table that match the criteria ''.
	 */
	public Users[] findAll() throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY email", null );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'email = :email'.
	 */
	public Users[] findWhereEmailEquals(String email) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE email = ? ORDER BY email", new Object[] { email } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'firstname = :firstname'.
	 */
	public Users[] findWhereFirstnameEquals(String firstname) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE firstname = ? ORDER BY firstname", new Object[] { firstname } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'lastname = :lastname'.
	 */
	public Users[] findWhereLastnameEquals(String lastname) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE lastname = ? ORDER BY lastname", new Object[] { lastname } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'mobile = :mobile'.
	 */
	public Users[] findWhereMobileEquals(String mobile) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE mobile = ? ORDER BY mobile", new Object[] { mobile } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'econtact = :econtact'.
	 */
	public Users[] findWhereEcontactEquals(String econtact) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE econtact = ? ORDER BY econtact", new Object[] { econtact } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'ename = :ename'.
	 */
	public Users[] findWhereEnameEquals(String ename) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE ename = ? ORDER BY ename", new Object[] { ename } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'status = :status'.
	 */
	public Users[] findWhereStatusEquals(String status) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE status = ? ORDER BY status", new Object[] { status } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	public Users[] findWhereUsernameEquals(String username) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE username = ? ORDER BY username", new Object[] { username } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'lastactive = :lastactive'.
	 */
	public Users[] findWhereLastactiveEquals(Date lastactive) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE lastactive = ? ORDER BY lastactive", new Object[] { lastactive==null ? null : new java.sql.Timestamp( lastactive.getTime() ) } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'lastlat = :lastlat'.
	 */
	public Users[] findWhereLastlatEquals(String lastlat) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE lastlat = ? ORDER BY lastlat", new Object[] { lastlat } );
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'lastlong = :lastlong'.
	 */
	public Users[] findWhereLastlongEquals(String lastlong) throws UsersDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE lastlong = ? ORDER BY lastlong", new Object[] { lastlong } );
	}

	/**
	 * Method 'UsersDaoImpl'
	 * 
	 */
	public UsersDaoImpl()
	{
	}

	/**
	 * Method 'UsersDaoImpl'
	 * 
	 * @param userConn
	 */
	public UsersDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows)
	{
		this.maxRows = maxRows;
	}

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "safedrop.users";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected Users fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Users dto = new Users();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected Users[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			Users dto = new Users();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Users ret[] = new Users[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Users dto, ResultSet rs) throws SQLException
	{
		dto.setEmail( rs.getString( COLUMN_EMAIL ) );
		dto.setFirstname( rs.getString( COLUMN_FIRSTNAME ) );
		dto.setLastname( rs.getString( COLUMN_LASTNAME ) );
		dto.setMobile( rs.getString( COLUMN_MOBILE ) );
		dto.setEcontact( rs.getString( COLUMN_ECONTACT ) );
		dto.setEname( rs.getString( COLUMN_ENAME ) );
		dto.setStatus( rs.getString( COLUMN_STATUS ) );
		dto.setUsername( rs.getString( COLUMN_USERNAME ) );
		dto.setLastactive( rs.getTimestamp(COLUMN_LASTACTIVE ) );
		dto.setLastlat( rs.getString( COLUMN_LASTLAT ) );
		dto.setLastlong( rs.getString( COLUMN_LASTLONG ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Users dto)
	{
	}

	/** 
	 * Returns all rows from the users table that match the specified arbitrary SQL statement
	 */
	public Users[] findByDynamicSelect(String sql, Object[] sqlParams) throws UsersDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = sql;
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new UsersDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the specified arbitrary SQL statement
	 */
	public Users[] findByDynamicWhere(String sql, Object[] sqlParams) throws UsersDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = SQL_SELECT + " WHERE " + sql;
		
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL);
			}
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new UsersDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

}
