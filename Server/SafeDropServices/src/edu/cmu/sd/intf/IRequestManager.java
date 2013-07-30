/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.intf;

import edu.cmu.sd.exceptions.SafeDropException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IRequestManager.
 */
public interface IRequestManager {
	
	/**
	 * Request pickup.
	 *
	 * @param email the email
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public String requestPickup(String email) throws SafeDropException;
	
	/**
	 * Cancel pickup.
	 *
	 * @param requestId the request id
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public String cancelPickup(int requestId) throws SafeDropException;
	
	/**
	 * Accept volunteer.
	 *
	 * @param requestId the request id
	 * @param volunteerEmail the volunteer email
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public String acceptVolunteer(int requestId, String volunteerEmail) throws SafeDropException;
	
	/**
	 * Accept requester.
	 *
	 * @param requestId the request id
	 * @param volunteerEmail the volunteer email
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public String acceptRequester(int requestId, String volunteerEmail) throws SafeDropException;
	
	/**
	 * Gets the request status.
	 *
	 * @param requestId the request id
	 * @return the request status
	 * @throws SafeDropException the safe drop exception
	 */
	public String getRequestStatus(int requestId) throws SafeDropException;
	
	/**
	 * Close request.
	 *
	 * @param requestId the request id
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public String closeRequest(int requestId) throws SafeDropException;
	
	/**
	 * Archive request.
	 *
	 * @param requestId the request id
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public String archiveRequest(int requestId) throws SafeDropException;
}
