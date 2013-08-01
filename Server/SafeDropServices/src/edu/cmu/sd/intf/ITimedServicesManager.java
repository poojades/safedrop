/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.intf;

import edu.cmu.sd.exceptions.SafeDropException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IUserManager.
 */
public interface ITimedServicesManager {
	
	/**
	 * Send request notifications.
	 *
	 * @throws SafeDropException the safe drop exception
	 */
	public void sendRequestNotifications() throws SafeDropException;
	
	/**
	 * Expire requests.
	 *
	 * @throws SafeDropException the safe drop exception
	 */
	public void expireRequests() throws SafeDropException;
	
	/**
	 * Expire notifications.
	 *
	 * @throws SafeDropException the safe drop exception
	 */
	public void expireNotifications() throws SafeDropException;
}
