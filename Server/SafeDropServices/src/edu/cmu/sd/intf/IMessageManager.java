/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.intf;

import java.util.List;

import edu.cmu.sd.dto.Notifications;
import edu.cmu.sd.exceptions.SafeDropException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessageManager.
 */
public interface IMessageManager {
	
	/**
	 * Gets the messages.
	 *
	 * @param email the email
	 * @param count the count
	 * @return the messages
	 * @throws SafeDropException the safe drop exception
	 */
	public List<Notifications> getMessages(String email, int count) throws SafeDropException;
	
	/**
	 * Send message.
	 *
	 * @param from the from
	 * @param to the to
	 * @param message the message
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public String sendMessage(String from, String to, String message) throws SafeDropException;
}
