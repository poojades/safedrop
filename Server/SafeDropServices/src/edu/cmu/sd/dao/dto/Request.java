/*
 * 
 * 
 *
 * 
 * 
 */

package edu.cmu.sd.dao.dto;

import edu.cmu.sd.dao.dao.*;
import edu.cmu.sd.dao.factory.*;
import edu.cmu.sd.dao.exceptions.*;
import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class Request implements Serializable
{
	/** 
	 * This attribute maps to the column id in the request table.
	 */
	protected int id;

	/** 
	 * This attribute maps to the column by in the request table.
	 */
	protected String by;

	/** 
	 * This attribute maps to the column created in the request table.
	 */
	protected Date created;

	/** 
	 * This attribute maps to the column status in the request table.
	 */
	protected String status;

	/**
	 * Method 'Request'
	 * 
	 */
	public Request()
	{
	}

	/**
	 * Method 'getId'
	 * 
	 * @return int
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Method 'getBy'
	 * 
	 * @return String
	 */
	public String getBy()
	{
		return by;
	}

	/**
	 * Method 'setBy'
	 * 
	 * @param by
	 */
	public void setBy(String by)
	{
		this.by = by;
	}

	/**
	 * Method 'getCreated'
	 * 
	 * @return Date
	 */
	public Date getCreated()
	{
		return created;
	}

	/**
	 * Method 'setCreated'
	 * 
	 * @param created
	 */
	public void setCreated(Date created)
	{
		this.created = created;
	}

	/**
	 * Method 'getStatus'
	 * 
	 * @return String
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Method 'setStatus'
	 * 
	 * @param status
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
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
		
		if (!(_other instanceof Request)) {
			return false;
		}
		
		final Request _cast = (Request) _other;
		if (id != _cast.id) {
			return false;
		}
		
		if (by == null ? _cast.by != by : !by.equals( _cast.by )) {
			return false;
		}
		
		if (created == null ? _cast.created != created : !created.equals( _cast.created )) {
			return false;
		}
		
		if (status == null ? _cast.status != status : !status.equals( _cast.status )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		if (by != null) {
			_hashCode = 29 * _hashCode + by.hashCode();
		}
		
		if (created != null) {
			_hashCode = 29 * _hashCode + created.hashCode();
		}
		
		if (status != null) {
			_hashCode = 29 * _hashCode + status.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return RequestPk
	 */
	public RequestPk createPk()
	{
		return new RequestPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "edu.cmu.sd.dao.dto.Request: " );
		ret.append( "id=" + id );
		ret.append( ", by=" + by );
		ret.append( ", created=" + created );
		ret.append( ", status=" + status );
		return ret.toString();
	}

}
