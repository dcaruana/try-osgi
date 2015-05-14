package caruana.osgi.provider.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import caruana.osgi.provider.api.HelloWorldService;

public class ProviderActivator implements BundleActivator {
    
    private ServiceRegistration registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception
    {
        HelloWorldService service = new HelloWorldServiceImpl();
        registration = bundleContext.registerService(HelloWorldService.class.getName(), service, null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception
    {
        registration.unregister();
    }

}
