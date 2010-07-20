/**
 * Copyright - Accor - All Rights Reserved www.accorhotels.com
 */
package com.accor.ota.receiver.test;

/**
 * @author <a href="mailto:Olivier.LAMY@accor.com">olamy</a>
 *
 * @version $Id: HttpSenderRequest.java 11627 2009-05-04 09:02:03Z olamy $
 */
public class HttpSenderRequest
{
    private String host;
    
    private String path;
    
    private String xmlRequest;
    
    private String proxyHost;
    
    private int proxyPort;

    

    public String getXmlRequest()
    {
        return xmlRequest;
    }

    public void setXmlRequest( String xmlRequest )
    {
        this.xmlRequest = xmlRequest;
    }

    public String getProxyHost()
    {
        return proxyHost;
    }

    public void setProxyHost( String proxyHost )
    {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort()
    {
        return proxyPort;
    }

    public void setProxyPort( int proxyPort )
    {
        this.proxyPort = proxyPort;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost( String host )
    {
        this.host = host;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath( String path )
    {
        this.path = path;
    }
}
