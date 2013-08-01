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
 * The Class RequestDaoImpl.
 */
public class RequestDaoImpl extends AbstractDAO implements RequestDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger( RequestDaoImpl.class );

	/** All finder methods in this class use this SELECT constant to build their queries. */
	protected final String SQL_SELECT = "SELECT id, requester, created, status FROM " + getTableName() + "";

	/** Finder methods will pass this value to the JDBC setMaxRows method. */
	protected int maxRows;

	/** SQL INSERT statement for this table. */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( id, requester, created, status ) VALUES ( ?, ?, ?, ? )";

	/** SQL UPDATE statement for this table. */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET id = ?, requester = ?, created = ?, status = ? WHERE id = ?";

	/** SQL DELETE statement for this table. */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE id = ?";

	/** Index of column id. */
	protected static final int COLUMN_ID = 1;

	/** Index of column requester. */
	protected static final int COLUMN_REQUESTER = 2;

	/** Index of column created. */
	protected static final int COLUMN_CREATED = 3;

	/** Index of column status. */
	protected static final int COLUMN_STATUS = 4;

	/** Number of columns. */
	protected static final int NUMBER_OF_COLUMNS = 4;

	/** Index of primary-key column id. */
	protected static final int PK_COLUMN_ID = 1;

	/**
	 * Inserts a new row in the request table.
	 *
	 * @param dto the dto
	 * @return the request pk
	 * @throws RequestDaoException the request dao exception
	 */
	public RequestPk insert(Request dto) throws RequestDaoException
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
		
			stmt = conn.prepareStatement( SQL_INSERT, Statement.RETURN_GENERATED_KEYS );
			int index = 1;
			stmt.setInt( index++, dto.getId() );
			stmt.setString( index++, dto.getRequester() );
			stmt.setTimestamp(index++, dto.getCreated()==null ? null : new java.sql.Timestamp( dto.getCreated().getTime() ) );
			stmt.setString( index++, dto.getStatus() );
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_INSERT + " with DTO: " + dto);
			}
		
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		
			// retrieve values from auto-increment columns
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				dto.setId( rs.getInt( 1 ) );
			}
		
			reset(dto);
			return dto.createPk();
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RequestDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Updates a single row in the request table.
	 *
	 * @param pk the pk
	 * @param dto the dto
	 * @throws RequestDaoException the request dao exception
	 */
	public void update(RequestPk pk, Request dto) throws RequestDaoException
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
			stmt.setString( index++, dto.getRequester() );
			stmt.setTimestamp(index++, dto.getCreated()==null ? null : new java.sql.Timestamp( dto.getCreated().getTime() ) );
			stmt.setString( index++, dto.getStatus() );
			stmt.setInt( 5, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RequestDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Deletes a single row in the request table.
	 *
	 * @param pk the pk
	 * @throws RequestDaoException the request dao exception
	 */
	public void delete(RequestPk pk) throws RequestDaoException
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
			throw new RequestDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Returns the rows from the request table that matches the specified primary-key value.
	 *
	 * @param pk the pk
	 * @return the request
	 * @throws RequestDaoException the request dao exception
	 */
	public Request findByPrimaryKey(RequestPk pk) throws RequestDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/**
	 * Returns all rows from the request table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the request
	 * @throws RequestDaoException the request dao exception
	 */
	public Request findByPrimaryKey(int id) throws RequestDaoException
	{
		Request ret[] = findByDynamicSelect( SQL_SELECT + " WHERE id = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the request table that match the criteria ''.
	 *
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findAll() throws RequestDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY id", null );
	}

	/**
	 * Returns all rows from the request table that match the criteria 'requester = :requester'.
	 *
	 * @param requester the requester
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findByUsers(String requester) throws RequestDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requester = ?", new Object[] { requester } );
	}

	/**
	 * Returns all rows from the request table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findWhereIdEquals(int id) throws RequestDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE id = ? ORDER BY id", new Object[] {  new Integer(id) } );
	}

	/**
	 * Returns all rows from the request table that match the criteria 'requester = :requester'.
	 *
	 * @param requester the requester
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findWhereRequesterEquals(String requester) throws RequestDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requester = ? ORDER BY requester", new Object[] { requester } );
	}

	/**
	 * Returns all rows from the request table that match the criteria 'created = :created'.
	 *
	 * @param created the created
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findWhereCreatedEquals(Date created) throws RequestDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE created = ? ORDER BY created", new Object[] { created==null ? null : new java.sql.Timestamp( created.getTime() ) } );
	}

	/**
	 * Returns all rows from the request table that match the criteria 'status = :status'.
	 *
	 * @param status the status
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findWhereStatusEquals(String status) throws RequestDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE status = ? ORDER BY status", new Object[] { status } );
	}

	/**
	 * Method 'RequestDaoImpl'.
	 */
	public RequestDaoImpl()
	{
	}

	/**
	 * Method 'RequestDaoImpl'.
	 *
	 * @param userConn the user conn
	 */
	public RequestDaoImpl(final java.sql.Connection userConn)
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
		return "safedrop.request";
	}

	/**
	 * Fetches a single row from the result set.
	 *
	 * @param rs the rs
	 * @return the request
	 * @throws SQLException the sQL exception
	 */
	protected Request fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Request dto = new Request();
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
	 * @return the request[]
	 * @throws SQLException the sQL exception
	 */
	protected Request[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			Request dto = new Request();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Request ret[] = new Request[ resultList.size() ];
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
	protected void populateDto(Request dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setRequester( rs.getString( COLUMN_REQUESTER ) );
		dto.setCreated( rs.getTimestamp(COLUMN_CREATED ) );
		dto.setStatus( rs.getString( COLUMN_STATUS ) );
	}

	/**
	 * Resets the modified attributes in the DTO.
	 *
	 * @param dto the dto
	 */
	protected void reset(Request dto)
	{
	}

	/**
	 * Returns all rows from the request table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findByDynamicSelect(String sql, Object[] sqlParams) throws RequestDaoException
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
			throw new RequestDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the request table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findByDynamicWhere(String sql, Object[] sqlParams) throws RequestDaoException
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
			throw new RequestDaoException( "Exception: " + _e.getMessage(), _e );
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
