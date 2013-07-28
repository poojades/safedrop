package edu.cmu.sd.filters;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class SecurityTokenizerFilter implements ContainerRequestFilter {
	/**
	 * Apply the filter : check input request, validate or not with user auth
	 */

	Logger logger = Logger.getLogger(SecurityTokenizerFilter.class);

	@Context 
	HttpServletRequest servletRequest; 

	@Context 
	HttpServletResponse servletResponse; 


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