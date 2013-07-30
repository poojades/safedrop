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
 * The Interface UsersDao.
 */
public interface UsersDao
{
	
	/**
	 * Inserts a new row in the users table.
	 *
	 * @param dto the dto
	 * @return the users pk
	 * @throws UsersDaoException the users dao exception
	 */
	public UsersPk insert(Users dto) throws UsersDaoException;

	/**
	 * Updates a single row in the users table.
	 *
	 * @param pk the pk
	 * @param dto the dto
	 * @throws UsersDaoException the users dao exception
	 */
	public void update(UsersPk pk, Users dto) throws UsersDaoException;

	/**
	 * Deletes a single row in the users table.
	 *
	 * @param pk the pk
	 * @throws UsersDaoException the users dao exception
	 */
	public void delete(UsersPk pk) throws UsersDaoException;

	/**
	 * Returns the rows from the users table that matches the specified primary-key value.
	 *
	 * @param pk the pk
	 * @return the users
	 * @throws UsersDaoException the users dao exception
	 */
	public Users findByPrimaryKey(UsersPk pk) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'email = :email'.
	 *
	 * @param email the email
	 * @return the users
	 * @throws UsersDaoException the users dao exception
	 */
	public Users findByPrimaryKey(String email) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria ''.
	 *
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findAll() throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'email = :email'.
	 *
	 * @param email the email
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereEmailEquals(String email) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'firstname = :firstname'.
	 *
	 * @param firstname the firstname
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereFirstnameEquals(String firstname) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'lastname = :lastname'.
	 *
	 * @param lastname the lastname
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereLastnameEquals(String lastname) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'mobile = :mobile'.
	 *
	 * @param mobile the mobile
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereMobileEquals(String mobile) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'econtact = :econtact'.
	 *
	 * @param econtact the econtact
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereEcontactEquals(String econtact) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'ename = :ename'.
	 *
	 * @param ename the ename
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereEnameEquals(String ename) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'status = :status'.
	 *
	 * @param status the status
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereStatusEquals(String status) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'lastactive = :lastactive'.
	 *
	 * @param lastactive the lastactive
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereLastactiveEquals(Date lastactive) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'lastlat = :lastlat'.
	 *
	 * @param lastlat the lastlat
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereLastlatEquals(String lastlat) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'lastlong = :lastlong'.
	 *
	 * @param lastlong the lastlong
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereLastlongEquals(String lastlong) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the criteria 'zip = :zip'.
	 *
	 * @param zip the zip
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findWhereZipEquals(String zip) throws UsersDaoException;

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
	 * Returns all rows from the users table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findByDynamicSelect(String sql, Object[] sqlParams) throws UsersDaoException;

	/**
	 * Returns all rows from the users table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the users[]
	 * @throws UsersDaoException the users dao exception
	 */
	public Users[] findByDynamicWhere(String sql, Object[] sqlParams) throws UsersDaoException;

}
