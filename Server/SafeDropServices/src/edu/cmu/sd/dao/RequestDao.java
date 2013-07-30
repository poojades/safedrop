/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */

package edu.cmu.sd.dao;

import java.util.Date;
import edu.cmu.sd.dto.*;
import edu.cmu.sd.exceptions.*;

// TODO: Auto-generated Javadoc
/**
 * The Interface RequestDao.
 */
public interface RequestDao
{
	
	/**
	 * Inserts a new row in the request table.
	 *
	 * @param dto the dto
	 * @return the request pk
	 * @throws RequestDaoException the request dao exception
	 */
	public RequestPk insert(Request dto) throws RequestDaoException;

	/**
	 * Updates a single row in the request table.
	 *
	 * @param pk the pk
	 * @param dto the dto
	 * @throws RequestDaoException the request dao exception
	 */
	public void update(RequestPk pk, Request dto) throws RequestDaoException;

	/**
	 * Deletes a single row in the request table.
	 *
	 * @param pk the pk
	 * @throws RequestDaoException the request dao exception
	 */
	public void delete(RequestPk pk) throws RequestDaoException;

	/**
	 * Returns the rows from the request table that matches the specified primary-key value.
	 *
	 * @param pk the pk
	 * @return the request
	 * @throws RequestDaoException the request dao exception
	 */
	public Request findByPrimaryKey(RequestPk pk) throws RequestDaoException;

	/**
	 * Returns all rows from the request table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the request
	 * @throws RequestDaoException the request dao exception
	 */
	public Request findByPrimaryKey(int id) throws RequestDaoException;

	/**
	 * Returns all rows from the request table that match the criteria ''.
	 *
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findAll() throws RequestDaoException;

	/**
	 * Returns all rows from the request table that match the criteria 'requester = :requester'.
	 *
	 * @param requester the requester
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findByUsers(String requester) throws RequestDaoException;

	/**
	 * Returns all rows from the request table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findWhereIdEquals(int id) throws RequestDaoException;

	/**
	 * Returns all rows from the request table that match the criteria 'requester = :requester'.
	 *
	 * @param requester the requester
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findWhereRequesterEquals(String requester) throws RequestDaoException;

	/**
	 * Returns all rows from the request table that match the criteria 'created = :created'.
	 *
	 * @param created the created
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findWhereCreatedEquals(Date created) throws RequestDaoException;

	/**
	 * Returns all rows from the request table that match the criteria 'status = :status'.
	 *
	 * @param status the status
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findWhereStatusEquals(String status) throws RequestDaoException;

	/**
	 * Sets the value of maxRows.
	 *
	 * @param maxRows the new max rows
	 */
	public void setMaxRows(int maxRows);

	/**
	 * Gets the value of maxRows.
	 *
	 * @return the max rows
	 */
	public int getMaxRows();

	/**
	 * Returns all rows from the request table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findByDynamicSelect(String sql, Object[] sqlParams) throws RequestDaoException;

	/**
	 * Returns all rows from the request table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the request[]
	 * @throws RequestDaoException the request dao exception
	 */
	public Request[] findByDynamicWhere(String sql, Object[] sqlParams) throws RequestDaoException;

}
