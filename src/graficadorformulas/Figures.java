/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficadorformulas;

import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *
 * @author Javiera
 */
public class Figures {
    private Curve[][] curves;
    private Line[][] lines;
    private Line[][] linesDiv;
    private Line[][] linesDivSave;
    private ArrayList<PointControl> ptosControl;
    private ArrayList<PointControl> ptosControlDiv;  
    /**
     * Javiera
     */
    public Figures (){
        curves= new Curve[140][10];
        lines= new Line[140][10];
        linesDiv = new Line[50][50];
        linesDivSave = new Line[50][50];
        ptosControl= new ArrayList<>();
        ptosControlDiv= new ArrayList<>();
    }
    /**
     * autor Javiera
     * @param x1
     * @param y1
     * @param controlx1
     * @param controly1
     * @param controlx2
     * @param controly2
     * @param x2
     * @param y2
     * @param x
     * @param y 
     * Metodo que agrega una curva a la matriz curvas
     */
    public void addCurve(double x1,double y1,double controlx1, double controly1, double controlx2,double controly2,double x2,double y2,int x,int y){
        Curve c= new Curve(x1, y1, controlx1, controly1, controlx2, controly2, x2,y2);       
        curves[x][y]=c;
    }
    /**
     * autor Javiera
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x
     * @param y 
     * Metodo que agrega una linea a la matriz lineas
     */
    public void addLine(double x1, double y1, double x2, double y2,int x, int y){
        Line l= new Line(x1,y1,x2,y2);       
        lines[x][y]=l;
    }
    /**
     * autor Javiera
     * @param x
     * @param y 
     * Metodo que agrega un punto de control a la matriz puntos de cotrol
     */
    public void addPointControl(double x,double y){
        PointControl p= new PointControl(x,y);
        ptosControl.add(p);    
    }
    /**
     * Camilo
     * @param x
     * @param y 
     * Metodo que agrega un punto de control de la division
     */
     public void addPointControlDiv(double x, double y){
        PointControl point= new PointControl(x,y);
        ptosControlDiv.add(point);
    }
     /**
      * Camilo
      * @param x1
      * @param y1
      * @param x2
      * @param y2
      * @param x
      * @param y 
      * metodo que agrega una linea de division a la matriz linediv
      */
    public void addLineDiv(double x1, double y1, double x2,double y2,int x,int y){      
        Line ld= new Line(x1,y1,x2,y2);
        System.out.println("Lineadiv en pos ("+x+", "+y+")");
        linesDiv[x][y]=ld;
        
        
    }
    /**
     * metodo que calcula el largo de una division
     * @param x
     * @return 
     */
    public void saveLinesDiv(){
        this.linesDivSave = linesDiv;
    }
    public int lenghtLineDiv(int x){
        int lenght=0;
        for (int i = 0; i < linesDiv[x].length; i++) {
            if (linesDiv[x][i]!=null)
                lenght++;
        }
        return lenght;
    }  
    public int lenghtLineDiv(){    
        int aux=0;
        for(int i=0; i<linesDiv.length; i++){
            for (int j = 0; j < linesDiv[i].length; j++) {
                
                if (linesDiv[i][j]!=null)
                    aux++;
            }      
        }
        return aux;
    }
    /**
     * autor Javiera
     * @param curves 
     * metodo que modifica el valor de curvas
     */
    public void setCurves(Curve[][] curves) {
        this.curves = curves;
    }
    /**
     * autor Javiera
     * @param lines 
     * metodo que mofidica el valor de lineas
     */
    public void setLines(Line[][] lines) {
        this.lines = lines;
    }
    /**
     * autor Javiera
     * metodo que elimina todos los puntos de control
     */
    public void setPtosControl() {
        this.ptosControl.clear();
        this.ptosControlDiv.clear();
    }
    /**
     * autor Javiera
     * @return 
     * metodo que retorna las curvas
     */
    public Curve[][] getCurves() {
        return curves;
    }
    /**
     * autor Javiera
     * @return 
     * metodo que retorna las lineas
     */
    public Line[][] getLines() {
        return lines;
    }
    /**
     * metodo que retorna las lineas de la division
     * @return 
     */
    public Line[][] getLinesDiv() {
        return linesDiv;
    }
   /**
    * metodo que cambia el valor de las lineas divisoras
    * @param linesDiv 
    */
    public void setLinesDiv(Line[][] linesDiv) {
        this.linesDiv = linesDiv;
    }
     
    /**
     * metodo que retorna los puntos de control
     * @return 
     */
    public ArrayList<PointControl> getPtosControl() {
        return ptosControl;
    }
    /**
     * metodo que retorna los puntos de control de la division
     * @return 
     */
    public ArrayList<PointControl> getPtosControlDiv() {
        return ptosControlDiv;
    }
    /**
     * Metodo que elimina el ultimo punto de control de la division para actualizarlo
     */
    public void delPtoControlDiv(){
        if (getPtosControlDiv().size()>0){
            getPtosControlDiv().remove(getPtosControlDiv().size()-1);          
        }
    } 

    public Line[][] getLinesDivSave() {
        return linesDivSave;
    }

    public void setLinesDivSave(Line[][] linesDivSave) {
        this.linesDivSave = linesDivSave;
    }
    
    
    
    
}
