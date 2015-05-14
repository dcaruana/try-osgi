package caruana.osgi.provider.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import caruana.osgi.provider.api.HelloWorldService;

public class HelloWorldServiceImpl implements HelloWorldService
{
    public HelloWorldServiceImpl()
        throws ClientProtocolException, IOException
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try
        {
            HttpHost target = new HttpHost("api.randomuser.me", 80, "http");
            HttpGet getRequest = new HttpGet("/");

            HttpResponse httpResponse = httpclient.execute(target, getRequest);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null)
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        finally
        {
            httpclient.close();
        }
    }

    
    
    @Override
    public void hello()
    {
        System.out.println("Hello World !");
    }
}
