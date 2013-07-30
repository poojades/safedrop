/*
 * Author : Sankha S Pathak
 * License : Carnegie Mellon University (R) 2013
 * SafeDrop Inc 
 */
package edu.cmu.sd.jdbc;

import java.io.*;
import java.sql.*;

// TODO: Auto-generated Javadoc
/**
 * Generic Base class for DAO classes.
 *
 * This is a customizable template within FireStorm/DAO.
 */
public class AbstractDAO
{

    /**
     * Gets the blob column.
     *
     * @param rs the rs
     * @param columnIndex the column index
     * @return the blob column
     * @throws SQLException the sQL exception
     */
    public byte[] getBlobColumn(ResultSet rs, int columnIndex)
            throws SQLException
    {
        try {
            Blob blob = rs.getBlob( columnIndex );
            if (blob == null) {
                return null;
            }

            InputStream is = blob.getBinaryStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            if (is == null) {
                return null;
            }
            else {
                byte buffer[] = new byte[ 64 ];
                int c = is.read( buffer );
                while (c>0) {
                    bos.write( buffer, 0, c );
                    c = is.read( buffer );
                }
                return bos.toByteArray();
            }
        }
        catch (IOException e) {
            throw new SQLException( "Failed to read BLOB column due to IOException: " + e.getMessage() );
        }
    }

    /**
     * Sets the blob column.
     *
     * @param stmt the stmt
     * @param parameterIndex the parameter index
     * @param value the value
     * @throws SQLException the sQL exception
     */
    public void setBlobColumn(PreparedStatement stmt, int parameterIndex, byte[] value)
            throws SQLException
    {
        if (value == null) {
            stmt.setNull( parameterIndex, Types.BLOB );
        }
        else {
            stmt.setBinaryStream( parameterIndex, new ByteArrayInputStream(value), value.length );
        }
    }

    /**
     * Gets the clob column.
     *
     * @param rs the rs
     * @param columnIndex the column index
     * @return the clob column
     * @throws SQLException the sQL exception
     */
    public String getClobColumn(ResultSet rs, int columnIndex)
        throws SQLException
    {
        try {
            Clob clob = rs.getClob( columnIndex );
            if (clob == null) {
                return null;
            }

            StringBuffer ret = new StringBuffer();
            InputStream is = clob.getAsciiStream();

            if (is == null) {
                return null;
            }
            else {
                byte buffer[] = new byte[ 64 ];
                int c = is.read( buffer );
                while (c>0) {
                    ret.append( new String(buffer, 0, c) );
                    c = is.read( buffer );
                }
                return ret.toString();
            }
        }
        catch (IOException e) {
            throw new SQLException( "Failed to read CLOB column due to IOException: " + e.getMessage() );
        }
    }

    /**
     * Sets the clob column.
     *
     * @param stmt the stmt
     * @param parameterIndex the parameter index
     * @param value the value
     * @throws SQLException the sQL exception
     */
    public void setClobColumn(PreparedStatement stmt, int parameterIndex, String value)
        throws SQLException
    {
        if (value == null) {
            stmt.setNull( parameterIndex, Types.CLOB );
        }
        else {
            stmt.setAsciiStream( parameterIndex, new ByteArrayInputStream(value.getBytes()), value.length() );
        }
    }
}
