package edu.cmu.sd.intf;

import java.util.List;

import edu.cmu.sd.dto.Notifications;
import edu.cmu.sd.exceptions.SafeDropException;

public interface INotificationManager {
	public List<Notifications> getNotifications(String email, int count) throws SafeDropException;
}
