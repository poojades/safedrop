/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import edu.cmu.sd.dao.NotificationsDao;
import edu.cmu.sd.dao.RatingsDao;
import edu.cmu.sd.dao.RequestDao;
import edu.cmu.sd.dao.UsersDao;
import edu.cmu.sd.dto.Notifications;
import edu.cmu.sd.dto.NotificationsPk;
import edu.cmu.sd.dto.Ratings;
import edu.cmu.sd.dto.RatingsPk;
import edu.cmu.sd.dto.RequestPk;
import edu.cmu.sd.dto.Users;
import edu.cmu.sd.dto.UsersPk;
import edu.cmu.sd.exceptions.NotificationsDaoException;
import edu.cmu.sd.exceptions.RatingsDaoException;
import edu.cmu.sd.exceptions.RequestDaoException;
import edu.cmu.sd.exceptions.SafeDropException;
import edu.cmu.sd.exceptions.UsersDaoException;
import edu.cmu.sd.factory.NotificationsDaoFactory;
import edu.cmu.sd.factory.RatingsDaoFactory;
import edu.cmu.sd.factory.RequestDaoFactory;
import edu.cmu.sd.factory.UsersDaoFactory;
import edu.cmu.sd.intf.IMessageManager;
import edu.cmu.sd.intf.INotificationManager;
import edu.cmu.sd.intf.IRatingsManager;
import edu.cmu.sd.intf.IRequestManager;
import edu.cmu.sd.intf.IUserManager;
import edu.cmu.sd.utils.SDConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceFacade.
 */
@Path("/service")
public class ServiceFacade implements IRequestManager, IUserManager, INotificationManager, IMessageManager, IRatingsManager{

	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger( ServiceFacade.class );


	/**
	 * Instantiates a new service facade.
	 */
	public ServiceFacade() {
		super();
	}

	/** The uri info. */
	@Context
	UriInfo uriInfo;
	
	/** The request. */
	@Context
	Request request;

	/** The response. */
	@Context 
	HttpServletResponse response; 

	/** The context. */
	@Context 
	ServletContext context;

	/**
	 * Status.
	 *
	 * @return the string
	 */
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/status")
	public String status() {
		return "Running";
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IMessageManager#getMessages(java.lang.String, int)
	 */
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getMessages/{email}/{afterMessageId}")
	public List<Notifications> getMessages(@PathParam("email") String email, @PathParam("afterMessageId")  int afterMessageId) throws SafeDropException {
		if ((null==email) || (email.trim().length()==0))
			throw new SafeDropException("Email cannot be null or of length zero");
		
		
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(email);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Requester should be available & active to create a SafeDrop request");
			}
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to validate requester");
		}

