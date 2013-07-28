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

public interface UsersDao
{
	/** 
	 * Inserts a new row in the users table.
	 */
	public UsersPk insert(Users dto) throws UsersDaoException;

	/** 
	 * Updates a single row in the users table.
	 */
	public void update(UsersPk pk, Users dto) throws UsersDaoException;

	/** 
	 * Deletes a single row in the users table.
	 */
	public void delete(UsersPk pk) throws UsersDaoException;

	/** 
	 * Returns the rows from the users table that matches the specified primary-key value.
	 */
	public Users findByPrimaryKey(UsersPk pk) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'email = :email'.
	 */
	public Users findByPrimaryKey(String email) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria ''.
	 */
	public Users[] findAll() throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'email = :email'.
	 */
	public Users[] findWhereEmailEquals(String email) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'firstname = :firstname'.
	 */
	public Users[] findWhereFirstnameEquals(String firstname) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'lastname = :lastname'.
	 */
	public Users[] findWhereLastnameEquals(String lastname) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'mobile = :mobile'.
	 */
	public Users[] findWhereMobileEquals(String mobile) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'econtact = :econtact'.
	 */
	public Users[] findWhereEcontactEquals(String econtact) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'ename = :ename'.
	 */
	public Users[] findWhereEnameEquals(String ename) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'status = :status'.
	 */
	public Users[] findWhereStatusEquals(String status) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	public Users[] findWhereUsernameEquals(String username) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'lastactive = :lastactive'.
	 */
	public Users[] findWhereLastactiveEquals(Date lastactive) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'lastlat = :lastlat'.
	 */
	public Users[] findWhereLastlatEquals(String lastlat) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'lastlong = :lastlong'.
	 */
	public Users[] findWhereLastlongEquals(String lastlong) throws UsersDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the users table that match the specified arbitrary SQL statement
	 */
	public Users[] findByDynamicSelect(String sql, Object[] sqlParams) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the specified arbitrary SQL statement
	 */
	public Users[] findByDynamicWhere(String sql, Object[] sqlParams) throws UsersDaoException;

}
