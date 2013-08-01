/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */

package edu.cmu.sd.jdbc;

import edu.cmu.sd.dao.*;
import edu.cmu.sd.factory.*;
import java.util.Date;
import edu.cmu.sd.dto.*;
import edu.cmu.sd.exceptions.*;
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

// TODO: Auto-generated Javadoc
/**
 * The Class RatingsDaoImpl.
 */
public class RatingsDaoImpl extends AbstractDAO implements RatingsDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger( RatingsDaoImpl.class );

	/** All finder methods in this class use this SELECT constant to build their queries. */
	protected final String SQL_SELECT = "SELECT id, requestid, requester, volunteer, value, text, created FROM " + getTableName() + "";

	/** Finder methods will pass this value to the JDBC setMaxRows method. */
	protected int maxRows;

	/** SQL INSERT statement for this table. */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( id, requestid, requester, volunteer, value, text, created ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";

	/** SQL UPDATE statement for this table. */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET id = ?, requestid = ?, requester = ?, volunteer = ?, value = ?, text = ?, created = ? WHERE id = ?";

	/** SQL DELETE statement for this table. */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE id = ?";

	/** Index of column id. */
	protected static final int COLUMN_ID = 1;

	/** Index of column requestid. */
	protected static final int COLUMN_REQUESTID = 2;

	/** Index of column requester. */
	protected static final int COLUMN_REQUESTER = 3;

	/** Index of column volunteer. */
	protected static final int COLUMN_VOLUNTEER = 4;

	/** Index of column value. */
	protected static final int COLUMN_VALUE = 5;

	/** Index of column text. */
	protected static final int COLUMN_TEXT = 6;

	/** Index of column created. */
	protected static final int COLUMN_CREATED = 7;

	/** Number of columns. */
	protected static final int NUMBER_OF_COLUMNS = 7;

	/** Index of primary-key column id. */
	protected static final int PK_COLUMN_ID = 1;

	/**
	 * Inserts a new row in the ratings table.
	 *
	 * @param dto the dto
	 * @return the ratings pk
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public RatingsPk insert(Ratings dto) throws RatingsDaoException
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
			stmt.setInt( index++, dto.getId() );
			stmt.setInt( index++, dto.getRequestid() );
			stmt.setString( index++, dto.getRequester() );
			stmt.setString( index++, dto.getVolunteer() );
			stmt.setFloat( index++, dto.getValue() );
			stmt.setString( index++, dto.getText() );
			stmt.setTimestamp(index++, dto.getCreated()==null ? null : new java.sql.Timestamp( dto.getCreated().getTime() ) );
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
			throw new RatingsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Updates a single row in the ratings table.
	 *
	 * @param pk the pk
	 * @param dto the dto
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public void update(RatingsPk pk, Ratings dto) throws RatingsDaoException
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
			stmt.setInt( index++, dto.getId() );
			stmt.setInt( index++, dto.getRequestid() );
			stmt.setString( index++, dto.getRequester() );
			stmt.setString( index++, dto.getVolunteer() );
			stmt.setFloat( index++, dto.getValue() );
			stmt.setString( index++, dto.getText() );
			stmt.setTimestamp(index++, dto.getCreated()==null ? null : new java.sql.Timestamp( dto.getCreated().getTime() ) );
			stmt.setInt( 8, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RatingsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Deletes a single row in the ratings table.
	 *
	 * @param pk the pk
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public void delete(RatingsPk pk) throws RatingsDaoException
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
			stmt.setInt( 1, pk.getId() );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RatingsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Returns the rows from the ratings table that matches the specified primary-key value.
	 *
	 * @param pk the pk
	 * @return the ratings
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings findByPrimaryKey(RatingsPk pk) throws RatingsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the ratings
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings findByPrimaryKey(int id) throws RatingsDaoException
	{
		Ratings ret[] = findByDynamicSelect( SQL_SELECT + " WHERE id = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the ratings table that match the criteria ''.
	 *
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findAll() throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY id", null );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'requestid = :requestid'.
	 *
	 * @param requestid the requestid
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByRequest(int requestid) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requestid = ?", new Object[] {  new Integer(requestid) } );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'requester = :requester'.
	 *
	 * @param requester the requester
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByUsers(String requester) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requester = ?", new Object[] { requester } );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'volunteer = :volunteer'.
	 *
	 * @param volunteer the volunteer
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByUsers2(String volunteer) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE volunteer = ?", new Object[] { volunteer } );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereIdEquals(int id) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE id = ? ORDER BY id", new Object[] {  new Integer(id) } );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'requestid = :requestid'.
	 *
	 * @param requestid the requestid
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereRequestidEquals(int requestid) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requestid = ? ORDER BY requestid", new Object[] {  new Integer(requestid) } );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'requester = :requester'.
	 *
	 * @param requester the requester
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereRequesterEquals(String requester) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requester = ? ORDER BY requester", new Object[] { requester } );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'volunteer = :volunteer'.
	 *
	 * @param volunteer the volunteer
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereVolunteerEquals(String volunteer) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE volunteer = ? ORDER BY volunteer", new Object[] { volunteer } );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'value = :value'.
	 *
	 * @param value the value
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereValueEquals(float value) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE value = ? ORDER BY value", new Object[] {  new Float(value) } );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'text = :text'.
	 *
	 * @param text the text
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereTextEquals(String text) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE text = ? ORDER BY text", new Object[] { text } );
	}

	/**
	 * Returns all rows from the ratings table that match the criteria 'created = :created'.
	 *
	 * @param created the created
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereCreatedEquals(Date created) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE created = ? ORDER BY created", new Object[] { created==null ? null : new java.sql.Timestamp( created.getTime() ) } );
	}

	/**
	 * Method 'RatingsDaoImpl'.
	 */
	public RatingsDaoImpl()
	{
	}

	/**
	 * Method 'RatingsDaoImpl'.
	 *
	 * @param userConn the user conn
	 */
	public RatingsDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	/**
	 * Sets the value of maxRows.
	 *
	 * @param maxRows the new max rows
	 */
	public void setMaxRows(int maxRows)
	{
		this.maxRows = maxRows;
	}

	/**
	 * Gets the value of maxRows.
	 *
	 * @return the max rows
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * Method 'getTableName'.
	 *
	 * @return String
	 */
	public String getTableName()
	{
		return "safedrop.ratings";
	}

	/**
	 * Fetches a single row from the result set.
	 *
	 * @param rs the rs
	 * @return the ratings
	 * @throws SQLException the sQL exception
	 */
	protected Ratings fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Ratings dto = new Ratings();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/**
	 * Fetches multiple rows from the result set.
	 *
	 * @param rs the rs
	 * @return the ratings[]
	 * @throws SQLException the sQL exception
	 */
	protected Ratings[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			Ratings dto = new Ratings();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Ratings ret[] = new Ratings[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet.
	 *
	 * @param dto the dto
	 * @param rs the rs
	 * @throws SQLException the sQL exception
	 */
	protected void populateDto(Ratings dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setRequestid( rs.getInt( COLUMN_REQUESTID ) );
		dto.setRequester( rs.getString( COLUMN_REQUESTER ) );
		dto.setVolunteer( rs.getString( COLUMN_VOLUNTEER ) );
		dto.setValue( rs.getFloat( COLUMN_VALUE ) );
		dto.setText( rs.getString( COLUMN_TEXT ) );
		dto.setCreated( rs.getTimestamp(COLUMN_CREATED ) );
	}

	/**
	 * Resets the modified attributes in the DTO.
	 *
	 * @param dto the dto
	 */
	protected void reset(Ratings dto)
	{
	}

	/**
	 * Returns all rows from the ratings table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByDynamicSelect(String sql, Object[] sqlParams) throws RatingsDaoException
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
			throw new RatingsDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the ratings table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByDynamicWhere(String sql, Object[] sqlParams) throws RatingsDaoException
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
			throw new RatingsDaoException( "Exception: " + _e.getMessage(), _e );
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
