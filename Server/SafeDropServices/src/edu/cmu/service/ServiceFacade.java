package edu.cmu.service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

@Path("/service")
public class ServiceFacade {

	Logger logger = Logger.getLogger(ServiceFacade.class);
	
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

}
