/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */

package edu.cmu.sd.dto;

import edu.cmu.sd.dao.*;
import edu.cmu.sd.factory.*;
import edu.cmu.sd.exceptions.*;

import java.io.Serializable;
import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;


// TODO: Auto-generated Javadoc
/**
 * The Class Users.
 */
@XmlRootElement
public class Users implements Serializable
{
	/** 
	 * This attribute maps to the column email in the users table.
	 */
	protected String email;

	/** 
	 * This attribute maps to the column firstname in the users table.
	 */
	protected String firstname;

	/** 
	 * This attribute maps to the column lastname in the users table.
	 */
	protected String lastname;

	/** 
	 * This attribute maps to the column mobile in the users table.
	 */
	protected String mobile;

	/** 
	 * This attribute maps to the column econtact in the users table.
	 */
	protected String econtact;

	/** 
	 * This attribute maps to the column ename in the users table.
	 */
	protected String ename;

	/** 
	 * This attribute maps to the column status in the users table.
	 */
	protected String status;

	/** 
	 * This attribute maps to the column lastactive in the users table.
	 */
	protected Date lastactive;

	/** 
	 * This attribute maps to the column lastlat in the users table.
	 */
	protected String lastlat;

	/** 
	 * This attribute maps to the column lastlong in the users table.
	 */
	protected String lastlong;

	/** 
	 * This attribute maps to the column zip in the users table.
	 */
	protected String zip;

	/**
	 * Method 'Users'.
	 */
	public Users()
	{
	}

	/**
	 * Method 'getEmail'.
	 *
	 * @return String
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Method 'setEmail'.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Method 'getFirstname'.
	 *
	 * @return String
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * Method 'setFirstname'.
	 *
	 * @param firstname the new firstname
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * Method 'getLastname'.
	 *
	 * @return String
	 */
	public String getLastname()
	{
		return lastname;
	}

	/**
	 * Method 'setLastname'.
	 *
	 * @param lastname the new lastname
	 */
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	/**
	 * Method 'getMobile'.
	 *
	 * @return String
	 */
	public String getMobile()
	{
		return mobile;
	}

	/**
	 * Method 'setMobile'.
	 *
	 * @param mobile the new mobile
	 */
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	/**
	 * Method 'getEcontact'.
	 *
	 * @return String
	 */
	public String getEcontact()
	{
		return econtact;
	}

	/**
	 * Method 'setEcontact'.
	 *
	 * @param econtact the new econtact
	 */
	public void setEcontact(String econtact)
	{
		this.econtact = econtact;
	}

	/**
	 * Method 'getEname'.
	 *
	 * @return String
	 */
	public String getEname()
	{
		return ename;
	}

	/**
	 * Method 'setEname'.
	 *
	 * @param ename the new ename
	 */
	public void setEname(String ename)
	{
		this.ename = ename;
	}

	/**
	 * Method 'getStatus'.
	 *
	 * @return String
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Method 'setStatus'.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * Method 'getLastactive'.
	 *
	 * @return Date
	 */
	public Date getLastactive()
	{
		return lastactive;
	}

	/**
	 * Method 'setLastactive'.
	 *
	 * @param lastactive the new lastactive
	 */
	public void setLastactive(Date lastactive)
	{
		this.lastactive = lastactive;
	}

	/**
	 * Method 'getLastlat'.
	 *
	 * @return String
	 */
	public String getLastlat()
	{
		return lastlat;
	}

	/**
	 * Method 'setLastlat'.
	 *
	 * @param lastlat the new lastlat
	 */
	public void setLastlat(String lastlat)
	{
		this.lastlat = lastlat;
	}

	/**
	 * Method 'getLastlong'.
	 *
	 * @return String
	 */
	public String getLastlong()
	{
		return lastlong;
	}

	/**
	 * Method 'setLastlong'.
	 *
	 * @param lastlong the new lastlong
	 */
	public void setLastlong(String lastlong)
	{
		this.lastlong = lastlong;
	}

	/**
	 * Method 'getZip'.
	 *
	 * @return String
	 */
	public String getZip()
	{
		return zip;
	}

	/**
	 * Method 'setZip'.
	 *
	 * @param zip the new zip
	 */
	public void setZip(String zip)
	{
		this.zip = zip;
	}

	/**
	 * Method 'equals'.
	 *
	 * @param _other the _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof Users)) {
			return false;
		}
		
		final Users _cast = (Users) _other;
		if (email == null ? _cast.email != email : !email.equals( _cast.email )) {
			return false;
		}
		
		if (firstname == null ? _cast.firstname != firstname : !firstname.equals( _cast.firstname )) {
			return false;
		}
		
		if (lastname == null ? _cast.lastname != lastname : !lastname.equals( _cast.lastname )) {
			return false;
		}
		
		if (mobile == null ? _cast.mobile != mobile : !mobile.equals( _cast.mobile )) {
			return false;
		}
		
		if (econtact == null ? _cast.econtact != econtact : !econtact.equals( _cast.econtact )) {
			return false;
		}
		
		if (ename == null ? _cast.ename != ename : !ename.equals( _cast.ename )) {
			return false;
		}
		
		if (status == null ? _cast.status != status : !status.equals( _cast.status )) {
			return false;
		}
		
		if (lastactive == null ? _cast.lastactive != lastactive : !lastactive.equals( _cast.lastactive )) {
			return false;
		}
		
		if (lastlat == null ? _cast.lastlat != lastlat : !lastlat.equals( _cast.lastlat )) {
			return false;
		}
		
		if (lastlong == null ? _cast.lastlong != lastlong : !lastlong.equals( _cast.lastlong )) {
			return false;
		}
		
		if (zip == null ? _cast.zip != zip : !zip.equals( _cast.zip )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'.
	 *
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		if (email != null) {
			_hashCode = 29 * _hashCode + email.hashCode();
		}
		
		if (firstname != null) {
			_hashCode = 29 * _hashCode + firstname.hashCode();
		}
		
		if (lastname != null) {
			_hashCode = 29 * _hashCode + lastname.hashCode();
		}
		
		if (mobile != null) {
			_hashCode = 29 * _hashCode + mobile.hashCode();
		}
		
		if (econtact != null) {
			_hashCode = 29 * _hashCode + econtact.hashCode();
		}
		
		if (ename != null) {
			_hashCode = 29 * _hashCode + ename.hashCode();
		}
		
		if (status != null) {
			_hashCode = 29 * _hashCode + status.hashCode();
		}
		
		if (lastactive != null) {
			_hashCode = 29 * _hashCode + lastactive.hashCode();
		}
		
		if (lastlat != null) {
			_hashCode = 29 * _hashCode + lastlat.hashCode();
		}
		
		if (lastlong != null) {
			_hashCode = 29 * _hashCode + lastlong.hashCode();
		}
		
		if (zip != null) {
			_hashCode = 29 * _hashCode + zip.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'.
	 *
	 * @return UsersPk
	 */
	public UsersPk createPk()
	{
		return new UsersPk(email);
	}

	/**
	 * Method 'toString'.
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "edu.cmu.sd.dto.Users: " );
		ret.append( "email=" + email );
		ret.append( ", firstname=" + firstname );
		ret.append( ", lastname=" + lastname );
		ret.append( ", mobile=" + mobile );
		ret.append( ", econtact=" + econtact );
		ret.append( ", ename=" + ename );
		ret.append( ", status=" + status );
		ret.append( ", lastactive=" + lastactive );
		ret.append( ", lastlat=" + lastlat );
		ret.append( ", lastlong=" + lastlong );
		ret.append( ", zip=" + zip );
		return ret.toString();
	}

}
