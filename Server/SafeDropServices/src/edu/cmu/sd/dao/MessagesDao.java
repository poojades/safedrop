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
 * The Interface MessagesDao.
 */
public interface MessagesDao
{
	
	/**
	 * Inserts a new row in the messages table.
	 *
	 * @param dto the dto
	 * @return the messages pk
	 * @throws MessagesDaoException the messages dao exception
	 */
	public MessagesPk insert(Messages dto) throws MessagesDaoException;

	/**
	 * Updates a single row in the messages table.
	 *
	 * @param pk the pk
	 * @param dto the dto
	 * @throws MessagesDaoException the messages dao exception
	 */
	public void update(MessagesPk pk, Messages dto) throws MessagesDaoException;

	/**
	 * Deletes a single row in the messages table.
	 *
	 * @param pk the pk
	 * @throws MessagesDaoException the messages dao exception
	 */
	public void delete(MessagesPk pk) throws MessagesDaoException;

	/**
	 * Returns the rows from the messages table that matches the specified primary-key value.
	 *
	 * @param pk the pk
	 * @return the messages
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages findByPrimaryKey(MessagesPk pk) throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the messages
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages findByPrimaryKey(int id) throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the criteria ''.
	 *
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findAll() throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the criteria 'sender = :sender'.
	 *
	 * @param sender the sender
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findByUsers(String sender) throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the criteria 'receiver = :receiver'.
	 *
	 * @param receiver the receiver
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findByUsers2(String receiver) throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the criteria 'id = :id'.
	 *
	 * @param id the id
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereIdEquals(int id) throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the criteria 'text = :text'.
	 *
	 * @param text the text
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereTextEquals(String text) throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the criteria 'sender = :sender'.
	 *
	 * @param sender the sender
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereSenderEquals(String sender) throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the criteria 'receiver = :receiver'.
	 *
	 * @param receiver the receiver
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereReceiverEquals(String receiver) throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the criteria 'created = :created'.
	 *
	 * @param created the created
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findWhereCreatedEquals(Date created) throws MessagesDaoException;

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
	 * Returns all rows from the messages table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findByDynamicSelect(String sql, Object[] sqlParams) throws MessagesDaoException;

	/**
	 * Returns all rows from the messages table that match the specified arbitrary SQL statement.
	 *
	 * @param sql the sql
	 * @param sqlParams the sql params
	 * @return the messages[]
	 * @throws MessagesDaoException the messages dao exception
	 */
	public Messages[] findByDynamicWhere(String sql, Object[] sqlParams) throws MessagesDaoException;

}
