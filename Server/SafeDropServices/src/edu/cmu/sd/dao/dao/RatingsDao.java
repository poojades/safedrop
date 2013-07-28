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

public interface RatingsDao
{
	/** 
	 * Inserts a new row in the ratings table.
	 */
	public RatingsPk insert(Ratings dto) throws RatingsDaoException;

	/** 
	 * Updates a single row in the ratings table.
	 */
	public void update(RatingsPk pk, Ratings dto) throws RatingsDaoException;

	/** 
	 * Deletes a single row in the ratings table.
	 */
	public void delete(RatingsPk pk) throws RatingsDaoException;

	/** 
	 * Returns the rows from the ratings table that matches the specified primary-key value.
	 */
	public Ratings findByPrimaryKey(RatingsPk pk) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'id = :id'.
	 */
	public Ratings findByPrimaryKey(int id) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria ''.
	 */
	public Ratings[] findAll() throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'requestid = :requestid'.
	 */
	public Ratings[] findByRequest(int requestid) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'by = :by'.
	 */
	public Ratings[] findByUsers(String by) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'for = :aFor'.
	 */
	public Ratings[] findByUsers2(String aFor) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'id = :id'.
	 */
	public Ratings[] findWhereIdEquals(int id) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'requestid = :requestid'.
	 */
	public Ratings[] findWhereRequestidEquals(int requestid) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'by = :by'.
	 */
	public Ratings[] findWhereByEquals(String by) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'for = :aFor'.
	 */
	public Ratings[] findWhereAForEquals(String aFor) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'value = :value'.
	 */
	public Ratings[] findWhereValueEquals(float value) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the criteria 'text = :text'.
	 */
	public Ratings[] findWhereTextEquals(String text) throws RatingsDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the ratings table that match the specified arbitrary SQL statement
	 */
	public Ratings[] findByDynamicSelect(String sql, Object[] sqlParams) throws RatingsDaoException;

	/** 
	 * Returns all rows from the ratings table that match the specified arbitrary SQL statement
	 */
	public Ratings[] findByDynamicWhere(String sql, Object[] sqlParams) throws RatingsDaoException;

}
