package edu.cmu.sd.intf;

import edu.cmu.sd.exceptions.SafeDropException;

public interface IRequestManager {
	public int requestPickup(String email) throws SafeDropException;
	public String cancelPickup(int requestId) throws SafeDropException;
	public String acceptVolunteer(String requestId, String volunteerEmail) throws SafeDropException;
	public String acceptRequester(String requestId, String volunteerEmail) throws SafeDropException;
	public String getRequestStatus(String requestId) throws SafeDropException;
	public String closeRequest(String requestId) throws SafeDropException;
	public String archiveRequest(String requestId) throws SafeDropException;
}
