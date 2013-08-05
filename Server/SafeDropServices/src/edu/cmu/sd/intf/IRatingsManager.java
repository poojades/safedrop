/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.intf;

import edu.cmu.sd.dto.Ratings;
import edu.cmu.sd.exceptions.SafeDropException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IRequestManager.
 */
public interface IRatingsManager {
	
	/**
	 * Request pickup.
	 *
	 * @param volunteerEmail the volunteer email
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public Ratings[] getRatings(String volunteerEmail) throws SafeDropException;
	
	/**
	 * Adds the rating.
	 *
	 * @param requestId the request id
	 * @param requesterEmail the requester email
	 * @param volunteerEmail the volunteer email
	 * @param text the text
	 * @param value the value
	 * @return the string
	 * @throws SafeDropException the safe drop exception
	 */
	public String addRating(int requestId, String requesterEmail,String volunteerEmail, String text, float value ) throws SafeDropException;
	
}
