package caruana.osgi.jars.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import caruana.osgi.jars.api.JarsService;

public class JarsActivator implements BundleActivator {
    
    private ServiceRegistration registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception
    {
        JarsService service = new JarsServiceImpl();
        registration = bundleContext.registerService(JarsService.class.getName(), service, null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception
    {
        registration.unregister();
    }

}
