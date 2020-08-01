package graficadorformulas;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.*;


/**
 * Clase que representa la pantalla donde se dibujara la formula ingresada
 * @author Marcelo, Javiera, Camilo
 */
public class Board extends JPanel{
    private Figures figures;
    private FigurePanel pane = new FigurePanel();
    
    /*Variable logica que representa el estado del boton para dibujar los puntos de control,
      false= no se dibujan, true= se dibujan.*/
    private boolean paintPtos=false;
    
    /* Lista de puntos de control de la division a dibujar*/
    
    /*Matriz de base de datos para los puntos de cada caracter 
      @autor Javiera*/
    
    private final int[][] chars={
                {0,150,70,150,440,-10,75,-10,432,318,75,318,432},
                {1,40,130,160,60,160,430},
                {2,30,90,155,255,160,20,300,80,20,430,230,430},            
                {3,40,110,70,255,30,415,210,0,264,255,225,489},            
                {4,210,430,210,80,30,340,280,340},            
                {5,240,75,70,75,70,230,60,415,315,147,315,504},
                {6,200,80,80,300,400,150,50,620},
                {7,30,80,270,80,100,450},
                {8,150,250,-100,20,400,20,-100,500,400,500},
                {9,280,100,100,450,50,0,50,400},
                {10,150,160,150,320,75,240,225,240},
                {11,100,250,230,250},
                {12,0,500,300,500},
                {13,80,180,190,350,190,180,80,350},
                {14,150,10,150,490,70,75,70,432},
                {15,150,10,150,490,220,75,220,432},
                {16,155,400,155,50,145,445,145,465,145,445,165,445,165,445,165,465,145,465,165,465},
                {17,80,300,20,330,80,360,20,400,120,300,180,300,120,400,120,300,120,350,180,350,120,400,180,400,
                    220,300,220,400,220,300,280,400,280,300,280,400},
                {18,80,300,20,330,20,360,80,400,120,350,140,320,160,320,180,350,120,350,140,380,160,380,180,
                    350,280,300,220,330,280,360,220,400 },
                {19,20,300,80,300,50,300,50,400,120,400,150,300,150,300,180,400,135,350,165,350,220,300,220,
                 400,220,300,280,400,280,300,280,400},
                {20,20,200,150,100,150,100,280,200},                
                {22,20,480,150,20,150,20,280,480,100,250,200,250},
                {23,50,20,50,480,50,20,250,100,250,200,50,250,50,250,250,300,250,400,50,480},
                {24,250,50,25,100,25,400,250,450},
                {25,50,20,50,480,50,20,300,100,300,400,50,480},
                {26,100,20,250,20,100,20,100,480,100,200,200,200,100,480,250,480},
                {27,100,20,250,20,100,20,100,480,100,200,200,200},
                {28,150,50,60,100,60,150,150,200,150,50,240,100,240,150,150,200}
               };   
    
    /**
     * Constructor del Panel
     * @param w Ancho del panel
     * @param h Alto del panel
     * @autor Camilo
     */
    public Board(int w, int h){
        super(new BorderLayout());
        this.setPreferredSize(new Dimension(w,h));             
        pane = new FigurePanel();
        add(pane);
        this.figures= new Figures();
                           
    }   
    /**
     * Metodo que obtiene una coordenada de un punto especifico, trabaja 
     * sobre la matriz caracter.
     * @param x coordenada x en la matriz.
     * @param y coordenada y en la matriz.
     * @return la coordenada del punto requerido.
     * @autor Camilo
     */
    public int getCar(int x, int y) {
        return chars[x][y];
    } 
    
    
    /**
     * Metodo que cambia el valor logico de pintarPtos.
     * @param o boolean, puede ser true o false.
     * @autor Camilo
     */
    public void orderDrawPoint(boolean o){
        paintPtos=o;
    } 
    /**
     * Clase que permite dibujar los elementos creados en el panel
     * @autor Camilo
     */
    
    class FigurePanel extends JComponent{
        /**
         * Metodo que dibuja los elementos
         * @param g Objeto grafico para acceder a los metodos de paint
         * @autor Camilo
         */
        public void paint(Graphics g){
            Graphics2D g2D = (Graphics2D) g;
            //renderizado
            RenderingHints rh= new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, 
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2D.setRenderingHints(rh);
           
            //DIBUJADO CON FUNCION DE LOS BOTONES
            g2D.setStroke(new BasicStroke(1));                           
            drawLinesDiv(g2D);
            drawLines(g2D);
            drawCurves(g2D);                  
            if (paintPtos){
                drawPointsControl(g2D);
            }          
        }     
    }  
    /**
     * metodo para dibujar curvas
     */
    public void drawCurves(Graphics2D g2D){
        for(int i=0;i<figures.getCurves().length;i++){
            for(int j=0; j<figures.getCurves()[i].length;j++){
                if(figures.getCurves()[i][j]!=null){
                    figures.getCurves()[i][j].draw(g2D);                   
                }
            }
        }    
    }
    /**
     * metodo para dibujar lineas
     * @param g2D 
     */
    public void drawLines(Graphics2D g2D){
        for(int i=0;i<figures.getLines().length;i++){
            for(int j=0;j<figures.getLines()[i].length;j++){
                if(figures.getLines()[i][j]!=null){
                    figures.getLines()[i][j].draw(g2D);
                }
            }
        }   
    }
    public void drawLinesDiv(Graphics2D g2D){
        for(int i=0;i<figures.getLinesDiv().length;i++){           
            for(int j=0;j<figures.getLinesDiv()[i].length;j++){               
                if(figures.getLinesDiv()[i][j]!=null){
                    figures.getLinesDiv()[i][j].draw(g2D);
                }
            }
        }   
    }
    public void drawPointsControl(Graphics2D g2D){
        g2D.setStroke(new BasicStroke(2));
                g2D.setPaint(Color.RED);
                paintPtos=false;
                for(int i=0;i<figures.getPtosControl().size();i++){
                    figures.getPtosControl().get(i).draw(g2D);               
                }
                for(int i=0;i<figures.getPtosControlDiv().size();i++){
                    figures.getPtosControlDiv().get(i).draw(g2D);                 
                }  
    }
    
    public Figures getFigures() {
        return figures;
    }
}
