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
 * The Class MessagesDaoImpl.
 */
public class MessagesDaoImpl extends AbstractDAO implements MessagesDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger( MessagesDaoImpl.class );

	/** All finder methods in this class use this SELECT constant to build their queries. */
	protected final String SQL_SELECT = "SELECT id, text, sender, receiver, created FROM " + getTableName() + "";

	/** Finder methods will pass this value to the JDBC setMaxRows method. */
	protected int maxRows;

	/** SQL INSERT statement for this table. */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( id, text, sender, receiver, created ) VALUES ( ?, ?, ?, ?, ? )";

	/** SQL UPDATE statement for this table. */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET id = ?, text = ?, sender = ?, receiver = ?, created = ? WHERE id = ?";

	/** SQL DELETE statement for this table. */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE id = ?";

	/** Index of column id. */
	protected static final int COLUMN_ID = 1;

	/** Index of column text. */
	protected static final int COLUMN_TEXT = 2;

	/** Index of column sender. */
	protected static final int COLUMN_SENDER = 3;

	/** Index of column receiver. */
	protected static final int COLUMN_RECEIVER = 4;

	/** Index of column created. */
	protected static final int COLUMN_CREATED = 5;

	/** Number of columns. */
	protected static final int NUMBER_OF_COLUMNS = 5;

	/** Index of primary-key column id. */
	protected static final int PK_COLUMN_ID = 1;

	/**
	 * Inserts a new row in the messages table.
	 *
	 * @param dto the dto
	 * @return the messages pk
	 * @throws MessagesDaoException the messages dao exception
	 */
	public MessagesPk insert(Messages dto) throws MessagesDaoException
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
			stmt.setString( index++, dto.getSender() );
			stmt.setString( index++, dto.getReceiver() );
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
			throw new MessagesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Updates a single row in the messages table.
	 *
	 * @param pk the pk
	 * @param dto the dto
	 * @throws MessagesDaoException the messages dao exception
	 */
	public void update(MessagesPk pk, Messages dto) throws MessagesDaoException
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
			stmt.setString( index++, dto.getSender() );
			stmt.setString( index++, dto.getReceiver() );
			stmt.setTimestamp(index++, dto.getCreated()==null ? null : new java.sql.Timestamp( dto.getCreated().getTime() ) );
			stmt.setInt( 6, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new MessagesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Deletes a single row in the messages table.
	 *
	 * @param pk the pk
	 * @throws MessagesDaoException the messages dao exception
	 */
	public void delete(MessagesPk pk) throws MessagesDaoException
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
			throw new MessagesDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/**
	 * Returns the rows from the messages table that matches the specified primary-key value.
	 *
	 * @param pk the pk
	 * @return the messages
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages findByPrimaryKey(MessagesPk pk) throws MessagesDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/**
	 * Returns all rows from the messages table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the messages
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages findByPrimaryKey(int id) throws MessagesDaoException
	{
		Messages ret[] = findByDynamicSelect( SQL_SELECT + " WHERE id = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/**
	 * Returns all rows from the messages table that match the criteria ''.
	 *
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findAll() throws MessagesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY id", null );
	}

	/**
	 * Returns all rows from the messages table that match the criteria 'sender = :sender'.
	 *
	 * @param sender the sender
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findByUsers(String sender) throws MessagesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE sender = ?", new Object[] { sender } );
	}

	/**
	 * Returns all rows from the messages table that match the criteria 'receiver = :receiver'.
	 *
	 * @param receiver the receiver
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findByUsers2(String receiver) throws MessagesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE receiver = ?", new Object[] { receiver } );
	}

	/**
	 * Returns all rows from the messages table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereIdEquals(int id) throws MessagesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE id = ? ORDER BY id", new Object[] {  new Integer(id) } );
	}

	/**
	 * Returns all rows from the messages table that match the criteria 'text = :text'.
	 *
	 * @param text the text
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereTextEquals(String text) throws MessagesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE text = ? ORDER BY text", new Object[] { text } );
	}

	/**
	 * Returns all rows from the messages table that match the criteria 'sender = :sender'.
	 *
	 * @param sender the sender
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereSenderEquals(String sender) throws MessagesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE sender = ? ORDER BY sender", new Object[] { sender } );
	}

	/**
	 * Returns all rows from the messages table that match the criteria 'receiver = :receiver'.
	 *
	 * @param receiver the receiver
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereReceiverEquals(String receiver) throws MessagesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE receiver = ? ORDER BY receiver", new Object[] { receiver } );
	}

	/**
	 * Returns all rows from the messages table that match the criteria 'created = :created'.
	 *
	 * @param created the created
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereCreatedEquals(Date created) throws MessagesDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE created = ? ORDER BY created", new Object[] { created==null ? null : new java.sql.Timestamp( created.getTime() ) } );
	}

	/**
	 * Method 'MessagesDaoImpl'.
	 */
	public MessagesDaoImpl()
	{
	}

	/**
	 * Method 'MessagesDaoImpl'.
	 *
	 * @param userConn the user conn
	 */
	public MessagesDaoImpl(final java.sql.Connection userConn)
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
		return "safedrop.messages";
	}

	/**
	 * Fetches a single row from the result set.
	 *
	 * @param rs the rs
	 * @return the messages
	 * @throws SQLException the sQL exception
	 */
	protected Messages fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Messages dto = new Messages();
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
	 * @return the messages[]
	 * @throws SQLException the sQL exception
	 */
	protected Messages[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			Messages dto = new Messages();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Messages ret[] = new Messages[ resultList.size() ];
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
	protected void populateDto(Messages dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setText( rs.getString( COLUMN_TEXT ) );
		dto.setSender( rs.getString( COLUMN_SENDER ) );
		dto.setReceiver( rs.getString( COLUMN_RECEIVER ) );
		dto.setCreated( rs.getTimestamp(COLUMN_CREATED ) );
	}

	/**
	 * Resets the modified attributes in the DTO.
	 *
	 * @param dto the dto
	 */
	protected void reset(Messages dto)
	{
	}

	/**
	 * Returns all rows from the messages table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findByDynamicSelect(String sql, Object[] sqlParams) throws MessagesDaoException
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
			throw new MessagesDaoException( "Exception: " + _e.getMessage(), _e );
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
	 * Returns all rows from the messages table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findByDynamicWhere(String sql, Object[] sqlParams) throws MessagesDaoException
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
			throw new MessagesDaoException( "Exception: " + _e.getMessage(), _e );
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
