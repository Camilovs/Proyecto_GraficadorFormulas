package graficadorformulas;


import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


/**
 * Clase que representa un punto de control de un caracter
 * @author Camilo
 */
public class PointControl {
    
    /*Circulo que representa el punto de control*/
    private Ellipse2D.Double circle; 
    /*radio del circulo*/
    private static final double radius = 2;
    private double x,y;
    
    
    /**
     * Constructor de un punto de control
     * @param x coordenada x en el plano
     * @param y coordenada y en el plano
     */
    public PointControl(double x,double y) {        
        circle = new Ellipse2D.Double(x - radius, 
                y - radius, 2.0 * radius, 2.0 * radius);
        this.x= x;
        this.y= y;
    }
    /**
     * Metodo para dibujar el punto de control
     * @param g2D Objeto grafico para acceder a los metodos de paint
     */
    public void draw(Graphics2D g2D) {
        g2D.draw(circle);
    }         
}
