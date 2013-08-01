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
 * The Interface RatingsDao.
 */
public interface RatingsDao
{
	
	/**
	 * Inserts a new row in the ratings table.
	 *
	 * @param dto the dto
	 * @return the ratings pk
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public RatingsPk insert(Ratings dto) throws RatingsDaoException;

	/**
	 * Updates a single row in the ratings table.
	 *
	 * @param pk the pk
	 * @param dto the dto
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public void update(RatingsPk pk, Ratings dto) throws RatingsDaoException;

	/**
	 * Deletes a single row in the ratings table.
	 *
	 * @param pk the pk
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public void delete(RatingsPk pk) throws RatingsDaoException;

	/**
	 * Returns the rows from the ratings table that matches the specified primary-key value.
	 *
	 * @param pk the pk
	 * @return the ratings
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings findByPrimaryKey(RatingsPk pk) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the ratings
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings findByPrimaryKey(int id) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria ''.
	 *
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findAll() throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'requestid = :requestid'.
	 *
	 * @param requestid the requestid
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByRequest(int requestid) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'requester = :requester'.
	 *
	 * @param requester the requester
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByUsers(String requester) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'volunteer = :volunteer'.
	 *
	 * @param volunteer the volunteer
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByUsers2(String volunteer) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereIdEquals(int id) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'requestid = :requestid'.
	 *
	 * @param requestid the requestid
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereRequestidEquals(int requestid) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'requester = :requester'.
	 *
	 * @param requester the requester
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereRequesterEquals(String requester) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'volunteer = :volunteer'.
	 *
	 * @param volunteer the volunteer
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereVolunteerEquals(String volunteer) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'value = :value'.
	 *
	 * @param value the value
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereValueEquals(float value) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'text = :text'.
	 *
	 * @param text the text
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereTextEquals(String text) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the criteria 'created = :created'.
	 *
	 * @param created the created
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findWhereCreatedEquals(Date created) throws RatingsDaoException;

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
	 * Returns all rows from the ratings table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByDynamicSelect(String sql, Object[] sqlParams) throws RatingsDaoException;

	/**
	 * Returns all rows from the ratings table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the ratings[]
	 * @throws RatingsDaoException the ratings dao exception
	 */
	public Ratings[] findByDynamicWhere(String sql, Object[] sqlParams) throws RatingsDaoException;

}
