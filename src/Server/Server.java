package Server;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by qwerty on 14-Apr-17.
 */



public class Server {//problem z przekazywaniem dalej

    private static ArrayList<Socket> connectionArrey = new ArrayList<Socket>();
    private static ArrayList<String> currentusersNames = new ArrayList<String>();
    private static double x;
    private static double y;
    private static Paint color;
    private static double font;
    private static boolean begin;
    private static double hue;
    private static double saturation;
    private static double brightness;
    private static double opacity;
    private static boolean clear;

    public static boolean isClear() {
        return clear;
    }

    public static void setClear(boolean clear) {
        Server.clear = clear;
    }

    public static double getHue() {
        return hue;
    }

    public static void setHue(double hue) {
        Server.hue = hue;
    }

    public static double getSaturation() {
        return saturation;
    }

    public static void setSaturation(double saturation) {
        Server.saturation = saturation;
    }

    public static double getBrightness() {
        return brightness;
    }

    public static void setBrightness(double brightness) {
        Server.brightness = brightness;
    }

    public static double getOpacity() {
        return opacity;
    }

    public static void setOpacity(double opacity) {
        Server.opacity = opacity;
    }

    public static boolean isBegin() {
        return begin;
    }

    public static void setBegin(boolean begin) {
        Server.begin = begin;
    }

    public static Paint getColor() {
        return color;
    }

    public static void setColor(Paint color) {
        Server.color = color;
    }

    public static double getFont() {
        return font;
    }

    public static void setFont(double font) {
        Server.font = font;
    }

    public static double getX() {
        return x;
    }

    public static void setX(double x) {
        Server.x = x;
    }

    public static double getY() {
        return y;
    }

    public static void setY(double y) {
        Server.y = y;
    }

    public static ArrayList<Socket> getConnectionArrey() {
        return connectionArrey;
    }

    public ArrayList<Socket> getConnections()
    {
        return connectionArrey;
    }

    public static void main(String[] args) throws Exception {
        clear=false;
        x=1;
        y=1;
        font=1;
        ServerSocket server = new ServerSocket(4500);
        System.out.println("Awaiting clients...");
        while(true)
        {
            Socket s = server.accept();
            connectionArrey.add(s);
            System.out.println("Polaczono z "+ s.getLocalAddress().getHostName());
            ServerThread thread = new ServerThread(s);
            thread.start();
        }
    }
}
