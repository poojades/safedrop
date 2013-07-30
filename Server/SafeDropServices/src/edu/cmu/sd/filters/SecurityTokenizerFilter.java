/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.filters;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityTokenizerFilter.
 */
public class SecurityTokenizerFilter implements ContainerRequestFilter {

	/** The logger. */
	Logger logger = Logger.getLogger(SecurityTokenizerFilter.class);

	/** The servlet request. */
	@Context 
	HttpServletRequest servletRequest; 

	/** The servlet response. */
	@Context 
	HttpServletResponse servletResponse; 


	/* (non-Javadoc)
	 * @see com.sun.jersey.spi.container.ContainerRequestFilter#filter(com.sun.jersey.spi.container.ContainerRequest)
	 */
	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {
		String method = containerRequest.getMethod();
		String path = containerRequest.getPath(true);
		if(method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))){
			return containerRequest;
		}
		String hash = containerRequest.getHeaderValue("hash");
		if (hash!=null && hash.trim().length()==0){
			throw new WebApplicationException(Status.SERVICE_UNAVAILABLE);
		}
		else
			return containerRequest;
	}

}