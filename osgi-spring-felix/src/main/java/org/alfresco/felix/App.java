package org.alfresco.felix;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try
        {
            System.out.println("Launching Felix....");
            FelixService svc = new FelixService();
            svc.init();
            
            svc.start();
            System.out.println("Installed bundles:");
            for (Bundle bundle : svc.getInstalledBundles())
            {
                System.out.println(bundle.getBundleId() + ": " + bundle.getSymbolicName());
            }

            System.out.println("Installed services:");
            for (ServiceReference<?> serviceRef : svc.getInstalledServices())
            {
                System.out.println(serviceRef);
            }
            
            svc.stop();
        }
        catch (Exception e)
        {
            System.err.println("Error:" + e);
        }
    }
}
