/**
 * Copyright - Accor - All Rights Reserved www.accorhotels.com
 */
package com.accor.ota.receiver.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.plexus.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:Olivier.LAMY@accor.com">olamy</a>
 * @version $Id: HttpSender.java 11628 2009-05-04 09:31:01Z olamy $
 */
public class HttpSender
{
    private Logger log = LoggerFactory.getLogger( getClass() );

    public HttpSender()
    {
        // no-op
    }

    public String sendHttpRequest( HttpSenderRequest request )
        throws HttpException, IOException
    {
        log.info( " System file.encoding " + System.getProperty( "file.encoding" ) );

        HttpClient cli = new HttpClient();
        String host = request.getHost();
        log.info( "use host " + host );
        cli.getHostConfiguration().setHost( host );
        PostMethod pm = new PostMethod( );
        String path = request.getPath(); 
        log.info( "use path " + path ); 
        pm.setPath( path );
        pm.addRequestHeader( "Content-Type", "text/xml;charset=UTF-8" );

        HttpMethodParams params = new HttpMethodParams();
        params.setSoTimeout( 10000 );
        
        if ( request.getProxyHost() != null )
        {
            cli.getHostConfiguration().setProxy( request.getProxyHost(), request.getProxyPort() );
        }
        pm.setRequestEntity( new StringRequestEntity( request.getXmlRequest(), "text/xml", "UTF-8" ) );

        cli.executeMethod( pm );

        return IOUtil.toString( pm.getResponseBodyAsStream() );

    }
}
