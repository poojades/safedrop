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
 * The Class NotificationsDaoImpl.
 */
public class NotificationsDaoImpl extends AbstractDAO implements NotificationsDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger( NotificationsDaoImpl.class );

	/** All finder methods in this class use this SELECT constant to build their queries. */
	protected final String SQL_SELECT = "SELECT id, text, requestid, created, receiver, sender, type FROM " + getTableName() + "";

	/** Finder methods will pass this value to the JDBC setMaxRows method. */
	protected int maxRows;

	/** SQL INSERT statement for this table. */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( id, text, requestid, created, receiver, sender, type ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";

	/** SQL UPDATE statement for this table. */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET id = ?, text = ?, requestid = ?, created = ?, receiver = ?, sender = ?, type = ? WHERE id = ?";

	/** SQL DELETE statement for this table. */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE id = ?";

	/** Index of column id. */
	protected static final int COLUMN_ID = 1;

	/** Index of column text. */
	protected static final int COLUMN_TEXT = 2;

	/** Index of column requestid. */
	protected static final int COLUMN_REQUESTID = 3;

	/** Index of column created. */
	protected static final int COLUMN_CREATED = 4;

	/** Index of column receiver. */
	protected static final int COLUMN_RECEIVER = 5;

	/** Index of column sender. */
	protected static final int COLUMN_SENDER = 6;

	/** Index of column type. */
	protected static final int COLUMN_TYPE = 7;

	/** Number of columns. */
	protected static final int NUMBER_OF_COLUMNS = 7;

	/** Index of primary-key column id. */
	protected static final int PK_COLUMN_ID = 1;

	/**
	 * Inserts a new row in the notifications table.
	 *
	 * @param dto the dto
	 * @return the notifications pk
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public NotificationsPk insert(Notifications dto) throws NotificationsDaoException
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
			stmt.setString( index++, dto.getText() );
			stmt.setInt( index++, dto.getRequestid() );
			stmt.setTimestamp(index++, dto.getCreated()==null ? null : new java.sql.Timestamp( dto.getCreated().getTime() ) );
			stmt.setString( index++, dto.getReceiver() );
			stmt.setString( index++, dto.getSender() );
			stmt.setString( index++, dto.getType() );
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
			throw new NotificationsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Updates a single row in the notifications table.
	 *
	 * @param pk the pk
	 * @param dto the dto
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public void update(NotificationsPk pk, Notifications dto) throws NotificationsDaoException
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
			stmt.setString( index++, dto.getText() );
			stmt.setInt( index++, dto.getRequestid() );
			stmt.setTimestamp(index++, dto.getCreated()==null ? null : new java.sql.Timestamp( dto.getCreated().getTime() ) );
			stmt.setString( index++, dto.getReceiver() );
			stmt.setString( index++, dto.getSender() );
			stmt.setString( index++, dto.getType() );
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
			throw new NotificationsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Deletes a single row in the notifications table.
	 *
	 * @param pk the pk
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public void delete(NotificationsPk pk) throws NotificationsDaoException
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
			throw new NotificationsDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Returns the rows from the notifications table that matches the specified primary-key value.
	 *
	 * @param pk the pk
	 * @return the notifications
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications findByPrimaryKey(NotificationsPk pk) throws NotificationsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the notifications
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications findByPrimaryKey(int id) throws NotificationsDaoException
	{
		Notifications ret[] = findByDynamicSelect( SQL_SELECT + " WHERE id = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'recevier = :recevier'.
	 *
	 * @param recevier the recevier
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereRecevierEquals(String recevier) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE recevier = ? ORDER BY recevier", new Object[] { recevier } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria ''.
	 *
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findAll() throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY id", null );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'sender = :sender'.
	 *
	 * @param sender the sender
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByUsers(String sender) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE sender = ?", new Object[] { sender } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'requestid = :requestid'.
	 *
	 * @param requestid the requestid
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByRequest(int requestid) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requestid = ?", new Object[] {  new Integer(requestid) } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'receiver = :receiver'.
	 *
	 * @param receiver the receiver
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByUsers2(String receiver) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE receiver = ?", new Object[] { receiver } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereIdEquals(int id) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE id = ? ORDER BY id", new Object[] {  new Integer(id) } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'text = :text'.
	 *
	 * @param text the text
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereTextEquals(String text) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE text = ? ORDER BY text", new Object[] { text } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'receiver = :receiver'.
	 *
	 * @param receiver the receiver
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereReceiverEquals(String receiver) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE receiver = ? ORDER BY receiver", new Object[] { receiver } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'requestid = :requestid'.
	 *
	 * @param requestid the requestid
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereRequestidEquals(int requestid) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requestid = ? ORDER BY requestid", new Object[] {  new Integer(requestid) } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'created = :created'.
	 *
	 * @param created the created
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereCreatedEquals(Date created) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE created = ? ORDER BY created", new Object[] { created==null ? null : new java.sql.Timestamp( created.getTime() ) } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'sender = :sender'.
	 *
	 * @param sender the sender
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereSenderEquals(String sender) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE sender = ? ORDER BY sender", new Object[] { sender } );
	}

	/**
	 * Returns all rows from the notifications table that match the criteria 'type = :type'.
	 *
	 * @param type the type
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereTypeEquals(String type) throws NotificationsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE type = ? ORDER BY type", new Object[] { type } );
	}

	/**
	 * Method 'NotificationsDaoImpl'.
	 */
	public NotificationsDaoImpl()
	{
	}

	/**
	 * Method 'NotificationsDaoImpl'.
	 *
	 * @param userConn the user conn
	 */
	public NotificationsDaoImpl(final java.sql.Connection userConn)
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
		return "safedrop.notifications";
	}

	/**
	 * Fetches a single row from the result set.
	 *
	 * @param rs the rs
	 * @return the notifications
	 * @throws SQLException the sQL exception
	 */
	protected Notifications fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Notifications dto = new Notifications();
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
	 * @return the notifications[]
	 * @throws SQLException the sQL exception
	 */
	protected Notifications[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			Notifications dto = new Notifications();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Notifications ret[] = new Notifications[ resultList.size() ];
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
	protected void populateDto(Notifications dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setText( rs.getString( COLUMN_TEXT ) );
		dto.setRequestid( rs.getInt( COLUMN_REQUESTID ) );
		dto.setCreated( rs.getTimestamp(COLUMN_CREATED ) );
		dto.setReceiver( rs.getString( COLUMN_RECEIVER ) );
		dto.setSender( rs.getString( COLUMN_SENDER ) );
		dto.setType( rs.getString( COLUMN_TYPE ) );
	}

	/**
	 * Resets the modified attributes in the DTO.
	 *
	 * @param dto the dto
	 */
	protected void reset(Notifications dto)
	{
	}

	/**
	 * Returns all rows from the notifications table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByDynamicSelect(String sql, Object[] sqlParams) throws NotificationsDaoException
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
			throw new NotificationsDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the notifications table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByDynamicWhere(String sql, Object[] sqlParams) throws NotificationsDaoException
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
			throw new NotificationsDaoException( "Exception: " + _e.getMessage(), _e );
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
