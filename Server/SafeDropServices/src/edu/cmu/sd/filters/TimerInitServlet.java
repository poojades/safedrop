/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.filters;

import java.util.Timer;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import edu.cmu.sd.timer.TimerFacade;
import edu.cmu.sd.utils.SDConstants;


// TODO: Auto-generated Javadoc
/**
 * The Class TimerInitServlet.
 */
public class TimerInitServlet implements javax.servlet.ServletContextListener {
	
	/** The timer. */
	private Timer timer;

	/** The Constant logger. */
	protected static final Logger logger = Logger.getLogger( TimerFacade.class );
	
	/**
	 * Instantiates a new timer init servlet.
	 */
	public TimerInitServlet() {
		super();
	}


	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Initializing SafeDrop Timer Services");
		System.out.println("Starting SafeDrop");
		if (timer==null){
			timer=new Timer();
			timer.scheduleAtFixedRate(new TimerFacade(), SDConstants.TimerDelay, SDConstants.TimerDelay);
		}
	}
}

