package edu.cmu.sd.intf;

import java.util.List;

import edu.cmu.sd.dto.Messages;
import edu.cmu.sd.exceptions.SafeDropException;

public interface IMessageManager {
	public List<Messages> getMessages(String email, int count) throws SafeDropException;
	public String sendMessage(String from, String to, String message) throws SafeDropException;
}
