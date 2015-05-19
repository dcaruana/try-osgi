package caruana.osgi.provider.impl;

import caruana.osgi.provider.api.HelloWorldService;

public class HelloWorldServiceImpl implements HelloWorldService
{
    public HelloWorldServiceImpl()
    {
    }
    
    @Override
    public void hello()
    {
        System.out.println("Hello World !");
    }
}
