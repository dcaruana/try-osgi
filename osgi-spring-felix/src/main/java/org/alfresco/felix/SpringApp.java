package org.alfresco.felix;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

@Configuration
@ComponentScan
public class SpringApp
{
    @SuppressWarnings("resource")
    public static void main(String[] args)
    {
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        FelixService svc = (FelixService)context.getBean("felixService");
        
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        FelixService svc = context.getBean(FelixService.class);
        
        ((AbstractApplicationContext)context).registerShutdownHook();
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
}
