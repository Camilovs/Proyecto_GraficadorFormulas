package graficadorformulas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;

/**
 * Clase que representa la ventana.
 * @author Marcelo, Javiera, Camilo
 */
public class Window extends JFrame {
    
    /*panel1: Botones, panel2: Formula*/
    private Board board;
    private JTextPane TextBox;
    private String listTextbox="";    
    /*estado presionado-no presionado del boton pto*/
    private boolean buttonPointPress;
    private boolean buttonDivPress;        
    private boolean based2;
    private boolean based16;
    private boolean parentheses=false;
    private boolean firstPar=true;
    private boolean basic=true;
    private boolean scientific=false;
    private boolean resizing=false;
    private ArrayList<JButton> buttons;      
    private ArrayList<Integer> expression;   
    private ArrayList<Integer> expression2;
    private ArrayList<Integer> denominator;
    //imagenes de los botones
    private final ArrayList<ImageIcon> images;
    //Variables para la separacion entre caracteres horizontal y vertical
    private final int desHor = 300,desVer=500;
    //Variable que redimensiona los caracteres.
    private double size = 10;
    //Multiplicadores de los desfases para
    private int xk=1, yk=0,limit=0;
    private ArrayList<Integer> valueOfXk;
    private ArrayList<Integer> allxdec;
    private ArrayList<Integer> allydec;
    private ArrayList<Integer> allxbin;
    private ArrayList<Integer> allybin;
    private ArrayList<Integer> allxhexa;
    private ArrayList<Integer> allyhexa;    
    private Integer[][] allxdiv;
    private Integer[][] allydiv;
    private int count=0;
    private int posY_Matrixdiv =0;
   
    //pto control Division
    private int lastPtoDiv;
    
    /*entero para la cantidad de pares de parentesis*/
    private int numberParen=0;
    private int startPar=0, endPar=0;
    private int xkLast=0;
    private Double resultado = null; 
    
