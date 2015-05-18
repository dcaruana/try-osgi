package org.alfresco.felix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.felix.framework.FrameworkFactory;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class FelixService implements InitializingBean
{
    private HostActivator activator = null;
    private Framework felix = null;
    
    public void afterPropertiesSet()
    {
        init();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void init()
    {
        try
        {
            System.out.println("Initialising Felix....");
            // create config
            Map config = new HashMap();
            this.activator = new HostActivator();
            List list = new ArrayList();
            list.add(activator);
            
            LoggingActivator logging = new LoggingActivator();
            list.add(logging);
            
            config.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, list);
            //config.put("felix.log.level", "2");
            
            // create framework
            FrameworkFactory factory = new FrameworkFactory();
            this.felix = factory.newFramework(config);
            this.felix.init();
            
            // auto deploy bundles
            AutoProcessor.process(null, this.felix.getBundleContext());
            
            // register bundles
            String userDir = System.getProperty("user.dir");
            this.felix.getBundleContext().installBundle("file:" + userDir + "/bundles/org.apache.felix.bundlerepository-2.0.4.jar");
            
            // register services
            Hashtable<String, String> props = new Hashtable<String, String>();
            props.put("Language", "English");
            this.felix.getBundleContext().registerService(
                DictionaryService.class.getName(), new DictionaryServiceImpl(), props);
            
            System.out.println("Felix has initialised.");
        }
        catch (Exception e)
        {
            System.err.println("Felix failed to initialise: " + e);
        }
    }
    
    public void start()
    {
        try
        {
            System.out.println("Starting Felix....");
            this.felix.start();
            System.out.println("Felix has started.");
        }
        catch (Exception e)
        {
            System.err.println("Felix failed to start:" + e);
        }
    }
    
    public void stop()
    {
        try
        {
            System.out.println("Stopping Felix....");
            this.felix.stop();
//            felix.waitForStop(0);
            System.out.println("Felix has stopped.");
            
            System.exit(0);
        }
        catch (Exception e)
        {
            System.err.println("Felix failed to stop:" + e);
        }
    }
    
    public Bundle[] getInstalledBundles()
    {
        // Use the system bundle activator to gain external
        // access to the set of installed bundles.
        return activator.getBundles();
    }
    
    public ServiceReference<?>[] getInstalledServices()
    {
        return activator.getServices();
    }
    
    public class HostActivator implements BundleActivator
    {
        private BundleContext context = null;

        public void start(BundleContext context)
        {
            this.context = context;
        }

        public void stop(BundleContext context)
        {
            this.context = null;
        }

        public Bundle[] getBundles()
        {
            if (this.context != null)
            {
                return this.context.getBundles();
            }
            return null;
        }
        
        public ServiceReference<?>[] getServices()
        {
            if (this.context != null)
            {
                try
                {
                    return this.context.getAllServiceReferences(null, null);
                }
                catch (Exception e)
                {
                    return null;
                }
            }
            return null;
        }
    }
}
