package edu.cmu.sd.intf;

import edu.cmu.sd.dto.Users;
import edu.cmu.sd.exceptions.SafeDropException;

public interface IUserManager {
	public String setUserInfo(String email, String lastLat, String lastLong, String zip) throws SafeDropException;
	public Users getUserInfo(String email) throws SafeDropException;
}
