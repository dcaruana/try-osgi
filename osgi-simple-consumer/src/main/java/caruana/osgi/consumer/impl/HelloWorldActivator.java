package caruana.osgi.consumer.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import caruana.osgi.provider.api.HelloWorldService;

public class HelloWorldActivator implements BundleActivator
{
    private HelloWorldConsumer consumer;

    @Override
    public void start(BundleContext bundleContext) throws Exception
    {
        ServiceReference reference = bundleContext.getServiceReference(HelloWorldService.class.getName());
        consumer = new HelloWorldConsumer((HelloWorldService) bundleContext.getService(reference));
        consumer.startTimer();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception
    {
        consumer.stopTimer();
    }
}
