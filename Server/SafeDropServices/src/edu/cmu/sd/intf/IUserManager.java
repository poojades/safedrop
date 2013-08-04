/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.intf;

import edu.cmu.sd.dto.Users;
import edu.cmu.sd.exceptions.SafeDropException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IUserManager.
 */
public interface IUserManager {
	
	/**
	 * Sets the user info.
	 *
	 * @param email the email
	 * @param firstname the firstname
	 * @param lastname the lastname
	 * @param mobile the mobile
	 * @param econtact the econtact
	 * @param ename the ename
	 * @param status the status
	 * @param lastLat the last lat
	 * @param lastLong the last long
	 * @param zip the zip
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public String setUserInfo(String email, 
			String firstname, 
			String lastname,
			String mobile, 
			String econtact, 
			String ename, 
			String status,
			String lastLat, 
			String lastLong, 
			String zip) throws SafeDropException;
	
	/**
	 * Gets the user info.
	 *
	 * @param email the email
	 * @return the user info
	 * @throws SafeDropException the safe drop exception
	 */
	public Users getUserInfo(String email) throws SafeDropException;
	
	
	public Users getOtherUserInfo(String email, int requestId) throws SafeDropException;
}
