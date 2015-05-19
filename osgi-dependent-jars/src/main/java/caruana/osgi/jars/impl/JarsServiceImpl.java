package caruana.osgi.jars.impl;

import org.apache.commons.math3.util.MathUtils;

import caruana.osgi.jars.api.JarsService;

public class JarsServiceImpl implements JarsService
{
    public JarsServiceImpl()
    {
        System.out.println("TWO_PI: " + MathUtils.TWO_PI);
    }
    
    @Override
    public void hello()
    {
        System.out.println("Hello World !");
    }
}
