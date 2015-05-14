package caruana.osgi.svc.one;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class One implements Runnable
{
    public static int servicePort = 9000;
    public boolean stopnow = false;

    public One()
    {
    }

    public synchronized void startService()
    {
        stopnow = false;
        new Thread(this).start();
    }

    public synchronized void stopServiceAsync()
    {
        // assume stop service successful
        stopnow = true;
    }

    public ServerSocket serverSocket = null;
    public Socket clientSocket = null;

    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(servicePort);
            while (!stopnow)
            {
                clientSocket = serverSocket.accept();
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println("OK");
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                if (clientSocket != null)
                {
                    clientSocket.close();
                    clientSocket = null;
                }
                if (serverSocket != null)
                {
                    serverSocket.close();
                    serverSocket = null;
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}