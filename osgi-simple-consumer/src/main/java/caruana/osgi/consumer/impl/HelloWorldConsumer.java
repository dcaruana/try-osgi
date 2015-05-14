package caruana.osgi.consumer.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import caruana.osgi.provider.api.HelloWorldService;

public class HelloWorldConsumer implements ActionListener
{
    private final HelloWorldService service;
    private final Timer timer;

    public HelloWorldConsumer(HelloWorldService service)
    {
        super();
        this.service = service;
        timer = new Timer(1000, this);
    }

    public void startTimer()
    {
        timer.start();
    }

    public void stopTimer()
    {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        service.hello();
    }
}
