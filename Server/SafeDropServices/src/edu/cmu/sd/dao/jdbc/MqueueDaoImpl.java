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

public class MqueueDaoImpl extends AbstractDAO implements MqueueDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( MqueueDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT id, type, text, from, to, requestid FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( id, type, text, from, to, requestid ) VALUES ( ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET id = ?, type = ?, text = ?, from = ?, to = ?, requestid = ? WHERE id = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE id = ?";

	/** 
	 * Index of column id
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column type
	 */
	protected static final int COLUMN_TYPE = 2;

	/** 
	 * Index of column text
	 */
	protected static final int COLUMN_TEXT = 3;

	/** 
	 * Index of column from
	 */
	protected static final int COLUMN_FROM = 4;

	/** 
	 * Index of column to
	 */
	protected static final int COLUMN_TO = 5;

	/** 
	 * Index of column requestid
	 */
	protected static final int COLUMN_REQUESTID = 6;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 6;

	/** 
	 * Index of primary-key column id
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the mqueue table.
	 */
	public MqueuePk insert(Mqueue dto) throws MqueueDaoException
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
			stmt.setString( index++, dto.getType() );
			stmt.setString( index++, dto.getText() );
			stmt.setString( index++, dto.getFrom() );
			stmt.setString( index++, dto.getTo() );
			stmt.setInt( index++, dto.getRequestid() );
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
			throw new MqueueDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the mqueue table.
	 */
	public void update(MqueuePk pk, Mqueue dto) throws MqueueDaoException
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
			stmt.setString( index++, dto.getType() );
			stmt.setString( index++, dto.getText() );
			stmt.setString( index++, dto.getFrom() );
			stmt.setString( index++, dto.getTo() );
			stmt.setInt( index++, dto.getRequestid() );
			stmt.setInt( 7, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new MqueueDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the mqueue table.
	 */
	public void delete(MqueuePk pk) throws MqueueDaoException
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
			throw new MqueueDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the mqueue table that matches the specified primary-key value.
	 */
	public Mqueue findByPrimaryKey(MqueuePk pk) throws MqueueDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'id = :id'.
	 */
	public Mqueue findByPrimaryKey(int id) throws MqueueDaoException
	{
		Mqueue ret[] = findByDynamicSelect( SQL_SELECT + " WHERE id = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria ''.
	 */
	public Mqueue[] findAll() throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY id", null );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'from = :from'.
	 */
	public Mqueue[] findByUsers(String from) throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE from = ?", new Object[] { from } );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'to = :to'.
	 */
	public Mqueue[] findByUsers2(String to) throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE to = ?", new Object[] { to } );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'requestid = :requestid'.
	 */
	public Mqueue[] findByRequest(int requestid) throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requestid = ?", new Object[] {  new Integer(requestid) } );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'id = :id'.
	 */
	public Mqueue[] findWhereIdEquals(int id) throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE id = ? ORDER BY id", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'type = :type'.
	 */
	public Mqueue[] findWhereTypeEquals(String type) throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE type = ? ORDER BY type", new Object[] { type } );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'text = :text'.
	 */
	public Mqueue[] findWhereTextEquals(String text) throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE text = ? ORDER BY text", new Object[] { text } );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'from = :from'.
	 */
	public Mqueue[] findWhereFromEquals(String from) throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE from = ? ORDER BY from", new Object[] { from } );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'to = :to'.
	 */
	public Mqueue[] findWhereToEquals(String to) throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE to = ? ORDER BY to", new Object[] { to } );
	}

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'requestid = :requestid'.
	 */
	public Mqueue[] findWhereRequestidEquals(int requestid) throws MqueueDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requestid = ? ORDER BY requestid", new Object[] {  new Integer(requestid) } );
	}

	/**
	 * Method 'MqueueDaoImpl'
	 * 
	 */
	public MqueueDaoImpl()
	{
	}

	/**
	 * Method 'MqueueDaoImpl'
	 * 
	 * @param userConn
	 */
	public MqueueDaoImpl(final java.sql.Connection userConn)
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
		return "safedrop.mqueue";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected Mqueue fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Mqueue dto = new Mqueue();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected Mqueue[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			Mqueue dto = new Mqueue();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Mqueue ret[] = new Mqueue[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Mqueue dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setType( rs.getString( COLUMN_TYPE ) );
		dto.setText( rs.getString( COLUMN_TEXT ) );
		dto.setFrom( rs.getString( COLUMN_FROM ) );
		dto.setTo( rs.getString( COLUMN_TO ) );
		dto.setRequestid( rs.getInt( COLUMN_REQUESTID ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Mqueue dto)
	{
	}

	/** 
	 * Returns all rows from the mqueue table that match the specified arbitrary SQL statement
	 */
	public Mqueue[] findByDynamicSelect(String sql, Object[] sqlParams) throws MqueueDaoException
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
			throw new MqueueDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the mqueue table that match the specified arbitrary SQL statement
	 */
	public Mqueue[] findByDynamicWhere(String sql, Object[] sqlParams) throws MqueueDaoException
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
			throw new MqueueDaoException( "Exception: " + _e.getMessage(), _e );
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
