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
 * The Interface NotificationsDao.
 */
public interface NotificationsDao
{
	
	/**
	 * Inserts a new row in the notifications table.
	 *
	 * @param dto the dto
	 * @return the notifications pk
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public NotificationsPk insert(Notifications dto) throws NotificationsDaoException;

	/**
	 * Updates a single row in the notifications table.
	 *
	 * @param pk the pk
	 * @param dto the dto
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public void update(NotificationsPk pk, Notifications dto) throws NotificationsDaoException;

	/**
	 * Deletes a single row in the notifications table.
	 *
	 * @param pk the pk
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public void delete(NotificationsPk pk) throws NotificationsDaoException;

	/**
	 * Returns the rows from the notifications table that matches the specified primary-key value.
	 *
	 * @param pk the pk
	 * @return the notifications
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications findByPrimaryKey(NotificationsPk pk) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the notifications
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications findByPrimaryKey(int id) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'recevier = :recevier'.
	 *
	 * @param recevier the recevier
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereRecevierEquals(String recevier) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria ''.
	 *
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findAll() throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'sender = :sender'.
	 *
	 * @param sender the sender
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByUsers(String sender) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'requestid = :requestid'.
	 *
	 * @param requestid the requestid
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByRequest(int requestid) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'receiver = :receiver'.
	 *
	 * @param receiver the receiver
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByUsers2(String receiver) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereIdEquals(int id) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'text = :text'.
	 *
	 * @param text the text
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereTextEquals(String text) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'receiver = :receiver'.
	 *
	 * @param receiver the receiver
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereReceiverEquals(String receiver) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'requestid = :requestid'.
	 *
	 * @param requestid the requestid
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereRequestidEquals(int requestid) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'created = :created'.
	 *
	 * @param created the created
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereCreatedEquals(Date created) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'sender = :sender'.
	 *
	 * @param sender the sender
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereSenderEquals(String sender) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the criteria 'type = :type'.
	 *
	 * @param type the type
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findWhereTypeEquals(String type) throws NotificationsDaoException;

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
	 * Returns all rows from the notifications table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByDynamicSelect(String sql, Object[] sqlParams) throws NotificationsDaoException;

	/**
	 * Returns all rows from the notifications table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the notifications[]
	 * @throws NotificationsDaoException the notifications dao exception
	 */
	public Notifications[] findByDynamicWhere(String sql, Object[] sqlParams) throws NotificationsDaoException;

}
