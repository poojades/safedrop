/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.exceptions;

// TODO: Auto-generated Javadoc
/**
 * A base class for all checked exceptions in the application.
 * 
 
 * 
 * @version 1.0
 */
public class SafeDropException extends Exception {

    /** The Constant serialVersionUID. */
    static final long serialVersionUID = -5829545098534135052L;

    /**
     * the message of the CollaborationException.
     */
    private String exceptionMessage;

    /**
     * A public constructor for CollaborationException containing no arguments.
     *  
     */
    public SafeDropException() {
    }

    /**
     * A public constructor for CollaborationException specifying exception message.
     * <p>
     * 
     * @param msg
     *            exception message.
     *  
     */
    public SafeDropException(String msg) {
        this.exceptionMessage = msg;
    }

    /**
     * A public constructor of <code>CollaborationException</code> containing
     * message and root cause (as <code>Throwable</code>) of the exception.
     * 
     * @param msg
     *            exception message.
     * @param e
     *            Throwable object.
     *  
     */
    public SafeDropException(String msg, Throwable e) {
        this.exceptionMessage = msg;
        this.initCause(e);
    }

    /**
     * sets the root cause of the exception. Used for setting Java built in
     * exception in <code>CollaborationException</code>.
     * 
     * @param e
     *            Throwable object.
     *  
     */
    public void setCause(Throwable e) {
        this.initCause(e);
    }

    /*
     * Gets the class name and exception message.
     * 
     * @see java.lang.Object#toString()
     */
    /* (non-Javadoc)
     * @see java.lang.Throwable#toString()
     */
    public String toString() {
        String s = getClass().getName();
        return s + ": " + exceptionMessage;
    }

    /*
     * Gets the message of the exception. equivalent to Exception.getMessage().
     * 
     * @see java.lang.Throwable#getMessage()
     */
    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        return exceptionMessage;
    }
}