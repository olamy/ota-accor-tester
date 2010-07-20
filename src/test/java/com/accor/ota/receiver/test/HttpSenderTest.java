/**
 * Copyright - Accor - All Rights Reserved www.accorhotels.com
 */
package com.accor.ota.receiver.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author olamy
 */
public class HttpSenderTest
    extends TestCase
{
    private Logger log = LoggerFactory.getLogger( getClass() );

    private static String basedir;

    public static String getBasedir()
    {
        if ( basedir != null )
        {
            return basedir;
        }

        basedir = System.getProperty( "basedir" );
        if ( basedir == null )
        {
            basedir = new File( "" ).getAbsolutePath();
        }

        return basedir;
    }

    public static File getTestFile( String path )
    {
        return new File( getBasedir(), path );
    }

    public String getTransactionIdentifier()
    {
        return new SimpleDateFormat( "SS':'mm':'ss':'dd':'MM':'yyyy" ).format( new Date() );

    }

    /**
     * 
     */
    public void testSend()
        throws Exception
    {
        try
        {
            InputStream is = new FileInputStream( getTestFile( "/src/test/resources/testfile.xml" ) );
            String rq = IOUtils.toString( is, "UTF-8" );
            HttpSenderRequest httpSenderRequest = new HttpSenderRequest();
            httpSenderRequest.setHost( System.getProperty( "otaEndPoint" ) );
            httpSenderRequest.setPath( System.getProperty( "otaPath", "/OTAReceiver/soap-messaging" ) );
            httpSenderRequest.setXmlRequest( rq );
            httpSenderRequest.setProxyHost( System.getProperty( "httpProxyHost" ) );
            httpSenderRequest.setProxyPort( NumberUtils.toInt( System.getProperty( "httpProxyPort" ) ) );
            HttpSender sender = new HttpSender();
            String response = sender.sendHttpRequest( httpSenderRequest );
            log.info( "response " + response );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage(), e );
            throw e;

        }
    }
}
