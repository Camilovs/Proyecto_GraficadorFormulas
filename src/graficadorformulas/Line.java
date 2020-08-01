/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficadorformulas;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 *
 * @author Javiera
 */
public class Line {
    private Line2D.Double line;
    /**
     * Javiera
     * @param x1
     * @param y1
     * @param x2
     * @param y2 
     */
    public Line (double x1,double y1,double x2,double y2){
        line= new Line2D.Double(x1,y1,x2,y2);
    }   

    public Line2D.Double getLine() {
        return line;
    }
    
    /**
     * Javiera
     * @param g2d 
     */
    public void draw(Graphics2D g2d){
        g2d.draw(line);
    }
    public double getX1(){
        return line.x1;
    }
    public double getX2(){
        return line.x2;
    }
    public double getY1(){
        return line.y1;
    }
    public double getY2(){
        return line.y2;
    }
}
