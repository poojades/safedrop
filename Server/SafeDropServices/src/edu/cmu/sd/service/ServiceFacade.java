package edu.cmu.sd.service;

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

import edu.cmu.sd.dao.RequestDao;
import edu.cmu.sd.dao.UsersDao;
import edu.cmu.sd.dto.Messages;
import edu.cmu.sd.dto.Notifications;
import edu.cmu.sd.dto.RequestPk;
import edu.cmu.sd.dto.Users;
import edu.cmu.sd.exceptions.RequestDaoException;
import edu.cmu.sd.exceptions.SafeDropException;
import edu.cmu.sd.exceptions.UsersDaoException;
import edu.cmu.sd.factory.RequestDaoFactory;
import edu.cmu.sd.factory.UsersDaoFactory;
import edu.cmu.sd.intf.IMessageManager;
import edu.cmu.sd.intf.INotificationManager;
import edu.cmu.sd.intf.IRequestManager;
import edu.cmu.sd.intf.IUserManager;
import edu.cmu.sd.utils.SDConstants;

@Path("/service")
public class ServiceFacade implements IRequestManager, IUserManager, INotificationManager, IMessageManager{

	protected static final Logger logger = Logger.getLogger( ServiceFacade.class );


	public ServiceFacade() {
		super();
	}

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@Context 
	HttpServletResponse response; 

	@Context 
	ServletContext context;

	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/status")
	public String status() {
		return "Running";
	}

	public List<Messages> getMessages(String email, int count) throws SafeDropException {

		return null;
	}

	@Override
	public String sendMessage(String from, String to, String message)
			throws SafeDropException {
		return null;
	}

	@Override
	public List<Notifications> getNotifications(String email, int count)
			throws SafeDropException {
		return null;
	}

	@Override
	public String setUserInfo(String email, String lastLat, String lastLong,
			String zip) throws SafeDropException {
		return null;
	}

	@Override
	public Users getUserInfo(String email) throws SafeDropException {
		return null;
	}

	@Override
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/requestPickup")
	public int requestPickup(@FormParam("email") String email) throws SafeDropException {
		logger.debug("ServiceFacade.requestPickup()");
		RequestPk pk =null;
		UsersDao userDao = UsersDaoFactory.create();
		try {
			Users user = userDao.findByPrimaryKey(email);
			if (!user.getStatus().equalsIgnoreCase(SDConstants.SUCCESS)){
				throw new SafeDropException("Requester should be active to create a SafeDrop request");
			}
		} catch (UsersDaoException e) {
			throw new SafeDropException("Failed to validate requester");
		}
		RequestDao dao = RequestDaoFactory.create();
		edu.cmu.sd.dto.Request request = new  edu.cmu.sd.dto.Request();
		pk = request.createPk();
		request.setRequester(email);
		logger.debug(request);
		try {
			dao.insert(request);
		} catch (RequestDaoException e) {
			throw new SafeDropException("Failed to create a new pickup request");
		}
		return pk.getId();
	}

	@Override
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/cancelPickup")
	public String cancelPickup(@FormParam("requestId") int requestId) throws SafeDropException {
		logger.debug("ServiceFacade.cancelPickup()");
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
		return SDConstants.SUCCESS;
	}

	@Override
	public String acceptVolunteer(String requestId, String volunteerEmail)
			throws SafeDropException {
		return null;
	}

	@Override
	public String acceptRequester(String requestId, String volunteerEmail)
			throws SafeDropException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestStatus(String requestId) throws SafeDropException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String closeRequest(String requestId) throws SafeDropException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String archiveRequest(String requestId) throws SafeDropException {
		// TODO Auto-generated method stub
		return null;
	}

}
