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

public class RatingsDaoImpl extends AbstractDAO implements RatingsDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger.getLogger( RatingsDaoImpl.class );

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT id, requestid, by, for, value, text FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( id, requestid, by, for, value, text ) VALUES ( ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET id = ?, requestid = ?, by = ?, for = ?, value = ?, text = ? WHERE id = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE id = ?";

	/** 
	 * Index of column id
	 */
	protected static final int COLUMN_ID = 1;

	/** 
	 * Index of column requestid
	 */
	protected static final int COLUMN_REQUESTID = 2;

	/** 
	 * Index of column by
	 */
	protected static final int COLUMN_BY = 3;

	/** 
	 * Index of column for
	 */
	protected static final int COLUMN_A_FOR = 4;

	/** 
	 * Index of column value
	 */
	protected static final int COLUMN_VALUE = 5;

	/** 
	 * Index of column text
	 */
	protected static final int COLUMN_TEXT = 6;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 6;

	/** 
	 * Index of primary-key column id
	 */
	protected static final int PK_COLUMN_ID = 1;

	/** 
	 * Inserts a new row in the ratings table.
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
			stmt.setString( index++, dto.getBy() );
			stmt.setString( index++, dto.getAFor() );
			stmt.setFloat( index++, dto.getValue() );
			stmt.setString( index++, dto.getText() );
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
			stmt.setString( index++, dto.getBy() );
			stmt.setString( index++, dto.getAFor() );
			stmt.setFloat( index++, dto.getValue() );
			stmt.setString( index++, dto.getText() );
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
	 */
	public Ratings findByPrimaryKey(RatingsPk pk) throws RatingsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'id = :id'.
	 */
	public Ratings findByPrimaryKey(int id) throws RatingsDaoException
	{
		Ratings ret[] = findByDynamicSelect( SQL_SELECT + " WHERE id = ?", new Object[] {  new Integer(id) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria ''.
	 */
	public Ratings[] findAll() throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY id", null );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'requestid = :requestid'.
	 */
	public Ratings[] findByRequest(int requestid) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requestid = ?", new Object[] {  new Integer(requestid) } );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'by = :by'.
	 */
	public Ratings[] findByUsers(String by) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE by = ?", new Object[] { by } );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'for = :aFor'.
	 */
	public Ratings[] findByUsers2(String aFor) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE for = ?", new Object[] { aFor } );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'id = :id'.
	 */
	public Ratings[] findWhereIdEquals(int id) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE id = ? ORDER BY id", new Object[] {  new Integer(id) } );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'requestid = :requestid'.
	 */
	public Ratings[] findWhereRequestidEquals(int requestid) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE requestid = ? ORDER BY requestid", new Object[] {  new Integer(requestid) } );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'by = :by'.
	 */
	public Ratings[] findWhereByEquals(String by) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE by = ? ORDER BY by", new Object[] { by } );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'for = :aFor'.
	 */
	public Ratings[] findWhereAForEquals(String aFor) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE for = ? ORDER BY for", new Object[] { aFor } );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'value = :value'.
	 */
	public Ratings[] findWhereValueEquals(float value) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE value = ? ORDER BY value", new Object[] {  new Float(value) } );
	}

	/** 
	 * Returns all rows from the ratings table that match the criteria 'text = :text'.
	 */
	public Ratings[] findWhereTextEquals(String text) throws RatingsDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE text = ? ORDER BY text", new Object[] { text } );
	}

	/**
	 * Method 'RatingsDaoImpl'
	 * 
	 */
	public RatingsDaoImpl()
	{
	}

	/**
	 * Method 'RatingsDaoImpl'
	 * 
	 * @param userConn
	 */
	public RatingsDaoImpl(final java.sql.Connection userConn)
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
		return "safedrop.ratings";
	}

	/** 
	 * Fetches a single row from the result set
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
	 * Fetches multiple rows from the result set
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
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Ratings dto, ResultSet rs) throws SQLException
	{
		dto.setId( rs.getInt( COLUMN_ID ) );
		dto.setRequestid( rs.getInt( COLUMN_REQUESTID ) );
		dto.setBy( rs.getString( COLUMN_BY ) );
		dto.setAFor( rs.getString( COLUMN_A_FOR ) );
		dto.setValue( rs.getFloat( COLUMN_VALUE ) );
		dto.setText( rs.getString( COLUMN_TEXT ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Ratings dto)
	{
	}

	/** 
	 * Returns all rows from the ratings table that match the specified arbitrary SQL statement
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
	 * Returns all rows from the ratings table that match the specified arbitrary SQL statement
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
