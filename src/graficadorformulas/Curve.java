/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficadorformulas;

import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;

/**
 *
 * @author Javiera
 */
public class Curve {
    private CubicCurve2D.Double curve;
    /**
     * Javiera
     * @param x1
     * @param y1
     * @param controlx1
     * @param controly1
     * @param controlx2
     * @param controly2
     * @param x2
     * @param y2 
     */
    public Curve(double x1,double y1,double controlx1, double controly1, double controlx2,double controly2,double x2,double y2){
        curve= new CubicCurve2D.Double(x1, y1, controlx1, controly1, controlx2, controly2, x2, y2);
    }
    /**
     * Javiera
     * @return 
     */
   public CubicCurve2D.Double getCurv() {
        return curve;
    }
    /**
     * Javiera
     * @param g2d 
     */
    public void draw(Graphics2D g2d){
        g2d.draw(curve);        
    }    
}
