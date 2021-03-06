package caruana.osgi.svc.two;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator
{
    private BundleContext m_context = null;

    public void start(BundleContext context)
    {
        m_context = context;
        Hashtable<String, String> dict = new Hashtable<String, String>();
        dict.put("caruana.osgi.svc.name", "ServiceTwo");
        Two service = new Two();
        service.startService();
        m_context.registerService(Two.class.getName(), service, dict);
    }

    public void stop(BundleContext context) throws Exception
    {
        ServiceReference[] refs = context.getServiceReferences(Two.class.getName(), "(caruana.osgi.svc.name=ServiceTwo)");
        if (refs != null)
        {
            ((Two) context.getService(refs[0])).stopServiceAsync();
        }
    }
}