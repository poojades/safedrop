/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.timer;

import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import edu.cmu.sd.dao.NotificationsDao;
import edu.cmu.sd.dao.RequestDao;
import edu.cmu.sd.dao.UsersDao;
import edu.cmu.sd.dto.Notifications;
import edu.cmu.sd.dto.Request;
import edu.cmu.sd.dto.RequestPk;
import edu.cmu.sd.dto.Users;
import edu.cmu.sd.exceptions.NotificationsDaoException;
import edu.cmu.sd.exceptions.RequestDaoException;
import edu.cmu.sd.exceptions.SafeDropException;
import edu.cmu.sd.exceptions.UsersDaoException;
import edu.cmu.sd.factory.NotificationsDaoFactory;
import edu.cmu.sd.factory.RequestDaoFactory;
import edu.cmu.sd.factory.UsersDaoFactory;
import edu.cmu.sd.intf.ITimedServicesManager;
import edu.cmu.sd.utils.SDConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class TimerFacade.
 */
public class TimerFacade extends TimerTask implements ITimedServicesManager {


	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger( TimerFacade.class );


	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		try {
			expireNotifications();
			expireRequests();
			sendRequestNotifications();
		} catch (SafeDropException e) {
			logger.debug(e);
		}
	}


	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.ITimedServicesManager#sendRequestNotifications()
	 */
	@Override
	public void sendRequestNotifications() throws SafeDropException {
		RequestDao dao = RequestDaoFactory.create();
		try {
			Request[] arrayReq = dao.findWhereStatusEquals(SDConstants.REQ_NEW_STATUS);
			UsersDao userDao = null;
			if (arrayReq.length>0){
				userDao = UsersDaoFactory.create();
			}

			int notifications = 0;
			for (Request req : arrayReq){
				try {
					Users requester = userDao.findByPrimaryKey(req.getRequester());
					notifications=0;
					if (null!=requester && requester.getZip().trim().length()!=0){
						String zip = requester.getZip().trim();
						Users[] volunteers = userDao.findWhereZipEquals(zip);
						NotificationsDao notiDao = null;
						if (volunteers.length>0)
						{
							notiDao=NotificationsDaoFactory.create();
						}
						for(Users volunteer : volunteers){
							if (!volunteer.getEmail().equals(req.getRequester())){
								Notifications notidto = new Notifications();
								notidto.setCreated(new Date());
								notidto.setType(SDConstants.TYPE_NOTIFICATION);
								notidto.setReceiver(volunteer.getEmail());
								notidto.setSender(req.getRequester());
								notidto.setRequestid(req.getId());
								notidto.setText(requester.getFirstname() + " needs your help for SafeDrop, can you help?");
								try {
									notiDao.insert(notidto);
									++notifications;
								} catch (NotificationsDaoException e) {
									continue;
								}
							}
						}
					}
				} catch (UsersDaoException e) {
					continue;
				}
				if (notifications>0){
					req.setStatus(SDConstants.REQ_PENDING_STATUS);
					dao.update(new RequestPk(req.getId()), req);
				}
			}
		} catch (RequestDaoException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.ITimedServicesManager#expireRequests()
	 */
	@Override
	public void expireRequests() throws SafeDropException {

	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.ITimedServicesManager#expireNotifications()
	 */
	@Override
	public void expireNotifications() throws SafeDropException {

	}



}
