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
 * The Interface INotificationManager.
 */
public interface INotificationManager {
	
	/**
	 * Gets the notifications.
	 *
	 * @param email the email
	 * @param afterMessageId the after message id
	 * @return the notifications
	 * @throws SafeDropException the safe drop exception
	 */
	public List<Notifications> getNotifications(String email, int afterMessageId) throws SafeDropException;
}