		NotificationsDao dao = NotificationsDaoFactory.create();
		Object[] sqlParams = new Object[3];
		sqlParams[0]=SDConstants.TYPE_MESSAGE;
		sqlParams[1]=email;
		sqlParams[2]=afterMessageId;
		try {
			List<Notifications> list = Arrays.asList(dao.findByDynamicWhere("TYPE = ? AND RECEIVER = ? AND ID > ? ORDER BY CREATED DESC",sqlParams));
			if (list!=null)
				return list;
			else
				return Collections.<Notifications>emptyList();
		} catch (NotificationsDaoException e) {
			throw new SafeDropException("Failed to retrieve messages"); 
		}
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IMessageManager#sendMessage(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/sendMessage")
	public String sendMessage(@FormParam("from") String from, @FormParam("to") String to, @FormParam("message") String message)
			throws SafeDropException {

		if ((null==from) || (null==to) || (from.equalsIgnoreCase(to)))
			throw new SafeDropException("Message From and To cannot be the same or null");
		
		
		if ((null==from) || (from.trim().length()==0))
			throw new SafeDropException("Message From cannot be null or of length zero");
		
		
		if ((null==to) || (to.trim().length()==0))
			throw new SafeDropException("Message To cannot be null or of length zero");
		
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(from);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Message From should be available & active to create a SafeDrop request");
			}
			user = userDao.findByPrimaryKey(to);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Message To should be available & active to create a SafeDrop request");
			}
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to validate requester");
		}


		NotificationsDao dao = NotificationsDaoFactory.create();
		Notifications messages = new Notifications();
		messages.setCreated(new Date());
		messages.setSender(from);
		messages.setReceiver(to);
		messages.setText(message);
		messages.setType(SDConstants.TYPE_MESSAGE);
		try {
			dao.insert(messages);
			return SDConstants.SUCCESS;
		} catch (NotificationsDaoException e) {
			throw new SafeDropException("Failed to send message"); 
		}
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.INotificationManager#getNotifications(java.lang.String, int)
	 */
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getNotifications/{email}/{afterMessageId}")
	public List<Notifications> getNotifications(@PathParam("email") String email, @PathParam("afterMessageId") int afterMessageId)
			throws SafeDropException {
		
		if ((null==email) || (email.trim().length()==0))
			throw new SafeDropException("Email cannot be null or of length zero");
		
		
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(email);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Requester should be available & active to create a SafeDrop request");
			}
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to validate requester");
		}


		NotificationsDao dao = NotificationsDaoFactory.create();
		Object[] sqlParams = new Object[3];
		sqlParams[0]=SDConstants.TYPE_NOTIFICATION;
		sqlParams[1]=email;
		sqlParams[2]=afterMessageId;
		try {
			List<Notifications> list = Arrays.asList(dao.findByDynamicWhere("TYPE=? AND RECEIVER = ? AND ID>? ORDER BY CREATED DESC",sqlParams));
			if (list!=null)
				return list;
			else
				return Collections.<Notifications>emptyList();
		} catch (NotificationsDaoException e) {
			throw new SafeDropException("Failed to retrieve notifications"); 
		}
	}



	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IRequestManager#requestPickup(java.lang.String)
	 */
	@Override
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/requestPickup")
	public String requestPickup(@FormParam("email") String email) throws SafeDropException {
		
		if ((null==email) || (email.trim().length()==0))
			throw new SafeDropException("Email cannot be null or of length zero");
		
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(email);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Requester should be available & active to create a SafeDrop request");
			}
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to validate requester");
		}


		RequestDao dao = RequestDaoFactory.create();
		RequestPk pk =null;
		edu.cmu.sd.dto.Request request = new  edu.cmu.sd.dto.Request();
		request.setRequester(email);
		request.setCreated(new Date());
		request.setStatus(SDConstants.REQ_NEW_STATUS);
		logger.debug(request);
		try {
			pk = dao.insert(request);
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to create a new pickup request");
		}

		return String.valueOf(pk.getId());
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IRequestManager#cancelPickup(int)
	 */
	@Override
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/cancelPickup")
	public String cancelPickup(@FormParam("requestId") int requestId) throws SafeDropException {
		
		RequestDao dao = RequestDaoFactory.create();
		edu.cmu.sd.dto.Request request;
		try {
			request = dao.findByPrimaryKey(new RequestPk(requestId));
		} catch (RequestDaoException e) {
			throw new SafeDropException("Request is not available, cannot cancel request");
		}
		if (request.getStatus().equalsIgnoreCase(SDConstants.REQ_DONE_STATUS)){
			throw new SafeDropException("Request is already done, cannot cancel request");
		}
		if (request.getStatus().equalsIgnoreCase(SDConstants.REQ_CANCEL_STATUS)){
			throw new SafeDropException("Request is already cancelled, cannot cancel request again");
		}
		request.setStatus(SDConstants.REQ_CANCEL_STATUS);
		try {
			dao.update(new RequestPk(request.getId()), request);
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to cancel pickup request");
		}

		//scenario for notifying requester and or volunteer may be required
		return SDConstants.SUCCESS;
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IRequestManager#acceptVolunteer(int, java.lang.String)
	 */
	@Override
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/acceptVolunteer")
	public String acceptVolunteer(@FormParam("requestId") int requestId, @FormParam("volunteerEmail") String volunteerEmail)
			throws SafeDropException {
		
		if ((null==volunteerEmail) || (volunteerEmail.trim().length()==0))
			throw new SafeDropException("volunteerEmail cannot be null or of length zero");
		

		//checking if requestId status is on Accepted
		RequestDao dao = RequestDaoFactory.create();
		edu.cmu.sd.dto.Request request = null;
		try {
			request = dao.findByPrimaryKey(requestId);
			if (request==null || !request.getStatus().equalsIgnoreCase(SDConstants.REQ_ACCEPTED_STATUS)){
				throw new SafeDropException("Request should be available & status should be ACCEPTED to accept a Volunteer");
			}
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to validate request");
		}
		//checking if requester is valid and active
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(volunteerEmail);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Volunteer should be available & active for the volunteer to accept a Volunteer");
			}
			if (request.getRequester().equalsIgnoreCase(volunteerEmail)){
				throw new SafeDropException("Requester and Volunteer cannot be the same");
			}
			user = userDao.findByPrimaryKey(request.getRequester());
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Requester should be available & active for the volunteer to accept a Volunteer");
			}
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to validate requester or volunteer");
		}
		//checking if requester had a notification 
		NotificationsDao notiDao = NotificationsDaoFactory.create();
		Object[] sqlParams = new Object[3];
		sqlParams[0]=SDConstants.TYPE_NOTIFICATION;
		sqlParams[1]=request.getRequester();
		sqlParams[2]=requestId;
		List<Notifications> list = null;
		try {
			list = Arrays.asList(notiDao.findByDynamicWhere("TYPE = ? AND RECEIVER = ? AND REQUESTID = ? ORDER BY CREATED DESC",sqlParams));
			if (list==null || list.size()==0)
			{
				throw new SafeDropException("Failed to check if requester actually received a notification"); 
			}
		} catch (NotificationsDaoException e) {
			throw new SafeDropException("Failed to check if requester actually received a notification"); 
		}
		//removing all notifications for this particular requestId and create new notification
		sqlParams = new Object[2];
		sqlParams[0]=SDConstants.TYPE_NOTIFICATION;;
		sqlParams[1]=requestId;
		try {
			list = Arrays.asList(notiDao.findByDynamicWhere("TYPE=? AND REQUESTID = ? ORDER BY CREATED DESC",sqlParams));
			if (list!=null) 
			{
				for(Notifications noti : list){
					notiDao.delete(new NotificationsPk(noti.getId()));
				}
			}
		} catch (NotificationsDaoException e) {
			throw new SafeDropException("Failed to delete notifications for all possbile volunteers"); 
		}
		try{
			Notifications notidto = new Notifications();
			notidto.setType(SDConstants.TYPE_NOTIFICATION);
			notidto.setCreated(new Date());
			notidto.setSender(request.getRequester());
			notidto.setReceiver(volunteerEmail);
			notidto.setRequestid(requestId);
			notidto.setText("Accepted By : " + request.getRequester());
			notiDao.insert(notidto);
		}catch (NotificationsDaoException e) {
			throw new SafeDropException("Failed to send notification to requester"); 
		}
		request.setStatus(SDConstants.REQ_INPROG_STATUS);
		try {
			dao.update(new RequestPk(request.getId()), request);
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to update request status"); 
		}
		return SDConstants.SUCCESS;

	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IRequestManager#acceptRequester(int, java.lang.String)
	 */
	@Override
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/acceptRequester")
	public String acceptRequester(@FormParam("requestId") int requestId, @FormParam("volunteerEmail") String volunteerEmail)
			throws SafeDropException {
		
		
		if ((null==volunteerEmail) || (volunteerEmail.trim().length()==0))
			throw new SafeDropException("volunteerEmail cannot be null or of length zero");
		
		
		
		//checking if requestId status is on Pending
		RequestDao dao = RequestDaoFactory.create();
		edu.cmu.sd.dto.Request request = null;
		try {
			request = dao.findByPrimaryKey(requestId);
			if (request==null || !request.getStatus().equalsIgnoreCase(SDConstants.REQ_PENDING_STATUS)){
				throw new SafeDropException("Request should be available & status should be PENDING to Accept a Requester");
			}
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to validate request");
		}
		//checking if requester is valid and active
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(volunteerEmail);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Volunteer should be available & active for the volunteer to accept Requester");
			}
			if (request.getRequester().equalsIgnoreCase(volunteerEmail)){
				throw new SafeDropException("Requester and Volunteer cannot be the same");
			}
			user = userDao.findByPrimaryKey(request.getRequester());
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Requester should be available & active for the volunteer to accept Requester");
			}
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to validate requester or volunteer");
		}
		//checking if requester had a notification 
		NotificationsDao notiDao = NotificationsDaoFactory.create();
		Object[] sqlParams = new Object[3];
		sqlParams[0]=SDConstants.TYPE_NOTIFICATION;
		sqlParams[1]=volunteerEmail;
		sqlParams[2]=requestId;
		List<Notifications> list = null;
		try {
			list = Arrays.asList(notiDao.findByDynamicWhere("TYPE=? AND RECEIVER = ? AND REQUESTID = ? ORDER BY CREATED DESC",sqlParams));
			if (list==null || list.size()==0)
			{
				throw new SafeDropException("Failed to check if volunteer actually received a notification"); 
			}
		} catch (NotificationsDaoException e) {
			throw new SafeDropException("Failed to check if volunteer actually received a notification"); 
		}
		//removing all notifications for this particular requestId and create new notification
		sqlParams = new Object[2];
		sqlParams[0]=SDConstants.TYPE_NOTIFICATION;
		sqlParams[1]=requestId;
		try {
			list = Arrays.asList(notiDao.findByDynamicWhere("TYPE=? AND REQUESTID = ? ORDER BY CREATED DESC",sqlParams));
			if (list!=null) 
			{
				for(Notifications noti : list){
					notiDao.delete(new NotificationsPk(noti.getId()));
				}
			}
		} catch (NotificationsDaoException e) {
			throw new SafeDropException("Failed to delete notifications for all possbile volunteers"); 
		}
		try{
			Notifications notidto = new Notifications();
			notidto.setType(SDConstants.TYPE_NOTIFICATION);
			notidto.setCreated(new Date());
			notidto.setSender(volunteerEmail);
			notidto.setReceiver(request.getRequester());
			notidto.setRequestid(requestId);
			notidto.setText("Accepted By : " + volunteerEmail);
			notiDao.insert(notidto);
		}catch (NotificationsDaoException e) {
			throw new SafeDropException("Failed to send notification to requester"); 
		}
		request.setStatus(SDConstants.REQ_ACCEPTED_STATUS);
		try {
			dao.update(new RequestPk(request.getId()), request);
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to update request status"); 
		}
		return SDConstants.SUCCESS;
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IRequestManager#getRequestStatus(int)
	 */
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRequestStatus/{requestId}")
	public String getRequestStatus(@PathParam("requestId") int requestId) throws SafeDropException {
		RequestDao dao = RequestDaoFactory.create();
		edu.cmu.sd.dto.Request request = null;
		try {
			request = dao.findByPrimaryKey(requestId);
			if (request==null){
				throw new SafeDropException("Request should be available for fetching status");
			}
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to get request status");
		}
		return request.getStatus();
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IRequestManager#closeRequest(int)
	 */
	@Override
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/closeRequest")
	public String closeRequest(@FormParam("requestId") int requestId) throws SafeDropException {
		//checking if requestId status is on in-progress
		RequestDao dao = RequestDaoFactory.create();
		edu.cmu.sd.dto.Request request = null;
		try {
			request = dao.findByPrimaryKey(requestId);
			if (request==null || !request.getStatus().equalsIgnoreCase(SDConstants.REQ_INPROG_STATUS)){
				throw new SafeDropException("Request should be available & status should be IN-PROGRESS to close");
			}
			request.setStatus(SDConstants.REQ_DONE_STATUS);
			dao.update(new RequestPk(request.getId()), request);
			return SDConstants.SUCCESS;
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to validate request");
		}
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IRequestManager#archiveRequest(int)
	 */
	@Override
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/archiveRequest")
	public String archiveRequest(@FormParam("requestId") int requestId) throws SafeDropException {
		//checking if requestId status is on Close
		RequestDao dao = RequestDaoFactory.create();
		edu.cmu.sd.dto.Request request = null;
		try {
			request = dao.findByPrimaryKey(requestId);
			if (request==null || !request.getStatus().equalsIgnoreCase(SDConstants.REQ_DONE_STATUS)){
				throw new SafeDropException("Request should be available & status should be CLOSED to archive");
			}
			request.setStatus(SDConstants.REQ_ARCHIVED_STATUS);
			dao.update(new RequestPk(request.getId()), request);
			return SDConstants.SUCCESS;
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to validate request");
		}
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IUserManager#setUserInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/setUserInfo")
	public String setUserInfo(@FormParam("email") String email, 
			@FormParam("firstname") String firstname, 
			@FormParam("lastname") String lastname,
			@FormParam("mobile") String mobile, 
			@FormParam("econtact") String econtact, 
			@FormParam("ename") String ename, 
			@FormParam("status") String status,
			@FormParam("lastlat") String lastlat, 
			@FormParam("lastlong") String lastlong, 
			@FormParam("zip") String zip) throws SafeDropException {
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(email);
			if (user==null){
				throw new SafeDropException("User should be available for setting user information");
			}
			if (firstname != null && firstname.trim().length()>0){
				user.setFirstname(firstname);
			}
			if (lastname != null && lastname.trim().length()>0){
				user.setLastname(lastname);
			}
			if (mobile != null && mobile.trim().length()>0){
				user.setMobile(mobile);
			}
			if (econtact != null && econtact.trim().length()>0){
				user.setEcontact(econtact);
			}
			if (ename != null && ename.trim().length()>0){
				user.setEname(ename);
			}
			if (lastlat != null && lastlat.trim().length()>0){
				user.setLastlat(lastlat);
			}
			if (lastlong != null && lastlong.trim().length()>0){
				user.setLastlong(lastlong);
			}
			if (zip != null && zip.trim().length()>0){
				user.setZip(zip);
			}

			if (status != null && (
					status.trim().equalsIgnoreCase(SDConstants.ACTIVE_STATUS) ||
					status.trim().equalsIgnoreCase(SDConstants.INACTIVE_STATUS))){
				user.setStatus(status);
			}
			userDao.update(new UsersPk(user.getEmail()), user);
			return SDConstants.SUCCESS;
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to update user status");
		}
	}

	/* (non-Javadoc)
	 * @see edu.cmu.sd.intf.IUserManager#getUserInfo(java.lang.String)
	 */
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUserInfo/{email}")
	public Users getUserInfo(@PathParam ("email") String email) throws SafeDropException {
		
		if ((null==email) || (email.trim().length()==0))
			throw new SafeDropException("Email cannot be null or of length zero");
		
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(email);
			if (user==null){
				throw new SafeDropException("User should be available for getting user information");
			}
			return user;
		}catch (UsersDaoException e) {
			throw new SafeDropException("Failed to get user status");
		}
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getLastRequestByUser/{email}")
	public String getLastRequestByUser(@PathParam ("email") String email) throws SafeDropException {
		RequestDao dao = RequestDaoFactory.create();
		Object[] sqlParams = new Object[1];
		sqlParams[0]=email;
		try {
			edu.cmu.sd.dto.Request[] request = dao.findByDynamicWhere("REQUESTER=? ORDER BY ID DESC LIMIT 1", sqlParams);
			if (request.length>0)
				return String.valueOf(request[0].getId());
			else
				return "0";
		} catch (RequestDaoException e) {
			throw new SafeDropException("Get last request by User Failed");
		}
				
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOtherUserInfo/{email}/{requestId}")
	public Users getOtherUserInfo(@PathParam ("email") String email, @PathParam ("requestId") int requestId)
			throws SafeDropException {
		RequestDao dao = RequestDaoFactory.create();
		edu.cmu.sd.dto.Request request = null;
		try {
			request = dao.findByPrimaryKey(requestId);
			if (request==null){
				throw new SafeDropException("Request should be available for fetching status");
			}
			if (request.getStatus()!=SDConstants.REQ_INPROG_STATUS){
				if (request.getRequester().equalsIgnoreCase(email)){
					//he is the requester so fetch the volunteer's userInfo
					NotificationsDao notidao = NotificationsDaoFactory.create();
					Notifications[] notification = notidao.findByRequest(requestId);
					if (notification==null || notification.length>1)
						throw new SafeDropException("Failed to get other user status"); 
					else
						return getUserInfo(notification[0].getReceiver());
					
				}
				else{
					//he is the volunteer so fetch the requesters userInfo
					return getUserInfo(request.getRequester());
				}
				
			}
			else
				throw new SafeDropException("Request has to be in IN-PROGRESS Status to send you other user info");
			
		} catch (RequestDaoException | NotificationsDaoException e) {
			throw new SafeDropException("Failed to get other user status");
		}
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRatings/{volunteerEmail}")
	public Ratings[] getRatings(@PathParam ("volunteerEmail")  String volunteerEmail) throws SafeDropException {
		
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(volunteerEmail);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Volunteer should be available & active for the volunteer to accept Requester");
			}
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to validate requester or volunteer");
		}
		
		
		RatingsDao dao = RatingsDaoFactory.create();
		Ratings[] returnarray =null;
		try {
			returnarray =  dao.findByUsers2(volunteerEmail);
			if (returnarray!=null || returnarray.length==1){
				Ratings ratings = new Ratings();
				ratings.setId(0);
				ratings.setRequester("No available ratings.");
				ratings.setText("This user has not yet been rated");
				ratings.setValue(0.0f);
				ratings.setVolunteer(volunteerEmail);
				returnarray = new Ratings[2];
				returnarray[0]=returnarray[0];
				returnarray[1]=ratings;
			}
		} catch (RatingsDaoException e) {
			throw new SafeDropException("Failed to fetch ratings for volunteer");
		}
		return returnarray;
	}

	@Override
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/addRating")
	public String addRating(@FormParam("requestId") int requestId, @FormParam("requesterEmail") String requesterEmail,
			@FormParam("volunteerEmail") String volunteerEmail, @FormParam("text") String text, @FormParam("value") float value)
			throws SafeDropException {
		
		RequestDao daor = RequestDaoFactory.create();
		edu.cmu.sd.dto.Request request = null;
		try {
			request = daor.findByPrimaryKey(requestId);
			if (request==null || !request.getStatus().equalsIgnoreCase(SDConstants.REQ_INPROG_STATUS)){
				throw new SafeDropException("Request should be available & status should be in PROGRESS to add a rating");
			}
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to validate request");
		}
		
		
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(volunteerEmail);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Volunteer should be available & active for fetching rating");
			}
			user = userDao.findByPrimaryKey(requesterEmail);
			if (user==null || !user.getStatus().equalsIgnoreCase(SDConstants.ACTIVE_STATUS)){
				throw new SafeDropException("Requester should be available & active for fetching rating");
			}
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to validate requester or volunteer");
		}
		
		
		RatingsDao dao = RatingsDaoFactory.create();
		Ratings dto = new Ratings();
		RatingsPk pk =null;
		try {
			dto.setCreated(new Date());
			dto.setRequester(requesterEmail);
			dto.setVolunteer(volunteerEmail);
			dto.setRequestid(requestId);
			dto.setText(text);
			dto.setValue(value);
			pk =  dao.insert(dto);
		} catch (RatingsDaoException e) {
			throw new SafeDropException("Failed to fetch ratings for volunteer");
		}
		return pk.toString();
	}
}
