package Server;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;

/**
 * Created by qwerty on 14-Apr-17.
 */
public class Package implements Serializable {
    private double font;
    //private Paint color;
    private double x;
    private double y;
    private boolean canDraw;
    private boolean begin;
    private double hue;
    private double saturation;
    private double brightness;
    private double opacity;
    private boolean clear;


    public Package(double font, double x, double y, boolean canDraw, boolean begin, double hue, double saturation, double brightness, double opacity,boolean clear) {
        this.font = font;
        this.x = x;
        this.y = y;
        this.canDraw = canDraw;
        this.begin = begin;
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
        this.opacity = opacity;
        this.clear=clear;
    }

    public boolean isClear() {
        return clear;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public double getHue() {
        return hue;
    }

    public void setHue(double hue) {
        this.hue = hue;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public boolean isBegin() {
        return begin;
    }

    public void setBegin(boolean begin) {
        this.begin = begin;
    }

    public double getFont() {
        return font;
    }

    public void setFont(double font) {
        this.font = font;
    }

//    public Paint getColor() {
//        return color;
//    }
//
//    public void setColor(Paint color) {
//        this.color = color;
//    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isCanDraw() {
        return canDraw;
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }
}