    /**
     * Constructor de la Ventana
     * @autor Camilo
     */
    public Window(){
        this.valueOfXk= new ArrayList<>();
        this.allxdec= new ArrayList<>();
        this.allydec= new ArrayList<>();      
        this.allxbin= new ArrayList<>();
        this.allybin= new ArrayList<>();
        this.allxhexa= new ArrayList<>();
        this.allyhexa= new ArrayList<>();
        this.denominator= new ArrayList<>();        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Graficador de Formulas");
        this.setResizable(false);
        this.setLayout(new BorderLayout());      
        this.buttons= new ArrayList<>();           
        this.images= new ArrayList<>();                
        this.expression= new ArrayList<>();
        this.expression2= new ArrayList<>();
        this.allxdiv = new Integer[100][120];
        this.allydiv = new Integer[100][120];       
        this.based2=false;  
        this.based16=false;
        imagesList();        
        this.buildWindow(); 
        
    }
    /**
     * Metodo que carga las imagenes de los botones
     * @autor Javiera
     */
    public void imagesList(){    
        images.add(new ImageIcon("cero.png"));
        images.add(new ImageIcon("uno.png"));
        images.add(new ImageIcon("dos.png"));
        images.add(new ImageIcon("tres.png"));
        images.add(new ImageIcon("cuatro.png"));
        images.add(new ImageIcon("cinco.png"));
        images.add(new ImageIcon("seis.png"));
        images.add(new ImageIcon("siete.png"));
        images.add(new ImageIcon("ocho.png"));
        images.add(new ImageIcon("nueve.png"));
        images.add(new ImageIcon("suma.png"));
        images.add(new ImageIcon("resta.png"));
        images.add(new ImageIcon("division.png"));
        images.add(new ImageIcon("multiplicacion.png"));
        images.add(new ImageIcon("abre parentesis.png"));
        images.add(new ImageIcon("cierre parentesis.png"));
        images.add(new ImageIcon("punto.png"));
        images.add(new ImageIcon("out.png"));
        images.add(new ImageIcon("A+.png"));
        images.add(new ImageIcon("igual.png"));
        images.add(new ImageIcon("sen.png"));
        images.add(new ImageIcon("cos.png"));
        images.add(new ImageIcon("tan.png"));
        images.add(new ImageIcon("pow.png"));       
        images.add(new ImageIcon("negativo.png"));
        images.add(new ImageIcon("factorial.png"));      
        images.add(new ImageIcon("a.png"));
        images.add(new ImageIcon("b.png"));
        images.add(new ImageIcon("c.png"));
        images.add(new ImageIcon("d.png"));
        images.add(new ImageIcon("e.png"));
        images.add(new ImageIcon("f.png"));
        images.add(new ImageIcon("negro.png"));
    }
    /**
     * Metodo que construye la ventana con todos los elementos necesarios
     * @autor Camilo
     */
    public void buildWindow(){
        JPanel PanelButtons = new JPanel();
        TextBox = new JTextPane();
        JPanel PanelCont = new JPanel();
        
        JTextArea text = new JTextArea();       
        Font f = new Font("Arial", Font.PLAIN, 23);
        TextBox.setFont(f);
        PanelButtons.setPreferredSize(new Dimension(800,200));
        PanelCont.setPreferredSize(new Dimension(1200,750));
        board = new Board (850,465);
        TextBox.setPreferredSize(new Dimension(100,50));            
               
        PanelButtons.setLayout(null);
        PanelCont.setLayout(new BorderLayout());
        buttonPointPress=false;
        buttonDivPress=false;             
        /**
         * Ciclo que crea y agrega los botones al panel de botones.
         * @autor Javiera
         */      
        int x=0,y=0;
        for(int i=0; i<32;i++){                           
            if(i%2==0 && i>=2){
                y+=75;
                x=0;
            }                     
            JButton boton= new JButton(" ");          
            boton.setBounds(y,x,75,75); 
            boton.setIcon(images.get(i));
            x+=75;
            PanelButtons.add(boton);
            buttons.add(boton);
            if(i>=20){
                boton.setVisible(false);
            }
        }   
        JButton boton1= new JButton("");
        PanelButtons.add(boton1);
        boton1.setBounds(0, 150, 35, 35);
        boton1.setIcon(images.get(32));
        boton1.setVisible(true);
        buttons.add(boton1);
        
        listener();
        JMenuBar j= new JMenuBar();
        this.setJMenuBar(j);
        JMenu opciones= new JMenu("Opciones");
        j.add(opciones);
               
        JMenuItem calculadoraNormal= new JMenuItem("Calculadora Normal");
        opciones.add(calculadoraNormal);
        JMenuItem calculadoraCientifica= new JMenuItem("Calculadora Cientifica");
        opciones.add(calculadoraCientifica);
        /**
         * Autor Javiera
         */
        calculadoraCientifica.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                basic=false;
                //si se apreta la opcion de calculadora cientifica se hacen visibles los botones de esa funcionalidad
                    for(int i=20;i<26;i++){
                        if(scientific==false){
                            scientific=true;
                            reset();
                        }
                        buttons.get(i).setVisible(true);
                        board.repaint();
                    }
                    
                }
        });        
        /**
         * Autor Javiera
         */
        calculadoraNormal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                scientific=false;
                for(int i=20;i<26;i++){                       
                    if(basic==false){
                        basic=true;                       
                        reset();
                        
                    }
                    buttons.get(i).setVisible(false);
                    board.repaint();
                }                
            }
        
        });
        JMenuItem clear= new JMenuItem("Limpiar");
        opciones.add(clear);
        clear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                reset();                
                board.repaint();                
            }
        });
        /**
         * transforma a base 2 ya sea desde base 10 o 16
         * Javiera
         */        
        JMenuItem baseDos= new JMenuItem("Base 2");
        opciones.add(baseDos);
        baseDos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {                   
                if(based2 == false){
                    ArrayList<Integer> nueva= new ArrayList();
                    for(int i=2;i<10;i++){
                        buttons.get(i).setVisible(false);
                    }
                    for(int i=26;i<32;i++){
                        buttons.get(i).setVisible(false);                        
                    }            
                    if(based16==true){            
                        nueva=decimal(expression);
                        expression=nueva;                    
                        based16=false;
                        nueva=binario(expression);
                        expression=nueva;                    
                        //SE TRANSFORMA A DECIMAL Y LUEGO ESA EXPRESION A binario                
                    }
                    else{
                        nueva=binario(expression);
                        expression=nueva;                                     
                        //SE TRANSFORMA DE DECIMAL A HBINARIO                 
                    } 
                    listTextbox=atexto(expression);
                    TextBox.setText(listTextbox);                
                    based2=true;                
                    reescribir();                                                           
                }                
            }
        });
        /**
         * transforma a base 10 ya sea desde base 2 o 16
         * Javiera
         */        
        JMenuItem baseDiez= new JMenuItem("Base 10");
        opciones.add(baseDiez);               
        baseDiez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {           
                if(based2==true || based16==true){
                    //ESCONDER BOTONES DE LAS LETRAS
                    for(int i=26;i<32;i++){
                        buttons.get(i).setVisible(false);                       
                    }
                    //Mostrar botones del 2 al 9
                    for(int i=2;i<10;i++){                    
                        buttons.get(i).setVisible(true);
                    }                                                   
                    //Transforma la lista desde alguna de las bases a decimal
                    ArrayList<Integer> n= new ArrayList();
                    n=decimal(expression);
                    expression=n;
                    listTextbox=atexto(expression);
                    TextBox.setText(listTextbox); 
                    based2=false;
                    based16=false;
                    reescribir();               
                }                
            }
        });
        /**
         * transforma a base 16 ya sea desde base 2 o 10
         * Javiera
         */        
        JMenuItem baseDiezy6= new JMenuItem("Base 16");
        opciones.add(baseDiezy6);
        baseDiezy6.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {     
                if(based16==false){
                    ArrayList<Integer> nueva= new ArrayList();
                    //Agrega los botones de las letras del 2 al 9                
                    for(int i=2;i<10;i++){                    
                        buttons.get(i).setVisible(true);
                    }
                    //Agrega los botones de las letras de la A a la F                  
                    for(int i=26;i<32;i++){
                        buttons.get(i).setVisible(true);                    
                    }                
                    ArrayList<Integer> n= new ArrayList<>();
                    if(based2==true){
                        nueva=decimal(expression);
                        expression=nueva;                                        
                        nueva=hexadecimal(expression);
                        expression=nueva; 
                        //SE TRANSFORMA A DECIMAL Y LUEGO ESA EXPRESION A HEXADECIMAL                
                    }
                    else{                        
                        nueva=hexadecimal(expression);
                        expression=nueva;                                      
                        //SE TRANSFORMA DE DECIMAL A HEXADECIMAL                    
                    }      
                    listTextbox=atexto(expression);
                    System.out.println("texto hexadecimal "+ listTextbox);
                    TextBox.setText(listTextbox);
                    based2=false;
                    based16=true;
                    //reescribir se supone :c    
                    reescribir();
                }
            }                    
        });
        /*Ambos paneles uno al lado del otro*/
        TextBox.add(text);   
        PanelCont.add(board,BorderLayout.NORTH); 
        PanelCont.add(TextBox, BorderLayout.SOUTH);                          
        add(PanelButtons,BorderLayout.NORTH);
        add(PanelCont, BorderLayout.CENTER);                                 
        this.pack();       
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
    }
    /**
     * JAVIERA, esto reescribe pero mal asi que lo debemos arreglar cabros
     */
    public void reescribir(){
        count=0;
        board.getFigures().setPtosControl();
        buttonDivPress=false;
        xk=1;
        yk=0;                
        board.getFigures().setCurves(new Curve[140][10]);
        board.getFigures().setLines(new Line[140][10]);
        board.getFigures().setLinesDiv(new Line[25][30]);                
        for (int i = 0; i < expression.size(); i++) {
            if(expression.get(i)==12){
                buttonDivPress=true;
            }   
            if(expression.get(i)==88){
                buttonDivPress=false;
                if (yk>0){
                    yk-=1;
                    
                }
            }
            write();
        }          
    }
    /**
     * Valida que la expresion cuente con la misma cantidad de parentesis izquierdos y derechos
     * @param lista
     * @return si es verdadero o falso
     * @autor Javiera
     */
    public boolean validarParentesis(ArrayList<Integer> lista){
        int aux=0;
        for(int i=0;i<lista.size();i++){
            if(lista.get(i)==14){
                aux++;
            }
            if(lista.get(i)==15){
                aux--;
            }
        }
        if(aux==0){
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * Camilo
     */
    public ArrayList<Integer> reducirFormula(ArrayList<Integer> expresion){
        for(int i=0; i<expresion.size()-1;i++){
            int opActual= expresion.get(i);
            int opSgte= expresion.get(i+1);
            if(opActual==10 || opActual==11){
                if(opSgte==10 || opSgte==11){
                    if(opActual==opSgte){
                        expresion=reemplazarSigno(expresion,i,10);
                        i--;
                    }
                    else{
                        expresion= reemplazarSigno(expresion,i,11);
                        i--;
                    }
                }            
            }        
        }        
        return expresion;
    }
    /**
     * Camilo
     * @param expresion
     * @param i
     * @param signo
     * @return 
     */
    public ArrayList<Integer> reemplazarSigno(ArrayList<Integer> expresion, int i, int signo){
        expresion.remove(i);
        expresion.remove(i);
        expresion.add(i,signo);
        return expresion;    
    }
    /**
     * Valida que la sintaxis de una formula este correcta
     * @param lista
     * @return 
     * @autor Javiera
     */
    public boolean validarSignos(ArrayList<Integer> lista){        
        for(int i=0;i<lista.size()-1;i++){            
//            if(lista.get(0)!= 14 ||lista.get(0)!= 17 || lista.get(0)!= 18 || lista.get(0)!= 19 || lista.get(0)!= 21 )
//                return true;
            // si hay una division, multiplicacion,factorial, seno, coseno, tangente, potencia              
            if(lista.get(i)>10 && lista.get(i)!=10 && lista.get(i)!=11 && lista.get(i)!=14 && lista.get(i)!=15){
                //si hay dos seguidas, se invalida
                if(lista.get(i) == lista.get(i+1)){
                    System.out.println("primer if");
                    return false;
                }
            }
            //si es suma 
            if((lista.get(i)==10 || lista.get(i) == 11 || lista.get(i) == 21) && (lista.get(i+1) == 12 || lista.get(i+1) == 13 
                    || lista.get(i+1) == 15 || lista.get(i+1) == 16 || lista.get(i+1) == 20 )){
                System.out.println("segundo if");
                return false;
            }
            //si es resta  
            if(lista.get(i)==11  && (lista.get(i+1) == 12 || lista.get(i+1) == 13 || lista.get(i+1) == 15 || lista.get(i+1) == 16 
                    || lista.get(i+1) == 20 )){
                System.out.println("tercer if");
                return false;
            }
            //si es  negativo 
            if( lista.get(i) == 21 && (lista.get(i+1) == 12 || lista.get(i+1) == 13
                || lista.get(i+1) == 15 || lista.get(i+1) == 16 || lista.get(i+1) == 20 )){
                System.out.println("cuarto if");
                return false;
            }
            // si es una division y seguido de ella viene una multiplicacion o un cierre de parentesis
            //o un factorial o una potencia se invalida
            if(lista.get(i) == 12 && (lista.get(i+1) == 10 || lista.get(i+1) == 11 || lista.get(i+1) == 13 || lista.get(i+1) == 15 
                    || lista.get(i+1) == 16 || lista.get(i+1) == 20)){
                System.out.println("quinto if");
                return false;
            } 
            // si es una multiplicacion y seguido de ella viene una division o un cierre de parentesis o un factorial o una potencia, se invalida
            if(lista.get(i) == 13 && (lista.get(i+1) == 10 || lista.get(i+1) == 11 || lista.get(i+1) == 12 || lista.get(i+1) == 15
                    || lista.get(i+1) == 16 || lista.get(i+1) == 20)){
                System.out.println("sexto if");
                return false;
            }
            //si es un abre parentesis, seguido de el no puede venir un operador de suma, resta, divisio, multiplicacion, cierre parentesis, factorial, potencia
            if(lista.get(i) == 14 && (lista.get(i+1) == 10 || lista.get(i+1) == 11 || lista.get(i+1) == 12 || lista.get(i+1) == 13 
                    || lista.get(i+1) == 15 || lista.get(i+1) == 16 || lista.get(i+1) == 20)){
                System.out.println("septimo if");
                return false;
            }
            // si es un cierre parentesis, seguido de el no puede venir un operador de abre parentesis, seno, coseno, tangente o un negativo
            if(lista.get(i) == 15 && (lista.get(i+1) == 14 || lista.get(i+1) == 17 || lista.get(i+1) == 18 || lista.get(i+1) == 19
                    || lista.get(i+1) == 21)){
                System.out.println("octavo if");
                return false;
            }
            //si es un factorial, seguido de el no puede venir un abre parentesis, seno, coseno, tangente o negativo
            if(lista.get(i) == 16 && (lista.get(i+1) == 14 || lista.get(i+1) == 17 || lista.get(i+1) == 18 || lista.get(i+1) == 19
                    || lista.get(i+1) == 21)){
                System.out.println("noveno if");
                return false;                
            }
            //si es un seno, seguido de el si o si debe venir un abre parentesis
            if(lista.get(i) == 17 && lista.get(i+1) !=14 ){
                System.out.println("decimo if");
                return false;
            }
            //si es un coseno, seguido de el si o si debe venir un abre parentesis
            if(lista.get(i) == 18 && lista.get(i+1) !=14 ){
                System.out.println("onceavo if");
                return false;
            }            
            //si es una tangente, seguido de ella si o si debe venir un abre parentesis
            if(lista.get(i) == 19 && lista.get(i+1) !=14 ){
                System.out.println("doceavo if");
                return false;
            }
            //si es una potencia, seguido de ella no debe venir una suma, resta, division, multiplicacion, cierre parentesis, factorial
            if(lista.get(i) == 20 && (lista.get(i+1) == 10 || lista.get(i+1) == 11 || lista.get(i+1) == 12 || lista.get(i+1) == 13 
                    || lista.get(i+1) == 15 || lista.get(i+1) == 16 || lista.get(i+1) == 21 )){
                System.out.println("treceavo if");
                return false;                
            }
            //un signo negativo debe ir seguido si o si de un numero, otr negativo o un abre parentesis
            if(lista.get(i)==21 && (lista.get(i+1) == 10 || lista.get(i+1)== 11 || lista.get(i+1)== 12 || lista.get(i+1)== 13 
                    || lista.get(i+1)== 15 || lista.get(i+1)== 16 || lista.get(i+1)== 17 || lista.get(i+1)== 18
                    || lista.get(i+1)== 19 || lista.get(i+1)== 20)){
                System.out.println("catorceavo if");
                return false;
            }            
        }    
        return true;
    }
    /**
     * Recibe una lista de enteros en hexadecimal
     * @param lista
     * @return una lista de enteros en base decimal 
     * @autor Javiera
     */
    public ArrayList<Integer> transformarFromHexaADecimal(ArrayList<Integer> lista){
        int d=0;
        int aux=0;
        for(int i=lista.size()-1;i>=0;i--){
            if(lista.get(i)>=22){
                d+=Math.pow(16,aux)*(lista.get(i)-12);
            }
            else{
                d+=Math.pow(16,aux)*lista.get(i);
            }            
            aux++;            
        }          
        return digitos(d);             
    }
    /**
     * Recibe una lista de enteros en base decimal
     * @param lista
     * @return una lista de enteros en base binaria
     * @autor Javiera 
     */
    public ArrayList<Integer> binario(ArrayList<Integer> lista){
        ArrayList<Integer> binaryExpression= new ArrayList();
        for(int i=0;i<lista.size();i++){        
            if(lista.get(i)!=10 && lista.get(i)!=11 && lista.get(i)!=12 && lista.get(i)!=13 && lista.get(i)!=14 && lista.get(i)!=15 && lista.get(i)!=16
                    && lista.get(i)!=17 && lista.get(i)!=18 && lista.get(i)!=19 && lista.get(i)!=20 && lista.get(i)!=21 && lista.get(i)!=88){
                int aux=i;                
                ArrayList<Integer> subLista= new ArrayList<>();
                for(int j=aux;j<lista.size() && lista.get(i)!=10 && lista.get(i)!=11 && lista.get(i)!=12 && lista.get(i)!=13 
                        && lista.get(i)!=14 && lista.get(i)!=15 && lista.get(i)!=16 && lista.get(i)!=17 && lista.get(i)!=18 
                        && lista.get(i)!=19 && lista.get(i)!=20 && lista.get(i)!=21 && lista.get(i)!=88;j++){
                    i++;
                    subLista.add(lista.get(j));
                }   
                i--;
                int numero=transformate(subLista);
                subLista=transformateBinary(numero);               
                for(int k=0;k<subLista.size();k++){
                    binaryExpression.add(subLista.get(k));
                }
            }
            else{
                binaryExpression.add(lista.get(i));          
            }          
        }            
        return binaryExpression;
    }
    /**
     * Recibe una lista de enteros en base decimal
     * @param lista
     * @return una lista de enteros en base hexadecimal
     * @autor Javiera
     */
    public ArrayList<Integer> hexadecimal(ArrayList<Integer> lista){
        ArrayList<Integer> hexadecimalExpresion= new ArrayList<>();
        for(int i=0;i<lista.size();i++){        
            if(lista.get(i)!=10 && lista.get(i)!=11 && lista.get(i)!=12 && lista.get(i)!=13 && lista.get(i)!=14 && lista.get(i)!=15 && lista.get(i)!=16
                    && lista.get(i)!=17 && lista.get(i)!=18 && lista.get(i)!=19 && lista.get(i)!=20 && lista.get(i)!=21 && lista.get(i)!=88){
                int aux=i;                
                ArrayList<Integer> subLista= new ArrayList<>();
                for(int j=aux;j<lista.size() && lista.get(i)!=10 && lista.get(i)!=11 && lista.get(i)!=12 && lista.get(i)!=13 
                        && lista.get(i)!=14 && lista.get(i)!=15 && lista.get(i)!=16 && lista.get(i)!=17 && lista.get(i)!=18 
                        && lista.get(i)!=19 && lista.get(i)!=20 && lista.get(i)!=21 && lista.get(i)!=88;j++){
                    i++;
                    subLista.add(lista.get(j));
                }   
                i--;
                int numero=transformate(subLista);
                subLista=transformarHexa(numero);               
                for(int k=0;k<subLista.size();k++){
                    hexadecimalExpresion.add(subLista.get(k));
                }
            }
            else{
                hexadecimalExpresion.add(lista.get(i));          
            }          
        } 
        return hexadecimalExpresion;
    }
    /**
     * Recibe una lista de enteros ya sea en base decimal o hexadecimal y la transforma a base decimal
     * @param lista
     * @return retorna una lista de enteros en base decimal
     * @autor Javiera
     */
    public ArrayList<Integer> decimal(ArrayList<Integer> lista){ 
        if(based2==false && based16==false){
            return lista;
        }
        ArrayList<Integer> decimalExpresion= new ArrayList<>();
        for(int i=0;i<lista.size();i++){        
            if(lista.get(i)!=10 && lista.get(i)!=11 && lista.get(i)!=12 && lista.get(i)!=13 && lista.get(i)!=14 && lista.get(i)!=15 && lista.get(i)!=16
                    && lista.get(i)!=17 && lista.get(i)!=18 && lista.get(i)!=19 && lista.get(i)!=20 && lista.get(i)!=21 && lista.get(i)!=88){
                int aux=i;                
                ArrayList<Integer> subLista= new ArrayList<>();
                for(int j=aux;j<lista.size() && lista.get(i)!=10 && lista.get(i)!=11 && lista.get(i)!=12 && lista.get(i)!=13 
                        && lista.get(i)!=14 && lista.get(i)!=15 && lista.get(i)!=16 && lista.get(i)!=17 && lista.get(i)!=18 
                        && lista.get(i)!=19 && lista.get(i)!=20 && lista.get(i)!=21 && lista.get(i)!=88;j++){
                    i++;
                    subLista.add(lista.get(j));
                }   
                i--;
                ArrayList<Integer> m= new ArrayList();
                if(based2==true){//desde binarioooooo a decimal                   
                    m=transformateDecimal(subLista);  
                    for(int k=0;k<m.size();k++){
                        decimalExpresion.add(m.get(k));
                    }                    
                }
                else{
                    if(based16==true){//desde hexadecimaaaal a decimal          
                        m=transformarFromHexaADecimal(subLista);
                        for(int k=0;k<m.size();k++){
                            decimalExpresion.add(m.get(k));
                        }                                                    
                    }
                    else{
                        i=lista.size()+1;
                    }
                }                 
            }
            else{
                decimalExpresion.add(lista.get(i));          
            }          
        } 
        return decimalExpresion;            
    } 
    /**
     * Metodo que pasa un arrayList de enteros a un String cualquiera sea su base
     * Javiera
     */
    public String atexto(ArrayList<Integer> lista){
        String signos[]={"+","-","/","*","(",")","!","sen","cos","tan","^","-","A","B","C","D","E","F"};
        String texto="";
        for(int k=0;k<lista.size();k++){
            if(lista.get(k)<10){
                String x= String.valueOf(lista.get(k));
                texto+=x;
            }                    
            else{
                int auxiliar=0;
                for(int m=10;m<28;m++){
                    if(lista.get(k)==m){
                        texto+=signos[auxiliar];                            
                    }                        
                    auxiliar++;
                }                    
            }
        }                            
        return texto;
    }
    /**
     * Javiera metodo que transforma un numero entero y lo devuelve como el mismo numero separado por sus digitos en una lista
     * @param number
     * @return 
     */
    public ArrayList<Integer> digitos(int number){
        ArrayList<Integer> l= new ArrayList<>();
        while(number>=10){
            int r= (int)(number%10);
            l.add(r);
            number/=10;
        }
        ArrayList<Integer> m= new ArrayList<>();
        l.add(number);        
        for(int j=l.size()-1;j>=0;j--){
            m.add(l.get(j));  
        }        
        return m;              
    }
    /**
     * Javiera 
     * metodo que transforma un numero binario a un numero decimal
     */
    public ArrayList<Integer> transformateDecimal(ArrayList<Integer> lista){
        int aux=0;
        int number=0;
        for(int i=lista.size()-1;i>=0;i--){
            number+=Math.pow(2,aux)*lista.get(i);
            aux++;
        }
        return digitos(number);
    }
    /**
     * Javiera pasar un numero base decimal a base binaria
     */
    public ArrayList<Integer> transformateBinary(int n){
        ArrayList<Integer> l= new ArrayList<>();
        ArrayList<Integer> nueva= new ArrayList<>();
        while(n!=1){
            int r= (int)(n%2);
            l.add(r);
            n/=2;
        }          
        l.add(n);        
        for(int i=l.size()-1;i>=0;i--){
            nueva.add(l.get(i));  
        }
        return nueva;
    }
    /**
     * Javiera transforma el numero n de base decimal a base hexadecimal
     * @param n
     * @return 
     */
    public ArrayList<Integer> transformarHexa(int n){
        ArrayList<Integer> l= new ArrayList<>();
        ArrayList<Integer> aux= new ArrayList<>();
        while(n!=0){
            int r= (int) n%16;
            l.add(r);
            n/=16;       
        }
        for(int i=l.size()-1;i>=0;i--){
            if(l.get(i)<10){
                aux.add(l.get(i));
            }
            if(l.get(i)==10){
                aux.add(22);
            }
            if(l.get(i)==11){
                aux.add(23);
            }            
            if(l.get(i)==12){
                aux.add(24);
            }            
            if(l.get(i)==13){
                aux.add(25);
            }            
            if(l.get(i)==14){
                aux.add(26);
            }            
            if(l.get(i)==15){
                aux.add(27);
            }            
        }        
        return aux;
    }
    /**
     * Javiera pasar una cadena de numeros enteros a un numero entero
     * [1,4,7,2] lo retorna como 1472
     */
    public int transformate(ArrayList<Integer> lista){
        int numero=0;
        int aux=lista.size()-1;
        for(int k=0; k<lista.size();k++){
            numero+=Math.pow(10,aux)*lista.get(k);
            aux--;
        }
        return numero;
    }
    /**
     * Metodo que crea la figura segun el ultimo caracter en expresion.
     * @autor Marcelo, Javiera, Camilo
     */
    public void write(){
        System.out.println("--------------------------------");
        System.out.println("xk: "+xk);
        System.out.println("yk: "+yk);
        System.out.println("Listaaa "+listTextbox);
        TextBox.setText(listTextbox);
        //System.out.println("caracter: "+expression.get(count));
        System.out.println("caracter: "+expression.get(count));
        int pos=0;
        if(expression.get(count)==0){
            board.getFigures().addCurve((board.getCar(0,1)+desHor*xk)/size,(board.getCar(0,2)+desVer*yk)/size
                    ,(board.getCar(0,5)+desHor*xk)/size ,(board.getCar(0,6)+desVer*yk)/size
                    ,(board.getCar(0,7)+desHor*xk)/size ,(board.getCar(0,8)+desVer*yk)/size
                    ,(board.getCar(0,3)+desHor*xk)/size , (board.getCar(0,4)+desVer*yk)/size,count,pos);
            board.getFigures().addCurve((board.getCar(0,1)+desHor*xk)/size,(board.getCar(0,2)+desVer*yk)/size
                    , (board.getCar(0,9)+desHor*xk)/size ,(board.getCar(0,10)+desVer*yk)/size
                    , (board.getCar(0,11)+desHor*xk)/size ,(board.getCar(0,12)+desVer*yk)/size
                    , (board.getCar(0,3)+desHor*xk)/size , (board.getCar(0,4)+desVer*yk)/size,count,pos+1);               
            board.getFigures().addPointControl((board.getCar(0,1)+desHor*xk)/size,(board.getCar(0,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(0,3)+desHor*xk)/size , (board.getCar(0,4)+desVer*yk)/size);
        }
        if(expression.get(count)==1){
            board.getFigures().addLine((board.getCar(1,1)+desHor*xk)/size,(board.getCar(1,2)+desVer*yk)/size
                    , (board.getCar(1,3)+desHor*xk)/size,(board.getCar(1,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(1,3)+desHor*xk)/size,(board.getCar(1,4)+desVer*yk)/size
                    , (board.getCar(1,5)+desHor*xk)/size,(board.getCar(1,6)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(1,1)+desHor*xk)/size,(board.getCar(1,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(1,3)+desHor*xk)/size,(board.getCar(1,4)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(1,5)+desHor*xk)/size,(board.getCar(1,6)+desVer*yk)/size);
 
        }
        if(expression.get(count)==2){
            board.getFigures().addCurve((board.getCar(2,1)+desHor*xk)/size,(board.getCar(2,2)+desVer*yk)/size
                    , (board.getCar(2,5)+desHor*xk)/size,(board.getCar(2,6)+desVer*yk)/size
                    , (board.getCar(2,7)+desHor*xk)/size,(board.getCar(2,8)+desVer*yk)/size
                    , (board.getCar(2,3)+desHor*xk)/size,(board.getCar(2,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(2,3)+desHor*xk)/size,(board.getCar(2,4)+desVer*yk)/size
                    ,(board.getCar(2,9)+desHor*xk)/size,(board.getCar(2,10)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(2,9)+desHor*xk)/size,(board.getCar(2,10)+desVer*yk)/size
                    ,(board.getCar(2,11)+desHor*xk)/size,(board.getCar(2,12)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(2,1)+desHor*xk)/size,(board.getCar(2,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(2,3)+desHor*xk)/size,(board.getCar(2,4)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(2,9)+desHor*xk)/size,(board.getCar(2,10)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(2,11)+desHor*xk)/size,(board.getCar(2,12)+desVer*yk)/size);

        }
        if(expression.get(count)==3){
            board.getFigures().addCurve((board.getCar(3,1)+desHor*xk)/size,(board.getCar(3,2)+desVer*yk)/size
                    ,(board.getCar(3,7)+desHor*xk)/size,(board.getCar(3,8)+desVer*yk)/size
                    ,(board.getCar(3,9)+desHor*xk)/size,(board.getCar(3,10)+desVer*yk)/size
                    ,(board.getCar(3,3)+desHor*xk)/size,(board.getCar(3,4)+desVer*yk)/size,count,pos);
            board.getFigures().addCurve((board.getCar(3,3)+desHor*xk)/size,(board.getCar(3,4)+desVer*yk)/size
                    ,(board.getCar(3,9)+desHor*xk)/size,(board.getCar(3,10)+desVer*yk)/size
                    ,(board.getCar(3,11)+desHor*xk)/size,(board.getCar(3,12)+desVer*yk)/size
                    ,(board.getCar(3,5)+desHor*xk)/size,(board.getCar(3,6)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(3,1)+desHor*xk)/size,(board.getCar(3,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(3,3)+desHor*xk)/size,(board.getCar(3,4)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(3,5)+desHor*xk)/size,(board.getCar(3,6)+desVer*yk)/size);

        }
        if(expression.get(count)==4){
            board.getFigures().addLine((board.getCar(4,1)+desHor*xk)/size,(board.getCar(4,2)+desVer*yk)/size
                    ,(board.getCar(4,3)+desHor*xk)/size,(board.getCar(4,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(4,3)+desHor*xk)/size,(board.getCar(4,4)+desVer*yk)/size
                    ,(board.getCar(4,5)+desHor*xk)/size,(board.getCar(4,6)+desVer*yk)/size,count,pos+1);
            board.getFigures().addLine((board.getCar(4,5)+desHor*xk)/size,(board.getCar(4,6)+desVer*yk)/size
                    ,(board.getCar(4,7)+desHor*xk)/size,(board.getCar(4,8)+desVer*yk)/size,count,pos+2);
            board.getFigures().addPointControl((board.getCar(4,1)+desHor*xk)/size,(board.getCar(4,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(4,3)+desHor*xk)/size,(board.getCar(4,4)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(4,5)+desHor*xk)/size,(board.getCar(4,6)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(4,7)+desHor*xk)/size,(board.getCar(4,8)+desVer*yk)/size);

        }
        if(expression.get(count)==5){
            board.getFigures().addLine((board.getCar(5,1)+desHor*xk)/size,(board.getCar(5,2)+desVer*yk)/size
                    ,(board.getCar(5,3)+desHor*xk)/size,(board.getCar(5,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(5,3)+desHor*xk)/size,(board.getCar(5,4)+desVer*yk)/size
                    ,(board.getCar(5,5)+desHor*xk)/size,(board.getCar(5,6)+desVer*yk)/size,count,pos+1);
            board.getFigures().addCurve((board.getCar(5,5)+desHor*xk)/size,(board.getCar(5,6)+desVer*yk)/size, 
                    (board.getCar(5,9)+desHor*xk)/size,(board.getCar(5,10)+desVer*yk)/size, 
                    (board.getCar(5,11)+desHor*xk)/size,(board.getCar(5,12)+desVer*yk)/size, 
                    (board.getCar(5,7)+desHor*xk)/size,(board.getCar(5,8)+desVer*yk)/size,count,pos);
            board.getFigures().addPointControl((board.getCar(5,1)+desHor*xk)/size,(board.getCar(5,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(5,3)+desHor*xk)/size,(board.getCar(5,4)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(5,5)+desHor*xk)/size,(board.getCar(5,6)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(5,7)+desHor*xk)/size,(board.getCar(5,8)+desVer*yk)/size);
        }
        if(expression.get(count)==6){
             board.getFigures().addLine((board.getCar(6,1)+desHor*xk)/size,(board.getCar(6,2)+desVer*yk)/size
                    ,(board.getCar(6,3)+desHor*xk)/size,(board.getCar(6,4)+desVer*yk)/size,count,pos);
             board.getFigures().addCurve((board.getCar(6,3)+desHor*xk)/size,(board.getCar(6,4)+desVer*yk)/size, 
                    (board.getCar(6,5)+desHor*xk)/size,(board.getCar(6,6)+desVer*yk)/size, 
                    (board.getCar(6,7)+desHor*xk)/size,(board.getCar(6,8)+desVer*yk)/size, 
                    (board.getCar(6,3)+desHor*xk)/size,(board.getCar(6,4)+desVer*yk)/size,count,pos);
             board.getFigures().addPointControl((board.getCar(6,1)+desHor*xk)/size,(board.getCar(6,2)+desVer*yk)/size);
             board.getFigures().addPointControl((board.getCar(6,3)+desHor*xk)/size,(board.getCar(6,4)+desVer*yk)/size);


        }
        if(expression.get(count)==7){
            board.getFigures().addLine((board.getCar(7,1)+desHor*xk)/size,(board.getCar(7,2)+desVer*yk)/size
                    ,(board.getCar(7,3)+desHor*xk)/size,(board.getCar(7,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(7,3)+desHor*xk)/size,(board.getCar(7,4)+desVer*yk)/size
                    ,(board.getCar(7,5)+desHor*xk)/size,(board.getCar(7,6)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(7,1)+desHor*xk)/size,(board.getCar(7,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(7,3)+desHor*xk)/size,(board.getCar(7,4)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(7,5)+desHor*xk)/size,(board.getCar(7,6)+desVer*yk)/size);

        }
        if(expression.get(count)==8){
            board.getFigures().addCurve((board.getCar(8,1)+desHor*xk)/size,(board.getCar(8,2)+desVer*yk)/size, 
                    (board.getCar(8,3)+desHor*xk)/size,(board.getCar(8,4)+desVer*yk)/size, 
                    (board.getCar(8,5)+desHor*xk)/size,(board.getCar(8,6)+desVer*yk)/size, 
                    (board.getCar(8,1)+desHor*xk)/size,(board.getCar(8,2)+desVer*yk)/size,count,pos);
            board.getFigures().addCurve((board.getCar(8,1)+desHor*xk)/size,(board.getCar(8,2)+desVer*yk)/size, 
                    (board.getCar(8,7)+desHor*xk)/size,(board.getCar(8,8)+desVer*yk)/size, 
                    (board.getCar(8,9)+desHor*xk)/size,(board.getCar(8,10)+desVer*yk)/size, 
                    (board.getCar(8,1)+desHor*xk)/size,(board.getCar(8,2)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(8,1)+desHor*xk)/size,(board.getCar(8,2)+desVer*yk)/size);

        }
        if(expression.get(count)==9){
            board.getFigures().addLine((board.getCar(9,1)+desHor*xk)/size,(board.getCar(9,2)+desVer*yk)/size,
                    (board.getCar(9,3)+desHor*xk)/size,(board.getCar(9,4)+desVer*yk)/size,count,pos);
            board.getFigures().addCurve((board.getCar(9,1)+desHor*xk)/size,(board.getCar(9,2)+desVer*yk)/size, 
                    (board.getCar(9,5)+desHor*xk)/size,(board.getCar(9,6)+desVer*yk)/size, 
                    (board.getCar(9,7)+desHor*xk)/size,(board.getCar(9,8)+desVer*yk)/size, 
                    (board.getCar(9,1)+desHor*xk)/size,(board.getCar(9,2)+desVer*yk)/size,count,pos);
            board.getFigures().addPointControl((board.getCar(9,1)+desHor*xk)/size,(board.getCar(9,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(9,3)+desHor*xk)/size,(board.getCar(9,4)+desVer*yk)/size);

        }
        if(expression.get(count)==10){
            board.getFigures().addLine((board.getCar(10,1)+desHor*xk)/size,(board.getCar(10,2)+desVer*yk)/size,
                    (board.getCar(10,3)+desHor*xk)/size,(board.getCar(10,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(10,5)+desHor*xk)/size,(board.getCar(10,6)+desVer*yk)/size,
                    (board.getCar(10,7)+desHor*xk)/size,(board.getCar(10,8)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(10,1)+desHor*xk)/size,(board.getCar(10,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(10,3)+desHor*xk)/size,(board.getCar(10,4)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(10,5)+desHor*xk)/size,(board.getCar(10,6)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(10,7)+desHor*xk)/size,(board.getCar(10,8)+desVer*yk)/size);

        }
        if(expression.get(count)==11 || expression.get(count)==21){
            board.getFigures().addLine((board.getCar(11,1)+desHor*xk)/size,(board.getCar(11,2)+desVer*yk)/size,
                    (board.getCar(11,3)+desHor*xk)/size,(board.getCar(11,4)+desVer*yk)/size,count,pos);
            board.getFigures().addPointControl((board.getCar(11,1)+desHor*xk)/size,(board.getCar(11,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(11,3)+desHor*xk)/size,(board.getCar(11,4)+desVer*yk)/size);
        }
        if (expression.get(count)==12 && !resizing){     
            System.out.println(limit);
            defDivision(limit);
            board.getFigures().addPointControl((board.getCar(12,1)+desHor*(xk+1))/size,(board.getCar(12,2)+desVer*(yk-1))/size);
            board.getFigures().addPointControl((board.getCar(12,3)+desHor*(lastPtoDiv))/size,(board.getCar(12,4)+desVer*(yk-1))/size);
            //limit=count;           
        }           
        if(expression.get(count)==13){
            board.getFigures().addLine((board.getCar(13,1)+desHor*xk)/size,(board.getCar(13,2)+desVer*yk)/size,
                    (board.getCar(13,3)+desHor*xk)/size,(board.getCar(13,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(13,5)+desHor*xk)/size,(board.getCar(13,6)+desVer*yk)/size,
                    (board.getCar(13,7)+desHor*xk)/size,(board.getCar(13,8)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(13,1)+desHor*xk)/size,(board.getCar(13,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(13,3)+desHor*xk)/size,(board.getCar(13,4)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(13,5)+desHor*xk)/size,(board.getCar(13,6)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(13,7)+desHor*xk)/size,(board.getCar(13,8)+desVer*yk)/size);
        }
        if(expression.get(count)==14){
            if (firstPar){
                startPar=xk;
                firstPar=false;
            }
            System.out.println("startPar: "+startPar);
            board.getFigures().addCurve((board.getCar(14,1)+desHor*xk)/size,(board.getCar(14,2)+desVer*yk)/size, 
                    (board.getCar(14,5)+desHor*xk)/size,(board.getCar(14,6)+desVer*yk)/size, 
                    (board.getCar(14,7)+desHor*xk)/size,(board.getCar(14,8)+desVer*yk)/size, 
                    (board.getCar(14,3)+desHor*xk)/size,(board.getCar(14,4)+desVer*yk)/size,count,pos);
            board.getFigures().addPointControl((board.getCar(14,1)+desHor*xk)/size,(board.getCar(14,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(14,3)+desHor*xk)/size,(board.getCar(14,4)+desVer*yk)/size);
        }
        if(expression.get(count)==15){
            endPar=xk;
            System.out.println("enPar: "+endPar);
            board.getFigures().addCurve((board.getCar(15,1)+desHor*xk)/size,(board.getCar(15,2)+desVer*yk)/size, 
                    (board.getCar(15,5)+desHor*xk)/size,(board.getCar(15,6)+desVer*yk)/size, 
                    (board.getCar(15,7)+desHor*xk)/size,(board.getCar(15,8)+desVer*yk)/size, 
                    (board.getCar(15,3)+desHor*xk)/size,(board.getCar(15,4)+desVer*yk)/size,count,pos);
            board.getFigures().addPointControl((board.getCar(15,1)+desHor*xk)/size,(board.getCar(15,2)+desVer*yk)/size);
            board.getFigures().addPointControl((board.getCar(15,3)+desHor*xk)/size,(board.getCar(15,4)+desVer*yk)/size);
        }    
        if(expression.get(count)==16){
            board.getFigures().addLine((board.getCar(16,1)+desHor*xk)/size,(board.getCar(16,2)+desVer*yk)/size
                    ,(board.getCar(16,3)+desHor*xk)/size,(board.getCar(16,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(16,5)+desHor*xk)/size,(board.getCar(16,6)+desVer*yk)/size
                    ,(board.getCar(16,7)+desHor*xk)/size,(board.getCar(16,8)+desVer*yk)/size,count,pos+1);
            board.getFigures().addLine((board.getCar(16,9)+desHor*xk)/size,(board.getCar(16,10)+desVer*yk)/size
                    ,(board.getCar(16,11)+desHor*xk)/size,(board.getCar(16,12)+desVer*yk)/size,count,pos+2);
            board.getFigures().addLine((board.getCar(16,13)+desHor*xk)/size,(board.getCar(16,14)+desVer*yk)/size
                    ,(board.getCar(16,15)+desHor*xk)/size,(board.getCar(16,16)+desVer*yk)/size,count,pos+3);
            board.getFigures().addLine((board.getCar(16,17)+desHor*xk)/size,(board.getCar(16,18)+desVer*yk)/size
                    ,(board.getCar(16,19)+desHor*xk)/size,(board.getCar(16,20)+desVer*yk)/size,count,pos+4);
            board.getFigures().addPointControl((board.getCar(16,1)+desHor*xk)/size,(board.getCar(16,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(16,3)+desHor*xk)/size,(board.getCar(16,4)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(16,17)+desHor*xk)/size,(board.getCar(16,18)+desVer*yk)/size );        
        }
        /**
         * Marcelo
         */
        //SENO
        if(expression.get(count)==17){
            board.getFigures().addCurve((board.getCar(17,1)+desHor*xk)/size,(board.getCar(17,2)+desVer*yk)/size, 
                    (board.getCar(17,3)+desHor*xk)/size,(board.getCar(17,4)+desVer*yk)/size, 
                    (board.getCar(17,5)+desHor*xk)/size,(board.getCar(17,6)+desVer*yk)/size, 
                    (board.getCar(17,7)+desHor*xk)/size,(board.getCar(17,8)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(17,9)+desHor*xk)/size,(board.getCar(17,10)+desVer*yk)/size, 
                    (board.getCar(17,11)+desHor*xk)/size,(board.getCar(17,12)+desVer*yk)/size, count, pos);
            board.getFigures().addLine((board.getCar(17,13)+desHor*xk)/size,(board.getCar(17,14)+desVer*yk)/size, 
                    (board.getCar(17,15)+desHor*xk)/size,(board.getCar(17,16)+desVer*yk)/size, count, pos+1);
            board.getFigures().addLine((board.getCar(17,17)+desHor*xk)/size,(board.getCar(17,18)+desVer*yk)/size, 
                    (board.getCar(17,19)+desHor*xk)/size,(board.getCar(17,20)+desVer*yk)/size, count, pos+2);
            board.getFigures().addLine((board.getCar(17,21)+desHor*xk)/size,(board.getCar(17,22)+desVer*yk)/size, 
                    (board.getCar(17,23)+desHor*xk)/size,(board.getCar(17,24)+desVer*yk)/size, count, pos+3);
            board.getFigures().addLine((board.getCar(17,25)+desHor*xk)/size,(board.getCar(17,26)+desVer*yk)/size, 
                    (board.getCar(17,27)+desHor*xk)/size,(board.getCar(17,28)+desVer*yk)/size, count, pos+4);
            board.getFigures().addLine((board.getCar(17,29)+desHor*xk)/size,(board.getCar(17,30)+desVer*yk)/size, 
                    (board.getCar(17,31)+desHor*xk)/size,(board.getCar(17,32)+desVer*yk)/size, count, pos+5);
            board.getFigures().addLine((board.getCar(17,33)+desHor*xk)/size,(board.getCar(17,34)+desVer*yk)/size, 
                    (board.getCar(17,35)+desHor*xk)/size,(board.getCar(17,36)+desVer*yk)/size, count, pos+6);
            board.getFigures().addPointControl((board.getCar(17,1)+desHor*xk)/size,(board.getCar(17,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,7)+desHor*xk)/size,(board.getCar(17,8)+desVer*yk)/size );            
            board.getFigures().addPointControl((board.getCar(17,9)+desHor*xk)/size,(board.getCar(17,10)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,11)+desHor*xk)/size,(board.getCar(17,12)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,13)+desHor*xk)/size,(board.getCar(17,14)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,15)+desHor*xk)/size,(board.getCar(17,16)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,17)+desHor*xk)/size,(board.getCar(17,18)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,19)+desHor*xk)/size,(board.getCar(17,20)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,21)+desHor*xk)/size,(board.getCar(17,22)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,23)+desHor*xk)/size,(board.getCar(17,24)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,25)+desHor*xk)/size,(board.getCar(17,26)+desVer*yk)/size );           
            board.getFigures().addPointControl((board.getCar(17,27)+desHor*xk)/size,(board.getCar(17,28)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,33)+desHor*xk)/size,(board.getCar(17,34)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(17,35)+desHor*xk)/size,(board.getCar(17,36)+desVer*yk)/size );        
            //AGREGAR CORDENADAS DE SENO        
        }   
        /**
         * Marcelo
         */
        //COSENO
        if(expression.get(count)==18){
            board.getFigures().addCurve((board.getCar(18,1)+desHor*xk)/size,(board.getCar(18,2)+desVer*yk)/size, 
                    (board.getCar(18,3)+desHor*xk)/size,(board.getCar(18,4)+desVer*yk)/size, 
                    (board.getCar(18,5)+desHor*xk)/size,(board.getCar(18,6)+desVer*yk)/size, 
                    (board.getCar(18,7)+desHor*xk)/size,(board.getCar(18,8)+desVer*yk)/size,count,pos);
            board.getFigures().addCurve((board.getCar(18,9)+desHor*xk)/size,(board.getCar(18,10)+desVer*yk)/size, 
                    (board.getCar(18,11)+desHor*xk)/size,(board.getCar(18,12)+desVer*yk)/size, 
                    (board.getCar(18,13)+desHor*xk)/size,(board.getCar(18,14)+desVer*yk)/size, 
                    (board.getCar(18,15)+desHor*xk)/size,(board.getCar(18,16)+desVer*yk)/size,count,pos+1);
            board.getFigures().addCurve((board.getCar(18,17)+desHor*xk)/size,(board.getCar(18,18)+desVer*yk)/size, 
                    (board.getCar(18,19)+desHor*xk)/size,(board.getCar(18,20)+desVer*yk)/size, 
                    (board.getCar(18,21)+desHor*xk)/size,(board.getCar(18,22)+desVer*yk)/size, 
                    (board.getCar(18,23)+desHor*xk)/size,(board.getCar(18,24)+desVer*yk)/size,count,pos+2);
            board.getFigures().addCurve((board.getCar(18,25)+desHor*xk)/size,(board.getCar(18,26)+desVer*yk)/size, 
                    (board.getCar(18,27)+desHor*xk)/size,(board.getCar(18,28)+desVer*yk)/size, 
                    (board.getCar(18,29)+desHor*xk)/size,(board.getCar(18,30)+desVer*yk)/size, 
                    (board.getCar(18,31)+desHor*xk)/size,(board.getCar(18,32)+desVer*yk)/size,count,pos+3);
            board.getFigures().addPointControl((board.getCar(18,1)+desHor*xk)/size,(board.getCar(18,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(18,7)+desHor*xk)/size,(board.getCar(18,8)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(18,9)+desHor*xk)/size,(board.getCar(18,10)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(18,15)+desHor*xk)/size,(board.getCar(18,16)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(18,17)+desHor*xk)/size,(board.getCar(18,18)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(18,23)+desHor*xk)/size,(board.getCar(18,24)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(18,25)+desHor*xk)/size,(board.getCar(18,26)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(18,31)+desHor*xk)/size,(board.getCar(18,32)+desVer*yk)/size );        
            //AGREGAR CORDENADAS DE COSENO        
        }
        /**
         * 
         * Marcelo
         */
        //TANGENTE
        if(expression.get(count)==19){
            board.getFigures().addLine((board.getCar(19,1)+desHor*xk)/size,(board.getCar(19,2)+desVer*yk)/size, 
                    (board.getCar(19,3)+desHor*xk)/size,(board.getCar(19,4)+desVer*yk)/size, count, pos);
            board.getFigures().addLine((board.getCar(19,5)+desHor*xk)/size,(board.getCar(19,6)+desVer*yk)/size, 
                    (board.getCar(19,7)+desHor*xk)/size,(board.getCar(19,8)+desVer*yk)/size, count, pos+1);
            board.getFigures().addLine((board.getCar(19,9)+desHor*xk)/size,(board.getCar(19,10)+desVer*yk)/size, 
                    (board.getCar(19,11)+desHor*xk)/size,(board.getCar(19,12)+desVer*yk)/size, count, pos+2);
            board.getFigures().addLine((board.getCar(19,13)+desHor*xk)/size,(board.getCar(19,14)+desVer*yk)/size, 
                    (board.getCar(19,15)+desHor*xk)/size,(board.getCar(19,16)+desVer*yk)/size, count, pos+3);
            board.getFigures().addLine((board.getCar(19,17)+desHor*xk)/size,(board.getCar(19,18)+desVer*yk)/size, 
                    (board.getCar(19,19)+desHor*xk)/size,(board.getCar(19,20)+desVer*yk)/size, count, pos+4);
            board.getFigures().addLine((board.getCar(19,21)+desHor*xk)/size,(board.getCar(19,22)+desVer*yk)/size, 
                    (board.getCar(19,23)+desHor*xk)/size,(board.getCar(19,24)+desVer*yk)/size, count, pos+5);
            board.getFigures().addLine((board.getCar(19,25)+desHor*xk)/size,(board.getCar(19,26)+desVer*yk)/size, 
                    (board.getCar(19,27)+desHor*xk)/size,(board.getCar(19,28)+desVer*yk)/size, count, pos+6);
            board.getFigures().addLine((board.getCar(19,29)+desHor*xk)/size,(board.getCar(19,30)+desVer*yk)/size, 
                    (board.getCar(19,31)+desHor*xk)/size,(board.getCar(19,32)+desVer*yk)/size, count, pos+7);  
            board.getFigures().addPointControl((board.getCar(19,1)+desHor*xk)/size,(board.getCar(19,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,3)+desHor*xk)/size,(board.getCar(19,4)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,5)+desHor*xk)/size,(board.getCar(19,6)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,7)+desHor*xk)/size,(board.getCar(19,8)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,9)+desHor*xk)/size,(board.getCar(19,10)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,11)+desHor*xk)/size,(board.getCar(19,12)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,13)+desHor*xk)/size,(board.getCar(19,14)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,15)+desHor*xk)/size,(board.getCar(19,16)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,17)+desHor*xk)/size,(board.getCar(19,18)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,19)+desHor*xk)/size,(board.getCar(19,20)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,21)+desHor*xk)/size,(board.getCar(19,22)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,23)+desHor*xk)/size,(board.getCar(19,24)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,25)+desHor*xk)/size,(board.getCar(19,26)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,27)+desHor*xk)/size,(board.getCar(19,28)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(19,29)+desHor*xk)/size,(board.getCar(19,30)+desVer*yk)/size );
        }
        //POTENCIA
        if(expression.get(count)==20){
            board.getFigures().addLine((board.getCar(20,1)+desHor*xk)/size,(board.getCar(20,2)+desVer*yk)/size, 
                    (board.getCar(20,3)+desHor*xk)/size,(board.getCar(20,4)+desVer*yk)/size, count, pos);
            board.getFigures().addLine((board.getCar(20,5)+desHor*xk)/size,(board.getCar(20,6)+desVer*yk)/size, 
                    (board.getCar(20,7)+desHor*xk)/size,(board.getCar(20,8)+desVer*yk)/size, count, pos+1);
            board.getFigures().addPointControl((board.getCar(20,1)+desHor*xk)/size,(board.getCar(20,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(20,3)+desHor*xk)/size,(board.getCar(20,4)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(20,5)+desHor*xk)/size,(board.getCar(20,6)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(20,7)+desHor*xk)/size,(board.getCar(20,8)+desVer*yk)/size );
        }          
        if(expression.get(count)==22){            
            board.getFigures().addLine((board.getCar(21,1)+desHor*xk)/size,(board.getCar(21,2)+desVer*yk)/size
                    , (board.getCar(21,3)+desHor*xk)/size,(board.getCar(21,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(21,5)+desHor*xk)/size,(board.getCar(21,6)+desVer*yk)/size
                    , (board.getCar(21,7)+desHor*xk)/size,(board.getCar(21,8)+desVer*yk)/size,count,pos+1);
            board.getFigures().addLine((board.getCar(21,9)+desHor*xk)/size,(board.getCar(21,10)+desVer*yk)/size
                    , (board.getCar(21,11)+desHor*xk)/size,(board.getCar(21,12)+desVer*yk)/size,count,pos+2);
            board.getFigures().addPointControl((board.getCar(21,1)+desHor*xk)/size,(board.getCar(21,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(21,3)+desHor*xk)/size,(board.getCar(21,4)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(21,5)+desHor*xk)/size,(board.getCar(21,6)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(21,7)+desHor*xk)/size,(board.getCar(21,8)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(21,9)+desHor*xk)/size,(board.getCar(21,10)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(21,11)+desHor*xk)/size,(board.getCar(21,12)+desVer*yk)/size );        
        }
        if(expression.get(count)==23){
            board.getFigures().addLine((board.getCar(22,1)+desHor*(xk))/size,(board.getCar(22,2)+desVer*yk)/size
                    ,(board.getCar(22,3)+desHor*(xk))/size,(board.getCar(22,4)+desVer*yk)/size,count,pos);
            board.getFigures().addCurve((board.getCar(22,5)+desHor*(xk))/size,(board.getCar(22,6)+desVer*yk)/size
                    , (board.getCar(22,7)+desHor*(xk))/size,(board.getCar(22,8)+desVer*yk)/size
                    , (board.getCar(22,9)+desHor*(xk))/size,(board.getCar(22,10)+desVer*yk)/size
                    , (board.getCar(22,11)+desHor*(xk))/size,(board.getCar(22,12)+desVer*yk)/size,count,pos);
            board.getFigures().addCurve((board.getCar(22,13)+desHor*(xk))/size,(board.getCar(22,14)+desVer*yk)/size
                    , (board.getCar(22,15)+desHor*(xk))/size,(board.getCar(22,16)+desVer*yk)/size
                    , (board.getCar(22,17)+desHor*(xk))/size,(board.getCar(22,18)+desVer*yk)/size
                    , (board.getCar(22,19)+desHor*(xk))/size,(board.getCar(22,20)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(22,1)+desHor*xk)/size,(board.getCar(22,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(22,3)+desHor*xk)/size,(board.getCar(22,4)+desVer*yk)/size );            
            board.getFigures().addPointControl((board.getCar(22,11)+desHor*xk)/size,(board.getCar(22,12)+desVer*yk)/size );            
        }
        if(expression.get(count)==24){          //C
            board.getFigures().addCurve((board.getCar(23,1)+desHor*(xk))/size,(board.getCar(23,2)+desVer*yk)/size
            ,(board.getCar(23,3)+desHor*(xk))/size,(board.getCar(23,4)+desVer*yk)/size
            ,(board.getCar(23,5)+desHor*(xk))/size,(board.getCar(23,6)+desVer*yk)/size
            ,(board.getCar(23,7)+desHor*(xk))/size,(board.getCar(23,8)+desVer*yk)/size,count,pos);
            board.getFigures().addPointControl((board.getCar(23,1)+desHor*xk)/size,(board.getCar(23,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(23,7)+desHor*xk)/size,(board.getCar(23,8)+desVer*yk)/size );
            
        }
        if(expression.get(count)==25){            //D 
            board.getFigures().addLine((board.getCar(24,1)+desHor*(xk))/size,(board.getCar(24,2)+desVer*yk)/size
                    ,(board.getCar(24,3)+desHor*(xk))/size,(board.getCar(24,4)+desVer*yk)/size,count,pos);
            board.getFigures().addCurve((board.getCar(24,5)+desHor*(xk))/size,(board.getCar(24,6)+desVer*yk)/size
                    ,(board.getCar(24,7)+desHor*(xk))/size,(board.getCar(24,8)+desVer*yk)/size
                    ,(board.getCar(24,9)+desHor*(xk))/size,(board.getCar(24,10)+desVer*yk)/size
                    ,(board.getCar(24,11)+desHor*(xk))/size,(board.getCar(24,12)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(24,1)+desHor*xk)/size,(board.getCar(24,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(24,3)+desHor*xk)/size,(board.getCar(24,4)+desVer*yk)/size );            
        }
        if(expression.get(count)==26){ 
            board.getFigures().addLine((board.getCar(25,1)+desHor*(xk))/size,(board.getCar(25,2)+desVer*yk)/size
                    ,(board.getCar(25,3)+desHor*(xk))/size,(board.getCar(25,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(25,5)+desHor*(xk))/size,(board.getCar(25,6)+desVer*yk)/size
                    ,(board.getCar(25,7)+desHor*(xk))/size,(board.getCar(25,8)+desVer*yk)/size,count,pos+1);
            board.getFigures().addLine((board.getCar(25,9)+desHor*(xk))/size,(board.getCar(25,10)+desVer*yk)/size
                    ,(board.getCar(25,11)+desHor*(xk))/size,(board.getCar(25,12)+desVer*yk)/size,count,pos+2);
            board.getFigures().addLine((board.getCar(25,13)+desHor*(xk))/size,(board.getCar(25,14)+desVer*yk)/size
                    ,(board.getCar(25,15)+desHor*(xk))/size,(board.getCar(25,16)+desVer*yk)/size,count,pos+3);
            board.getFigures().addPointControl((board.getCar(25,1)+desHor*xk)/size,(board.getCar(25,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(25,3)+desHor*xk)/size,(board.getCar(25,4)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(25,7)+desHor*xk)/size,(board.getCar(25,8)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(25,11)+desHor*xk)/size,(board.getCar(25,12)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(25,9)+desHor*xk)/size,(board.getCar(25,10)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(25,15)+desHor*xk)/size,(board.getCar(25,16)+desVer*yk)/size );            
        }
        if(expression.get(count)==27){
            board.getFigures().addLine((board.getCar(25,1)+desHor*(xk))/size,(board.getCar(25,2)+desVer*yk)/size
                    ,(board.getCar(25,3)+desHor*(xk))/size,(board.getCar(25,4)+desVer*yk)/size,count,pos);
            board.getFigures().addLine((board.getCar(25,5)+desHor*(xk))/size,(board.getCar(25,6)+desVer*yk)/size
                    ,(board.getCar(25,7)+desHor*(xk))/size,(board.getCar(25,8)+desVer*yk)/size,count,pos+1);
            board.getFigures().addLine((board.getCar(25,9)+desHor*(xk))/size,(board.getCar(25,10)+desVer*yk)/size
                    ,(board.getCar(25,11)+desHor*(xk))/size,(board.getCar(25,12)+desVer*yk)/size,count,pos+2);
            board.getFigures().addPointControl((board.getCar(25,1)+desHor*xk)/size,(board.getCar(25,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(25,3)+desHor*xk)/size,(board.getCar(25,4)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(25,7)+desHor*xk)/size,(board.getCar(25,8)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(25,11)+desHor*xk)/size,(board.getCar(25,12)+desVer*yk)/size );
        }     
        if(expression.get(count)==28){
            board.getFigures().addCurve((board.getCar(27,1)+desHor*(xk))/size,(board.getCar(27,2)+desVer*yk)/size
                    , (board.getCar(27,3)+desHor*(xk))/size,(board.getCar(27,4)+desVer*yk)/size
                    , (board.getCar(27,5)+desHor*(xk))/size,(board.getCar(27,6)+desVer*yk)/size
                    , (board.getCar(27,7)+desHor*(xk))/size,(board.getCar(27,8)+desVer*yk)/size,count,pos);
            board.getFigures().addCurve((board.getCar(27,9)+desHor*(xk))/size,(board.getCar(27,10)+desVer*yk)/size
                    , (board.getCar(27,11)+desHor*(xk))/size,(board.getCar(27,12)+desVer*yk)/size
                    , (board.getCar(27,13)+desHor*(xk))/size,(board.getCar(27,14)+desVer*yk)/size
                    , (board.getCar(27,15)+desHor*(xk))/size,(board.getCar(27,16)+desVer*yk)/size,count,pos+1);
            board.getFigures().addPointControl((board.getCar(27,1)+desHor*xk)/size,(board.getCar(27,2)+desVer*yk)/size );
            board.getFigures().addPointControl((board.getCar(27,7)+desHor*xk)/size,(board.getCar(27,8)+desVer*yk)/size );
            
        }
        //agrega las posiciones de los numeros del denominador en la division actual
        if (buttonDivPress ) {
            if(expression.get(count)!=12 && expression.get(count)!=88){
                denominator.add(count);                              
                denominators();
            }
        }
        System.out.println("Guardando: "+xk+", "+yk);
        allxdec.add(xk);
        allydec.add(yk);
        if(expression.get(count)!=88)
            xk = xk+1; 
        count++;
                       
        System.out.println("valor xk: "+valueOfXk);
        if(yk>0){
            System.out.println("write1");            
            for (int i = valueOfXk.size()-1; i >= 0; i--) {                  
                if (valueOfXk.get(i)!=100){
                    if (xk-1>valueOfXk.get(i)){    
                        int aux=valueOfXk.get(i); 
                        allxdiv[i][aux+1]=xk-1;
                        allydiv[i][aux+1]=i;
                        board.getFigures().addLineDiv((board.getCar(12,1)+desHor*(xk-1))/size,(board.getCar(12,2)+desVer*(i))/size,
                             (board.getCar(12,3)+desHor*(xk-1))/size,(board.getCar(12,4)+desVer*(i))/size,i,aux+1);                                          
                        valueOfXk.set(i, xk-1);
                    }                                 
                }              
            }
        }      
        if (xk>xkLast){
            xkLast=xk; 
        }             
        board.repaint();
    }
    /**
     * Metodo que agrega los listener a los respectivos botones de los caracteres
     * @author Javiera
     */
    public void listener(){        
        buttons.get(0).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(0,"0");
            }
        });
        buttons.get(1).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(1,"1");                
            }
        });
        buttons.get(2).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(2,"2");             
            }
        });
        buttons.get(3).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(3,"3"); 
                
            }
        });
        buttons.get(4).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(4,"4"); 
            }
        });
        buttons.get(5).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(5,"5"); 
            }
        });
        buttons.get(6).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(6,"6"); 
            }
        });
        buttons.get(7).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(7,"7"); 
            }
        });
        buttons.get(8).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(8,"8"); 
            }
        });
        buttons.get(9).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(9,"9"); 
            }
        });
        buttons.get(10).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(10, "+");
            }
        });        
        buttons.get(11).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(11, "-");   

            }
        });
        buttons.get(12).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {     
                buttonDivPress=true;                
                eventButton(12, "/");              
            }
        });
        buttons.get(13).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(13, "*");        
            }
        });
        buttons.get(14).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(14, "(");
                numberParen++;                
            }
        });
        buttons.get(15).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(15, ")");
                parentheses=true;                                            
            }       
        });
        buttons.get(16).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonPointPress){
                    board.orderDrawPoint(false);
                    buttonPointPress=false;
                    board.repaint();
                }
                else{
                    board.orderDrawPoint(true);
                    buttonPointPress=true;
                    board.repaint();
                }             
            }
        });
        buttons.get(17).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {   
                eventButton(88, "");
                int aux=0;               
                if(yk>0){
                    yk--;
                    buttonDivPress=false;                    
                }
                if(valueOfXk.size()>0){                                                             
                    if (valueOfXk.get(posY_Matrixdiv-1)!=100){                        
                        xk=valueOfXk.get(posY_Matrixdiv-1)+1;                                               
                        valueOfXk.set(posY_Matrixdiv-1, 100);
                    }
                    for (int i = 0; i < valueOfXk.size(); i++) {
                        if (valueOfXk.get(i)==100)
                            aux++;
                        if (aux==valueOfXk.size())
                            xk=xkLast;                       
                    }                                                                         
                }

                denominator.clear();
            }
        });
        buttons.get(18).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (size>=4)
                    size=size-1;
                else
                    size=10;
                if(based2){//resize en base2
                    ArrayList<Integer> saveallx = allxdec;
                    ArrayList<Integer> saveally= allydec; 
                    allxdec = new ArrayList<>();
                    allydec = new ArrayList<>();
                    repaintFormule(saveallx, saveally);                                        
                }
                else if(based16){//resize en base16
                    ArrayList<Integer> saveallx = allxdec;
                    ArrayList<Integer> saveally= allydec; 
                    allxdec = new ArrayList<>();
                    allydec = new ArrayList<>();
                    repaintFormule(saveallx, saveally);
               
                }
                else{//resize en base10
                    ArrayList<Integer> saveallx = allxdec;
                    ArrayList<Integer> saveally= allydec; 
                    allxdec = new ArrayList<>();
                    allydec = new ArrayList<>();
                    repaintFormule(saveallx, saveally);
                    
                }
            }
            
        });        
        buttons.get(19).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                ArrayList<Integer> listaTransformada= new ArrayList();
                ArrayList<Double> formula = new ArrayList<>(); 
                //expression2 = expression;
                expression2.add(0, 14);
                expression2.add(15);
                //transforma la expresion a decimal, sin importar la base en la que este
                listaTransformada = decimal(expression2);              
                //reduce signos repetidos de suma y resta en la expresion
                listaTransformada = reducirFormula(listaTransformada); 
                //se valida que la formula ingresada este correctamente escrita
                if(validarParentesis(listaTransformada) && validarSignos(listaTransformada)){                   
                    formula = ArrayIntToDouble(listaTransformada);                                            
                    resultado = calcular(ArrayIntToDouble(listaTransformada));
                    int resultadoRedondeado = (int)(Math.round(resultado));                    
                    ArrayList<Integer> resultadolista= new ArrayList<>();
                    resultadolista=digitos(resultadoRedondeado);                         
                    if(based2){                                         
                        listTextbox=atexto(binario(ArrayDoubleToInt(formula)));
                        resultadolista= binario(resultadolista);   
                        listTextbox+=" = ";    
                        String listaderesultado;
                        listaderesultado=atexto(resultadolista); 
                        listTextbox+=listaderesultado;
                    }
                    else if(based16){
                        listTextbox=atexto(hexadecimal(ArrayDoubleToInt(formula)));
                        resultadolista=hexadecimal(resultadolista); 
                        listTextbox+=" = ";      
                        listTextbox+=String.valueOf(resultadolista); 
                    }      
                    else{
                        listTextbox=atexto(ArrayDoubleToInt(formula));
                        listTextbox+=" = ";      
                        listTextbox+=String.valueOf(resultado);                                                              
                    }
                    TextBox.setText(listTextbox);   
                }
                else{
                    String mensaje="Error de sintaxis";
                    TextBox.setText(mensaje);
                    System.out.println("Error de sintaxis");
                }

            }
        });
        buttons.get(20).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                eventButton(17, "sen");                     
            }
        });
        buttons.get(21).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                //18 representa a cos
                eventButton(18, "cos");                                
            }
        });
        buttons.get(22).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                //19 representa a tan
                eventButton(19, "tan");                    
            }
        });
        buttons.get(23).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                //20 representa a potencia
                eventButton(20, "^");      
            }
        });  
        buttons.get(24).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(21, "-");        
            }
        });
        buttons.get(25).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                eventButton(16, "!");
            }
        });
        buttons.get(26).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(22, "A");
            }
        });
        buttons.get(27).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(23, "B");                
            }
        });
        buttons.get(28).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(24, "C");
            }
        });
        buttons.get(29).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(25, "D");
            }
        });
        buttons.get(30).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(26, "E");
            }       
        });
        buttons.get(31).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eventButton(27, "F");
            }
        });
        buttons.get(32).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {                
                eventButton(28, "°");
            }
        });
    }
    /**
     * repaint formula 
     * @param allx
     * @param ally 
     */
    public void repaintFormule(ArrayList<Integer> allx, ArrayList<Integer> ally){
        posY_Matrixdiv=0;
        buttonDivPress=false;
        resizing=true;             
        xk=1;
        yk=0;
        count=0;
        board.getFigures().setPtosControl();    
        board.getFigures().setCurves(new Curve[140][10]);
        board.getFigures().setLines(new Line[140][10]);
        for (int i = 0; i < expression.size(); i++) {    
            System.out.println("reescribiendo en: "+xk+","+yk);
            xk=allx.get(i);
            yk=ally.get(i);
            write();
        }
        board.getFigures().saveLinesDiv();
        board.getFigures().setLinesDiv(new Line[25][30]);
        for (int i = 0; i < board.getFigures().getLinesDivSave().length; i++) {
            for (int j = 0; j < board.getFigures().getLinesDivSave()[i].length; j++) {
                if(board.getFigures().getLinesDivSave()[i][j]!=null){                         
                    int newxk= allxdiv[i][j];
                    int newyk= allydiv[i][j];                           
                    board.getFigures().addLineDiv((board.getCar(12,1)+desHor*(newxk))/size,(board.getCar(12,2)+desVer*(newyk))/size,
                    (board.getCar(12,3)+desHor*(newxk))/size,(board.getCar(12,4)+desVer*(newyk))/size,i,j);

                }
            }
        }
    }
    /**
     * Metodo que dibuja la linea divisora segun el numerador, reconoce parentesis 
     * y composiciones de parentesis.
     * @param limite parametro que define hasta donde analizar la lista expresion,
     *               para evitar que lea los caracteres anteriores a la division.
     * @autor Camilo
     */
    public void defDivision(int limite){        
        /*variables que representan el largo de la linea divisora*/
        int largolinea=0;
        int auxnumpar = numberParen;
        /*ciclo que recorre la lista expresion*/        
        if (parentheses){          
            for (int i = expression.size()-1; i>=0; i--) {
                if (expression.get(i)==14){
                                       
                    if (auxnumpar==1){                       
                        limite=i;
                        firstPar=true;
                        
                    }
                    /*Cuando se encuentra un parentesis cerrado pero no es el ultimo haz..*/
                    else{
                        auxnumpar--;                                           
                    }               
                }
            }           
        }       
        for (int i = expression.size()-2; i >= limite; i--) {
            /*Si el caracter es igual a un parentesis haz..*/          
            if (!parentheses){
                if (((expression.get(i)>=10 && expression.get(i)<=14)) || expression.get(i)==20){
                    funtionLinesDiv(largolinea, xk-largolinea, largolinea);
                    i=-1;                                
                }                
                /* Si la division se hace sobre el primer termino ingresado haz..
                 @autor Marcelo 
                */
                if(i==limite){                    
                    funtionLinesDiv(largolinea+1, xk-largolinea-1, largolinea+1);                   
                }
            }
            /*Si existen parentesis en el numerador haz..*/            
            if(parentheses){                
                if (expression.get(i)==12)
                    yk++;               
                if (expression.get(i)==14){                   
                    if (numberParen==1){
                        System.out.println("largolinea= "+endPar+" - "+(startPar));                       
                        //largolinea=limite;
                        largolinea=endPar-startPar+1;                       
                        funtionLinesDiv(largolinea, xk-largolinea, largolinea);
                        numberParen=0;
                        i=-1;                                               
                    }                    
                    else{
                        numberParen--;                                            
                    }
                }
                
            }            
            /*Siempre que se recorra la lista expresion antes de un valor de detencion se
            alarga la linea divisora en 1*/
            largolinea++;
        }
        parentheses=false;
    }
    /**
     * Metodo usado para crear una linea divisora
     * @param lenghtLine Largo de la linea en terminos de xk
     * @param xk Posicion donde comenzar a crear la linea
     * @param aux variable auxilar que guarda el largo de la linea con el fin de 
     *            restarlo al xk final y volver a la posicion xk de inicio del denominador.
     * @autor Camilo
     */
    public void funtionLinesDiv(int lenghtLine, int xk,int aux){        
        int posy=0;
        while(lenghtLine>0){           
            if (lenghtLine==1){
                lastPtoDiv=xk;
                valueOfXk.add(lastPtoDiv);            
            }
            System.out.println("FunctionLinesDiv");
            allxdiv[posY_Matrixdiv][posy]=xk;
            allydiv[posY_Matrixdiv][posy]=yk;
            board.getFigures().addLineDiv((board.getCar(12,1)+desHor*xk)/size,(board.getCar(12,2)+desVer*yk)/size,
               (board.getCar(12,3)+desHor*xk)/size,(board.getCar(12,4)+desVer*yk)/size,posY_Matrixdiv,posy);
            
            xk++;                        
            lenghtLine--;
            posy++;
        }
        /*nuevos valores de yk y xk para escribir el denominador*/
        this.yk++;    
        this.xk=this.xk-aux-1;
        posY_Matrixdiv++;
    }
    /**
     * Metodo que agranda la linea de division de acuerdo a los denominadores
     * @author Javiera
     */
    public void denominators(){   
        int aux=yk;
        aux--;
        for(int i=0; i<denominator.size();i++){
            if(i==1){                              
                if (lastPtoDiv<xk){                   
                    lastPtoDiv=xk;
                    valueOfXk.remove(valueOfXk.size()-1);
                    valueOfXk.add(lastPtoDiv);                  
                    board.getFigures().delPtoControlDiv();
                    board.getFigures().addPointControlDiv((board.getCar(12,3)+desHor*(lastPtoDiv))/size
                            ,(board.getCar(12,4)+desVer*(yk-1))/size);
                    System.out.println("denominators");
                    board.getFigures().addLineDiv((board.getCar(12,1)+desHor*xk)/size,(board.getCar(12,2)+desVer*aux)/size,
                    (board.getCar(12,3)+desHor*xk)/size,(board.getCar(12,4)+desVer*aux)/size,posY_Matrixdiv-1,xk);   
                }
            }                        
        }        
       //buttonDivPress=false;
    }
    /**
     * Metodo que agrega el numero n a la lista expression y el string car al text box
     * @param n
     * @param car 
     * @autor Javiera
     */
    public void eventButton(int n, String car){
        listTextbox += car;             
        expression.add(n);  
        expression2.add(n);      
        TextBox.setText(listTextbox);
        System.out.println("Expresion dib: "+expression);
        System.out.println("Expresion cal: "+expression2);
        write();
    }
    public void reset(){
        xk=1;
        yk=0;
        posY_Matrixdiv=0;
        count=0;
        limit=0;
        expression.clear();
        expression2.clear();
        valueOfXk.removeAll(valueOfXk);
        buttonPointPress=false;
        buttonDivPress=false;
        buttonDivPress=false;
        parentheses=false;
        numberParen=0;
        startPar=0;
        endPar=0;
        xkLast=0;
        listTextbox="";
        TextBox.setText(listTextbox);       
        board.getFigures().setCurves(new Curve[140][10]);
        board.getFigures().setLines(new Line[140][10]);
        board.getFigures().setLinesDiv(new Line[25][30]);
        board.getFigures().setPtosControl();
    }
    /**
     * Metodo recursivo que detecta los parentesis y llama al metodo subcalcular para que los calcule, con el fin
     * de reeplazar toda esa operacion matematica entre parentesis por su respectivo resultado. Al final, se tendra una formula 
     * lineal, sin parentesis que sera calculada con el mismo mecanismo que las anteriores subformulas.
     * El resultado se agrega al String del TextBox.
     * @param formula formula matematica a calcular
     * @autor Camilo
     */
    public Double calcular(ArrayList<Double> formula){
        System.out.println("*************CALCULAR***************");
        Double subresultado= null;
        int indexParOpen = -1;
        ArrayList<Double> subformula = new ArrayList<>();              
        for (int i = 0; i < formula.size(); i++) { 
            double aux = formula.get(i);
            if(aux==14){
                indexParOpen=i;
            }
            if(aux==15){             
                for (int j = indexParOpen+1; j < i; j++) {
                    if(formula.get(j)==15){
                        formula.remove(j);
                        break;
                    }
                    subformula.add(formula.get(j));
                    formula.remove(j);
                    j--;                 
                }
                subresultado = subcalculo(subformula); 
                System.out.println("-Calcular- subresultado: "+subresultado);
                double aux2=subresultado;
                //diferenciar el resultado de los identificadores para cada operacion matematica
                
                if (aux2==10.0 || aux2==11.0 || aux2==12.0 || aux2==13.0 || aux2==14.0 
                                 || aux2==15.0 || aux2==16.0 || aux2==17.0 || aux2==18.0 
                                 || aux2==19.0 || aux2==20.0 || aux2==21.0) {
                    System.out.println("ES SIGNO! ");
                    System.out.println("-calcular- dig: "+digitos(subresultado.intValue()));
                    ArrayList<Double> subresList = ArrayIntToDouble(digitos(subresultado.intValue()));
                    System.out.println("-calcular- subreslist: "+subresList);
                    formula.remove(indexParOpen);
                    formula.addAll(indexParOpen, subresList);
                    System.out.println("-calcular- new formula: "+formula);
                    subresultado = calcular(formula);
                }
                else{
                    System.out.println("NO ES SIGNO! ");
                    formula.set(indexParOpen, Double.parseDouble(subresultado.toString()));
                    subresultado = calcular(formula);       
                }               
            }            
        }
        if (indexParOpen==-1) {
            subresultado = subcalculo(formula);                   
            Double resultado = Math.round((subresultado)*1000d)/1000d;
            return resultado;
        }
        
        System.out.println("-Calcular- retornando normal..");
        return subresultado;
    }
    /**
     * Metodo que recibe una subformula para resolverla y retornarla. Puede recibir las siguientes subformulas:
     * -Seno, coseno o tangentes que las resuelve y retorna el valor.
     * -Subformulas dentro de parentesis.
     * -Potencias y factoriales.
     * -Operaciones matematica elementales
     * -Combinacion de toda las anteriores SIN PARENTESIS.
     * @param subformula
     * @return Object que representa el resultado de la subformula
     * @autor Camilo
     */
    public Double subcalculo(ArrayList<Double> subformula){
        System.out.println("*************SUBCALCULO***************");
        System.out.println("-Subcalculo- subformula antes: "+subformula);       
        ScriptEngineManager manager=new ScriptEngineManager(); 
        ScriptEngine engine =  manager.getEngineByName("js");        
        for (int i = 0; i < subformula.size(); i++) {
            String numInteres="";
            double resultado;
            int index=-1;                                     
            if(subformula.get(i)==16){//Resolver factorial y reemplazar resultado en lista expression
                Object datos[] = funcionPostParentesis(subformula, i, index);
                resultado=1;
                for (int j = 1; j <= Double.parseDouble(datos[0].toString()); j++) {
                    resultado*=j;
                }
                subformula.add(0.0);
                subformula.set((int)datos[1],resultado); 
                break;
            }
            if (subformula.get(i)==20){//Resolver potencia y reemplazar el resultado en la lista expression          
                Object datos[] = funcionPostParentesis(subformula, i, index);             
                resultado=Math.pow(Double.parseDouble(datos[0].toString()), subformula.get((int)datos[1]));              
                subformula.set((int)datos[1],resultado); 
                break;
            }
            if(subformula.get(i)==21){//Transformar numero a negativo
                numInteres=funcionPreParentesis(subformula, i);
                resultado=Double.parseDouble(numInteres)*-1;
                subformula.set(i, resultado);                       
            }
            if (subformula.get(i)==17){//Resolver seno y reemplazar resultado en la lista expression
                numInteres=funcionPreParentesis(subformula, i); 
                resultado= Math.sin(Math.toRadians(Double.parseDouble(numInteres))) ;                          
                subformula.set(i,resultado);       
            }                  
            if (subformula.get(i)== 18){//Resolver coseno y reemplazar el resultado en la lista expression                   
                numInteres=funcionPreParentesis(subformula, i);
                resultado= Math.cos(Math.toRadians(Double.parseDouble(numInteres))) ;                          
                subformula.set(i,resultado); 
            }
            if (subformula.get(i)== 19){//Resolver tangente y reemplazar el resultado en la lista expression                    
                numInteres=funcionPreParentesis(subformula, i);
                resultado= Math.tan(Math.toRadians(Double.parseDouble(numInteres))) ;                          
                subformula.set(i,resultado);     
            }          
        }        
        try {
            System.out.println("-Subcalculo- subfor despues: "+subformula);
            Object result = engine.eval(subformuleToString(subformula)); 
            System.out.println("-subcalculo- return: "+result);
            return (double)result;
            
        } catch(ScriptException se) {
            System.out.println("Script Exception");
            return null; }                  
    }
    /**
     * Metodo que resuelve funciones que, sintacticamente, se escriben antes del valor. Por ejemplo:
     * sen(45), cos(1), tan(20), -2. Los signos e identificadores de estas funciones matematicas se escriben antes que el valor.
     * @param subformula subformula a resolver
     * @param i posicion de la funcion en la lista subformula
     * @return String que representa el numero de interes para aplicar la funcion matematica.
     * @autor Camilo
     */
    public String funcionPreParentesis(ArrayList<Double> subformula, int i){
        String numInteres="";
        for (int j = i+1; j < subformula.size(); j++) {                       
            double aux=subformula.get(j);
            if (aux==13||aux==12||aux==11||aux==10 || aux==16) {                                                                              
                break;
            }
            else if(aux==15){
                subformula.remove(j);
                break;
            }
            else if(aux==14){
                subformula.remove(j);
                j--;
            }
            else{                               
                numInteres+=subformula.get(j).intValue();
                subformula.remove(j);
                j--;
            }
        }
        return numInteres;
    }
    /**
     * Metodo que resuelve funciones que, sintacticamente, se escribe despues del valor. Por ejemplo: 
     * 24! y 2^2, factorial y potencia respectivamente. El signo de la funcion va despues del valor
     * @param subformula subformula a resolver
     * @param i posicion de la funcion factorial o potencia en la lista de subformula.
     * @param index indice que ayuda a setear el resultado en la lista subformula.
     * @return Object[] una lista de objetos que retorna el numero de interes (valor al que se le aplica la funcion deseada) y el index.
     * @autor Camilo
     */
    public Object[] funcionPostParentesis(ArrayList<Double> subformula, int i, int index){
        String numInteres=""; 
        ArrayList<Double> saveSubFormula = new ArrayList<>();
        for (int j = 0; j < subformula.size(); j++) {
            saveSubFormula.add(subformula.get(j));
        }
        System.out.println("-FposPar- suformula: "+subformula);
        System.out.println("i: "+i);
        int count=0;
        for (int j = i; j >= 0; j--) {
            
            double aux=subformula.get(j);                             
            if (aux==13||aux==12||aux==11||aux==10) {                                                                       
                break;
            }
            else if(aux==14){
                subformula.remove(j);
                index=j;
                break;
            }
            else if(aux==15 || aux==20 || aux==16){
                subformula.remove(j);                         
            } 
            else{
                count++;
                numInteres+=subformula.get(j).toString();                            
                subformula.remove(j);                        
            }                                             
            index=j;                                              
        }
        System.out.println("save: "+saveSubFormula);
        numInteres = doubleToString(saveSubFormula.subList(i-count, i));
        System.out.println("-FposPar- numInteres: "+numInteres);
        System.out.println("-FposPar- index: "+index);
        Object datos[]= {numInteres,index};
        return datos;
    }
    /**
     * Metodo que convierte una subformula de tipo ArrayList en un String, se agregan signos +,-,/,* , si es que existen,
     * correspondientes a las operaciones matematicas elementales. Si el ArrayList no contiene signos significa que es solo un mumero por lo 
     * que solo lo retorna como string.
     * @param subformula ArrayList que representa la subformula a convertir.
     * @return String que representa la subformula
     * @autor Camilo
     */
    public String subformuleToString(ArrayList<Double> subformula){
        System.out.println("*************SUFORMULATOSTRING***************");
        System.out.println("-subforulatostring- toSring "+subformula);
        String listSubFormula="";//lista subformula convertida a String
        ArrayList<Integer> pos_Signos=new ArrayList<>();//Lista de las posiciones de los operandos en la subformula
        //Ciclo que guarda las posiciones de los operandos
        for (int i = 0; i < subformula.size(); i++) {
            double aux = subformula.get(i);
            if(aux==10.0 || aux==11.0 || aux==12.0 || aux==13.0)               
                pos_Signos.add(i);          
        }        
        int start=0, end=subformula.size();//inicio y termino, limites de la lista subformula
        boolean firstNumber=false;//variable que representa el primer numero de la subformula y cambia true si este se pasa al string.       
        //Si subformula no es solamente un numero, es decir, que contiene signos. 
        if (pos_Signos.size()>=1){
            System.out.println("-subforulatostring- ES UNA FORMULA!");
            //Ciclo que recorre la lista pos_Signos, dependiendo de cada signo se convertira y agregara el numero de la DERECHA!
            //El primer numero de la subformula se agrega primero que todos, y luego se van agregando los demas junto con los signos correspondientes
            for (int i = 0; i < pos_Signos.size(); i++) {
                //primer numero
                if(!firstNumber){                    
                    listSubFormula+=doubleToString(subformula.subList(start, (pos_Signos.get(i))));
                    firstNumber=true;
                }
                //primer signo, agrega el numero de la derecha (Siempre y cuando existan mas signos)
                if (i==start && pos_Signos.size()>1){
                    listSubFormula+=addSigno(subformula.get(pos_Signos.get(i)));  //Agrega el signo correspondiente
                    //Convierte el numero de la derecha a string
                    listSubFormula+=doubleToString(subformula.subList(pos_Signos.get(i)+1, pos_Signos.get(i+1)));          
                }
                //Ultimo signo, agrega el numero de la derecha y termina el ciclo con el break;
                else if (i == pos_Signos.size()-1){
                    listSubFormula+=addSigno(subformula.get(pos_Signos.get(i)));                
                    listSubFormula+=doubleToString(subformula.subList(pos_Signos.get(i)+1, end));              
                    break;
                }
                //Todos los demas signos despues del primero y antes del ultimo, agrega el numero de la derecha de cada signo. 
                else{
                    listSubFormula+=addSigno(subformula.get(pos_Signos.get(i)));               
                    listSubFormula+=doubleToString(subformula.subList(pos_Signos.get(i)+1, pos_Signos.get(i+1)));                            
                }               
            }       
            System.out.println("-subforulatostring- subform to string :"+listSubFormula);
            return listSubFormula; //Retorna el String completo de la subformula. 
        }
        else{         
            System.out.println("-subforulatostring- ES SOLO UN NUMERO! ");
            listSubFormula+=doubleToString(subformula);
            System.out.println("-subforulatostring- subform to string :"+listSubFormula);
            return listSubFormula;
        }
    }
    /**
     * Metodo que convierte la lista recibida, que representa un numero, en el string de ese numero. Por ejemplo, 
     * si recibe la lista [1.0 , 3.0, 5.0] retorna el string "135", correspondiente al numero 135.
     * @param number Lista Double que representa el numero.
     * @return String que representa el numero deseado. 
     * @autor Camilo
     */
    public String doubleToString(List<Double> number){ 
        System.out.println("*************DOUBLETOSTRING***************");
        String string_number= "";//String del numero
        int aux=number.size()-1;//tamaño del numero
        System.out.println("-doubletostring- numero a string: "+number+" size: "+aux);
        double parcial_number=0;//Double usado para una suma parcial de centenas, decenas, unidades etc.
        for(int k=0; k<number.size();k++){ //Ciclo que recorre el numero
            parcial_number+=Math.pow(10,aux)*number.get(k);//convierte cada valor del numero a centena, decena, unidad o como corresponda y lo suma.
            aux--;//le resta una la tamaño del numero, que en este caso se usa como exponente para las potencias de 10
            System.out.println("-doubletostring- suma parcial: "+parcial_number);
        }
        string_number+=parcial_number;//Se agrega el numero al string y se retorna.
        System.out.println("-doubletostring- string de numero: "+string_number);
        return string_number;
    }
    /**
     * Metodo identifica el operando observado.
     * @param signo Double que representa el codigo del signo; 
     * @return String del signo correspondiente o null si no encuentra el signo.
     * @autor Camilo.
     */
    public String addSigno(Double signo){
        String signos[]={"+","-","/","*"};//Lista de signos        
        for (int s = 10; s < 14; s++) {
            if(signo==s){              
                return signos[s-10];                    
            }
        }
        return null;
    }
    /**
     * Metodo que transforma una lista de enteros a una lista de doubles.
     * @param lista Lista de enteros
     * @return Lista de doubles.
     * @autor Camilo
     */
    public ArrayList<Double> ArrayIntToDouble(ArrayList<Integer> lista){
        ArrayList<Double> listDouble = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            listDouble.add((double)lista.get(i));
        }
        return listDouble;
    }
    /**
     * Metodo que transforma una lista de doubles a una lista de enteros
     * @param lista lista de doubles
     * @return lista de enteros
     */
    public ArrayList<Integer> ArrayDoubleToInt(ArrayList<Double> lista){
        ArrayList<Integer> listInt = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            listInt.add((lista.get(i).intValue()));
        }
        return listInt;
    }
}
