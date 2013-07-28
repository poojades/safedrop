/*
 * 
 * 
 *
 * 
 * 
 */

package edu.cmu.sd.dao.dao;

import java.util.Date;
import edu.cmu.sd.dao.dto.*;
import edu.cmu.sd.dao.exceptions.*;

public interface RequestDao
{
	/** 
	 * Inserts a new row in the request table.
	 */
	public RequestPk insert(Request dto) throws RequestDaoException;

	/** 
	 * Updates a single row in the request table.
	 */
	public void update(RequestPk pk, Request dto) throws RequestDaoException;

	/** 
	 * Deletes a single row in the request table.
	 */
	public void delete(RequestPk pk) throws RequestDaoException;

	/** 
	 * Returns the rows from the request table that matches the specified primary-key value.
	 */
	public Request findByPrimaryKey(RequestPk pk) throws RequestDaoException;

	/** 
	 * Returns all rows from the request table that match the criteria 'id = :id'.
	 */
	public Request findByPrimaryKey(int id) throws RequestDaoException;

	/** 
	 * Returns all rows from the request table that match the criteria ''.
	 */
	public Request[] findAll() throws RequestDaoException;

	/** 
	 * Returns all rows from the request table that match the criteria 'by = :by'.
	 */
	public Request[] findByUsers(String by) throws RequestDaoException;

	/** 
	 * Returns all rows from the request table that match the criteria 'id = :id'.
	 */
	public Request[] findWhereIdEquals(int id) throws RequestDaoException;

	/** 
	 * Returns all rows from the request table that match the criteria 'by = :by'.
	 */
	public Request[] findWhereByEquals(String by) throws RequestDaoException;

	/** 
	 * Returns all rows from the request table that match the criteria 'created = :created'.
	 */
	public Request[] findWhereCreatedEquals(Date created) throws RequestDaoException;

	/** 
	 * Returns all rows from the request table that match the criteria 'status = :status'.
	 */
	public Request[] findWhereStatusEquals(String status) throws RequestDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the request table that match the specified arbitrary SQL statement
	 */
	public Request[] findByDynamicSelect(String sql, Object[] sqlParams) throws RequestDaoException;

	/** 
	 * Returns all rows from the request table that match the specified arbitrary SQL statement
	 */
	public Request[] findByDynamicWhere(String sql, Object[] sqlParams) throws RequestDaoException;

}
