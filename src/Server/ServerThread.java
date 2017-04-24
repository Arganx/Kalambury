package Server;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by qwerty on 14-Apr-17.
 */
public class ServerThread extends Thread {

    private Socket socket;
    private boolean canDraw;
    private double x;
    private double y;

    public ServerThread(Socket socket)
    {
        this.socket=socket;
    }

    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public void run() { //dopisac mozliwosc zmiany rysujacego
        //Inicjalizacja
        if(socket==Server.getConnectionArrey().get(0))//Wysylajacy zawsze 0
        {
            try {
                OutputStream os1 = socket.getOutputStream();
                ObjectOutputStream oos1 = new ObjectOutputStream(os1);
                Package pack1 = new Package(1,0,0,true,true,0,0,0,100,false);
                oos1.writeObject(pack1);
                canDraw=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            canDraw=false;
        }
        x=0;
        y=0;
        while(true)
        {
            if(canDraw==true)//Server odbiera
            {
                try {
                    InputStream is = socket.getInputStream();
                    if(is.available()!=0) {
                        ObjectInputStream ois = new ObjectInputStream(is);
                        Package package_from_client = (Package) ois.readObject();
                        Server.setX(package_from_client.getX());
                        Server.setY(package_from_client.getY());
                        Server.setBegin(package_from_client.isBegin());
                        Server.setFont(package_from_client.getFont());
                        Server.setFont(package_from_client.getFont());
                        Server.setHue(package_from_client.getHue());
                        Server.setBrightness(package_from_client.getBrightness());
                        Server.setSaturation(package_from_client.getSaturation());
                        Server.setOpacity(package_from_client.getOpacity());
                        Server.setClear(package_from_client.isClear());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else//Server nadaje
            {
                try {
                    if(x!=Server.getX() || y!=Server.getY()) {
                        //System.out.println("nadaje");
                        OutputStream os2 = socket.getOutputStream();
                        ObjectOutputStream oos2 = new ObjectOutputStream(os2);
                        Package package_to_client = new Package(Server.getFont(), Server.getX(), Server.getY(), false, Server.isBegin(), Server.getHue(), Server.getSaturation(), Server.getBrightness(), Server.getOpacity(), Server.isClear());
                        x=Server.getX();
                        y=Server.getY();
                        //System.out.println(Server.getX());
                        //System.out.println(Server.getBrightness());
                        oos2.writeObject(package_to_client);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
