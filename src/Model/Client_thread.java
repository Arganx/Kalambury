package Model;

import Server.Package;
import View.GUI_Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by qwerty on 17-Apr-17.
 */
public class Client_thread extends Thread {//problem z odbiorem

    Socket socket;
    GraphicsContext gc;
    double x;
    double x2;
    double y;
    double y2;
    double width;
    double width2;

    public Client_thread(Socket socket, GraphicsContext gc) {
        this.socket = socket;
        this.gc = gc;
    }

    @Override
    public void run()
    {
        x=0;
        x2=0;
        y=0;
        y2=0;
        width=0;
        width2=0;
        double hue=0,saturation=0,brightness=0,opacity=100;
        double hue2=0,saturation2=0,brightness2=0,opacity2=100;
        while(true)
        {
            if(!GUI_Controller.isCanDraw())
            {
                try {
                    //System.out.println("Klient odbiera obraz");
                    InputStream is = socket.getInputStream();
                    if(is.available()!=0) {
                        //System.out.println("Rozpoczato rysowanie");
                        ObjectInputStream ois = new ObjectInputStream(is);
                        Package pack = (Package) ois.readObject();
                        GUI_Controller.setCanDraw(pack.isCanDraw());
                        if(pack.isClear())
                        {
                            //tu czyszczenie
                            //System.out.println("Czyszcze");
                            double canvasWidth = gc.getCanvas().getWidth();
                            double canvasHeight = gc.getCanvas().getHeight();
                            gc.setStroke(Color.BLACK);
                            gc.setLineWidth(1);
                            gc.clearRect(0,0,canvasWidth,canvasHeight);
                            gc.strokeRect(0, 0, canvasWidth,canvasHeight);
                            gc.setLineWidth(pack.getFont());
                            gc.setStroke(Color.hsb(hue2,saturation2,brightness2,opacity2));
                        }
                        x2=pack.getX();
                        y2=pack.getY();
                        width2=pack.getFont();
                        if(width2!=width)
                        {
                            gc.setLineWidth(width2);
                        }
                        hue2=pack.getHue();
                        saturation2=pack.getSaturation();
                        brightness2=pack.getBrightness();
                        opacity2=pack.getOpacity();
                        if(hue!=hue2 || saturation!=saturation2 || brightness!=brightness2)
                        {
                            //System.out.println("Zmiana koloru");
                            gc.setStroke(Color.hsb(hue2,saturation2,brightness2,opacity2));
                            hue=hue2;
                            brightness=brightness2;
                            saturation=saturation2;
                            opacity = opacity2;
                        }
                        width=width2;
                        if(pack.isBegin()==true && (x2!=x ||y2!=y))
                        {
                            //System.out.println(pack.isBegin());
                            gc.beginPath();
                            gc.lineTo(pack.getX(), pack.getY());
                            gc.stroke();
                        }
                        else if(pack.isBegin()==false && (x2!=x ||y2!=y))
                        {
                            //System.out.println(pack.isBegin());
                            gc.lineTo(pack.getX(), pack.getY());
                            gc.stroke();
                            x=x2;
                            y=y2;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
