package View;

import Model.Client_thread;
import Server.Package;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


/**
 * Created by qwerty on 14-Apr-17.
 */
public class GUI_Controller {
    @FXML
    Canvas canvas;
    @FXML
    ColorPicker colorpicker;
    @FXML
    Button czysc;
    @FXML
    Label fontsize;
    @FXML
    Slider font;

    private Socket socket;
    private static boolean canDraw;

    public static boolean isCanDraw() {
        return canDraw;
    }

    public static void setCanDraw(boolean canDraw) {
        GUI_Controller.canDraw = canDraw;
    }

    private GraphicsContext gc;
    @FXML
    public void initialize() throws IOException {
        socket=new Socket("localhost",4500);
        canDraw =false;
        gc=canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Client_thread watek = new Client_thread(socket,gc);
        watek.start();
        colorpicker.setValue(Color.BLACK);
        colorpicker.setOnAction(e->{
            gc.setStroke(colorpicker.getValue());
        });
        initDraw(gc);
        font.valueProperty().addListener(e->{
            double value = font.getValue();
            String std = "Rozmiar " + value;
            fontsize.setText(std);
            gc.setLineWidth(value);
        });
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(canDraw ==true) {
                    gc.beginPath();
                    gc.lineTo(event.getX(), event.getY());
                    try {
                        OutputStream os = socket.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        Package pack_pressed = new Package(gc.getLineWidth(),event.getX(),event.getY(),true,true,colorpicker.getValue().getHue(),colorpicker.getValue().getSaturation(),colorpicker.getValue().getBrightness(),colorpicker.getValue().getOpacity(),false);
                        colorpicker.getValue().getRed();
                        oos.writeObject(pack_pressed);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    gc.stroke();
                }
            }
        });

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(canDraw==true) {
                    gc.lineTo(event.getX(), event.getY());
                    try {
                        OutputStream os2 = socket.getOutputStream();
                        ObjectOutputStream oos2 = new ObjectOutputStream(os2);
                        Package pack_pressed2 = new Package(gc.getLineWidth(),event.getX(),event.getY(),true,false,colorpicker.getValue().getHue(),colorpicker.getValue().getSaturation(),colorpicker.getValue().getBrightness(),colorpicker.getValue().getOpacity(),false);
                        //System.out.println(colorpicker.getValue().getRed());
                        oos2.writeObject(pack_pressed2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    gc.stroke();
                }
            }
        });
    }

    private void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
        gc.setLineWidth(1);
        gc.strokeRect(0, 0, canvasWidth,canvasHeight);
        gc.setLineWidth(font.getValue());
    }

    @FXML
    private void clearhandle() throws IOException {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
        gc.setStroke(Color.BLACK);
        gc.clearRect(0,0,canvasWidth,canvasHeight);
        OutputStream os2 = socket.getOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(os2);
        Package pack_pressed2 = new Package(1,0,0,true,true,colorpicker.getValue().getHue(),colorpicker.getValue().getSaturation(),colorpicker.getValue().getBrightness(),colorpicker.getValue().getOpacity(),true);
        oos2.writeObject(pack_pressed2);
        initDraw(gc);
        gc.setStroke(colorpicker.getValue());
    }
}
