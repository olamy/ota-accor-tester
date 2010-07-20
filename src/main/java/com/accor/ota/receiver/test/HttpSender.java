/**
 * Copyright - Accor - All Rights Reserved www.accorhotels.com
 */
package com.accor.ota.receiver.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
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

    private ThreadSafeClientConnManager clientConnectionManager;

    public HttpSender( String sslProtocol )
        throws NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException, KeyStoreException
    {
        SchemeRegistry schemeRegistry = new SchemeRegistry();

        // https scheme
        // TLS
        //schemeRegistry.register( new Scheme( "https", 443, SSLSocketFactory.getSocketFactory() ) );

        schemeRegistry.register( new Scheme( "https", 443, new SSLSocketFactory( sslProtocol, null, null, null, null,
                                                                                 null, null ) ) );

        // http scheme
        schemeRegistry.register( new Scheme( "http", 80, PlainSocketFactory.getSocketFactory() ) );

        clientConnectionManager = new ThreadSafeClientConnManager( schemeRegistry );
    }

    public String sendHttpRequest( HttpSenderRequest request )
        throws IOException
    {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion( params, HttpVersion.HTTP_1_1 );
        DefaultHttpClient httpClient = new DefaultHttpClient( clientConnectionManager, params );
        String host = request.getHost();
        log.info( "use HttpSenderRequest {} ", request );

        String path = request.getPath();

        HttpPost httpPost = new HttpPost( host + "/" + path );

        httpPost.addHeader( "Content-Type", "text/xml;charset=UTF-8" );

        if ( request.getProxyHost() != null )
        {

            HttpHost proxy = new HttpHost( request.getProxyHost(), request.getProxyPort(), "http" );
            httpClient.getParams().setParameter( ConnRoutePNames.DEFAULT_PROXY, proxy );
        }

        HttpEntity httpEntity = new ByteArrayEntity( request.getXmlRequest().getBytes() );

        httpPost.setEntity( httpEntity );

        HttpConnectionParams.setConnectionTimeout( httpPost.getParams(), 10000 );
        HttpConnectionParams.setSoTimeout( httpPost.getParams(), 10000 );

        HttpResponse res = httpClient.execute( httpPost );

        return IOUtil.toString( res.getEntity().getContent() );

    }
}
