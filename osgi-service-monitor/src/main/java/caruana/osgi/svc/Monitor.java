package caruana.osgi.svc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Monitor extends JFrame implements Runnable
{
    private static final long serialVersionUID = -3645246230619733443L;

    private static class ServiceStatus
    {
        private String name;
        private String host;
        private int port;
        
        public ServiceStatus(String name, String host, int port)
        {
            this.name = name;
            this.host = host;
            this.port = port;
        }
        
        public String getDisplayName()
        {
            return name;
        }
        
        public String getServiceHost()
        {
            return host;
        }
        
        public int getServicePort()
        {
            return port;
        }
    }
    
    public Monitor()
    {
    }

    public static Monitor mon = null;
    public static ServiceStatus services[] = new ServiceStatus[2];
    public static JButton[] buttons = null;

    public static void main(String[] args)
    {
        services[0] = new ServiceStatus("service 1", "localhost", 9000);
        services[1] = new ServiceStatus("service 2", "localhost", 9010);

        mon = new Monitor();
        mon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Box box = Box.createVerticalBox();
        buttons = new JButton[services.length];

        for (int i = 0; i < services.length; i++)
        {
            buttons[i] = new JButton(services[i].getDisplayName());
            box.add(buttons[i]);
        }

        mon.add(box, BorderLayout.CENTER);
        mon.setSize(500, 300);
        mon.setVisible(true);
        new Thread(mon).start();
    }

    public void run()
    {
        Socket socket = null;
        while (true)
        {
            try
            {
                Thread.sleep(3000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            for (int curservice = 0; curservice < services.length; curservice++)
            {
                try
                {
                    socket = new Socket(services[curservice].getServiceHost(), services[curservice].getServicePort());
                    socket.setSoTimeout(1000);
                    // try connecting for 1 second
                    BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String resp = serverIn.readLine();
                    if (resp.equals("OK"))
                    {
                        final int curIndex = curservice;
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            public void run()
                            {
                                buttons[curIndex].setBackground(Color.GREEN);
                                buttons[curIndex].setText(services[curIndex].getDisplayName() + " RUNNING");
                            }
                        });
                    }
                } catch (Exception ex)
                {
                    final int curIndex = curservice;
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            buttons[curIndex].setBackground(Color.RED);
                            buttons[curIndex].setText(services[curIndex].getDisplayName() + " STOPPED");
                        }
                    });
                } finally
                {
                    try
                    {
                        if (socket != null)
                        {
                            socket.close();
                            socket = null;
                        }
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}