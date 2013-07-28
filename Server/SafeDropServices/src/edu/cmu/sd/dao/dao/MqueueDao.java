/*
 * 
 * 
 *
 * 
 * 
 */

package edu.cmu.sd.dao.dao;

import edu.cmu.sd.dao.dto.*;
import edu.cmu.sd.dao.exceptions.*;

public interface MqueueDao
{
	/** 
	 * Inserts a new row in the mqueue table.
	 */
	public MqueuePk insert(Mqueue dto) throws MqueueDaoException;

	/** 
	 * Updates a single row in the mqueue table.
	 */
	public void update(MqueuePk pk, Mqueue dto) throws MqueueDaoException;

	/** 
	 * Deletes a single row in the mqueue table.
	 */
	public void delete(MqueuePk pk) throws MqueueDaoException;

	/** 
	 * Returns the rows from the mqueue table that matches the specified primary-key value.
	 */
	public Mqueue findByPrimaryKey(MqueuePk pk) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'id = :id'.
	 */
	public Mqueue findByPrimaryKey(int id) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria ''.
	 */
	public Mqueue[] findAll() throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'from = :from'.
	 */
	public Mqueue[] findByUsers(String from) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'to = :to'.
	 */
	public Mqueue[] findByUsers2(String to) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'requestid = :requestid'.
	 */
	public Mqueue[] findByRequest(int requestid) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'id = :id'.
	 */
	public Mqueue[] findWhereIdEquals(int id) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'type = :type'.
	 */
	public Mqueue[] findWhereTypeEquals(String type) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'text = :text'.
	 */
	public Mqueue[] findWhereTextEquals(String text) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'from = :from'.
	 */
	public Mqueue[] findWhereFromEquals(String from) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'to = :to'.
	 */
	public Mqueue[] findWhereToEquals(String to) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the criteria 'requestid = :requestid'.
	 */
	public Mqueue[] findWhereRequestidEquals(int requestid) throws MqueueDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the mqueue table that match the specified arbitrary SQL statement
	 */
	public Mqueue[] findByDynamicSelect(String sql, Object[] sqlParams) throws MqueueDaoException;

	/** 
	 * Returns all rows from the mqueue table that match the specified arbitrary SQL statement
	 */
	public Mqueue[] findByDynamicWhere(String sql, Object[] sqlParams) throws MqueueDaoException;

}
