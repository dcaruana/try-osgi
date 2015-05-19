package caruana.osgi.svc;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.felix.framework.FrameworkFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.launch.Framework;


/**
 * Unit test for simple App.
 */
public class AppLaunch 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppLaunch( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppLaunch.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        try
        {
            System.out.println("Initialising Felix....");
            
            // create config
            Map config = new HashMap();
            config.put("felix.log.level", "2");
            config.put("felix.auto.deploy.action", "install, start");
            
            // create framework
            FrameworkFactory factory = new FrameworkFactory();
            Framework felix = factory.newFramework(config);
            felix.init();
            
            System.out.println("Felix has initialised.");
            
            // register bundles
            String userDir = System.getProperty("user.dir");
            Bundle b1 = felix.getBundleContext().installBundle("file:" + userDir + "/target/osgi-dependent-jars-1.0-SNAPSHOT.jar");
            b1.start();
            
            felix.start();
            System.out.println("Felix has started.");
        }
        catch (Exception e)
        {
            System.err.println("Felix failed to initialise: " + e);
            fail(e.toString());
        }
    }
}
