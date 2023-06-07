/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package noteestrelles;
import java.awt.*; //Abstract Window Toolkit - una de las primeras herramientas, ya casi no se usa
//Depende de la plataforma (SO) (en cuanto a controles)
import javax.swing.*;//javax significa extension - Independente de la plataforma
import java.awt.event.*;//accion con el mouse, este se pone porque java.swing tambien tiene un event
import static java.lang.System.exit;
import javax.swing.JOptionPane;//para los mensajes
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;//para el boton salir
/**
 *
 * @author victor
 */
public class Nivel3 extends JFrame implements KeyListener{//se implementan algunas interfaces que nos permiten interactuar por medio de botones, etc
    //atributos
    static String color;
    static String dificultad;
    static int dif;
    JPanel panel;
    static Nivel3 marco;//para crear la ventana del tipo de la clase
    //variables para las imagenes
    static ImageIcon araña;
    static ImageIcon niv;
    static ImageIcon vic;
    static ImageIcon der;
    Timer fondo;//objeto de la clase Timer
    public Nivel3(){//constructor vacio
        
    }
    public Nivel3(int d, String c){//constructor
        //se asignan los valores
        if(d==1)dificultad="/imagenes/nivel3facil.png";//si la dificultad es facil
        if(d==2)dificultad="/imagenes/nivel3normal.png";//si la dificultad es normal
        if(d==3)dificultad="/imagenes/nivel3dificil.png";//si la dificultad es dificil
        dif=d;
        color=c;
        //variables para las imagenes
        araña = new ImageIcon(getClass().getResource(color));//para guardar la imagen del cubo
        niv = new ImageIcon(getClass().getResource(dificultad));//para el fondo (nivel)
        vic = new ImageIcon(getClass().getResource("/imagenes/victoria.png"));//imagen de victoria
        der = new ImageIcon(getClass().getResource("/imagenes/derrota.png"));//imagen de derrota
    }
    public static void nivel3(){
        marco = new Nivel3();//se crea el objeto
        marco.setSize(800, 800);// metodo de JFrame, tamaño de la ventana en px
        marco.setTitle("Nivel 3");// metodo de JFrame
        marco.setLocationRelativeTo(null);//para centrar la ventana
        marco.setVisible(true);//mostrar la ventana
        marco.araña();//se añaden las cosas a la ventana (panel)
        
    }
    public void araña(){
        setDefaultCloseOperation(EXIT_ON_CLOSE); //EXIT_ON_CLOSE Es una enumeracion, es para que deje de correr el programa (termina la ejecucion al cerrar la ventana)
        Container ventana = getContentPane();

        ventana.setLayout(new FlowLayout());//ADMINISTRADOR DE CONTENIDO, flowlayout los acomoda como van llegando donde caben
        //Colocar los objtos en la ventana.
        //PANEL
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,700));
        panel.setBackground(Color.BLACK);//cambio de color
        ventana.add(panel);
        addKeyListener(this);//toda la ventana se suscribe al keylistener

        
        fondo = new Timer(18 , new ActionListener(){//cree un objeto de la clase Timer con un delay de 18 para que no vaya muy rapido
            public void actionPerformed(ActionEvent event){//me permite hacer una tarea repetidamente, tambien cumple una funcion similar a un hilo
                movimiento(araña,niv,vic,der);//se usa el metodo de movimiento del fondo
            }
        });
        fondo.start();//aqui se inicia el Timer el cual repetira el movimiento hasta que le de stop
        
        
    }
    //variable para la araña en el juego
    int posx=50;
    int posy=400;
    //variables para que el nivel se mueva
    int x=0;
    int y=100;
    //metodos para las teclas
    public void keyPressed(KeyEvent event){
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior
        int key = event.getKeyCode();
        if(key==32){//espacio
            teletransporte(papel,araña,niv,vic,der);//teletrasnsportacion de la araña
        }
        if(key==27){//si es la tecla escape el juego se pausara y se mostraran algunas opciones
            fondo.stop();//se detiene el nivel
            Object[] opciones = { "Continuar", "Menu Principal", "Salir"};//opciones del menu
            //se guarda laopcion en otro objeto
            int seleccionado=JOptionPane.showOptionDialog(null, "Escoge una opcion", "Pausa",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, opciones, opciones[0]);
            if(seleccionado==0){
                fondo.start();//se descongela el nivel
            }
            if(seleccionado==1){
                marco.dispose();//se quita la ventana para que al ir de nuevo a el menu principal no se cree una aparte
                NoteEstrelles jp4 = new NoteEstrelles();//se crea un objeto de la clase principal para volver al inicio
                jp4.juego();//se usa el metodo jugar para empezar de nuevo en el menu principal
            }
            if(seleccionado==2){
                //limpieza
                papel.setColor(Color.BLACK);//cambiar color
                papel.fillRect(0, 0, 800, 700);//imprimir rectangulo
                ImageIcon img4 = new ImageIcon(getClass().getResource("/imagenes/salir.png"));//imagen de despedida
                papel.drawImage(img4.getImage(), 80, 0,700,700, this);
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(600);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                exit(0);//terminar el programa
            }
        }
    }
    public void keyReleased(KeyEvent event){}
    
    public void teletransporte(Graphics papel, ImageIcon araña, ImageIcon niv, ImageIcon vic, ImageIcon der){//para la araña
        int cont=0;//contador de teletransportaciones
        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior
        if(cont==0){
            if(posy==400){posy=100;cont++;}//se cambia a la parte superior si esta en la inferior
        }
        if(cont==0){
            if(posy==100){posy=400;cont++;}//se cambia a la parte inferior si esta en la superior
        }
        papel.drawImage(araña.getImage(), posx, posy,50,50, this);//se pone la araña
        papel.drawImage(niv.getImage(), x, y,5000,350, this);//se pone el nivel
        papel.drawImage(araña.getImage(), posx, posy,50,50, this);//se pone la araña
        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior
        if(dif==1){//facil
            //coordenadas de los obstaculos
            if((x<=-300 && x>=-360 && posy>=360)||(x<=-665 && x>=-745 && posy<=120)||(x<=-1315 && x>=-1380 && posy>=360)||(x<=-1915 && x>=-1995 && posy<=120)||(x<=-2255 && x>=-2320 && posy>=360)||(x<=-2855 && x>=-2935 && posy<=120)||(x<=-3500 && x>=-3565 && posy>=360)||(x<=-4105 && x>=-4180 && posy<=120)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la araña para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==2){//normal
            //coordenadas de los obstaculos
            if((x<=-140 && x>=-200 && posy>=360)||(x<=-370 && x>=-435 && posy<=120)||(x<=-600 && x>=-665 && posy>=360)||(x<=-825 && x>=-940 && posy<=120)||(x<=-1105 && x>=-1170 && posy>=360)||(x<=-1350 && x>=-1420 && posy<=120)||(x<=-1585 && x>=-1652 && posy>=360)||(x<=-1807 && x>=-1930 && posy<=120)||(x<=-2090 && x>=-2155 && posy>=360)||(x<=-2485 && x>=-2560 && posy<=120)||(x<=-2720 && x>=-2830 && posy>=360)||(x<=-2990 && x>=-3065 && posy<=120)||(x<=-3240 && x>=-3310 && posy>=360)||(x<=-3470 && x>=-3550 && posy<=120)||(x<=-3705 && x>=-3817 && posy>=360)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la araña para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==3){//dificil
            //coordenadas de los obstaculos
            if((x<=-560 && x>=-645 && posy>=360)||(x<=-685 && x>=-770 && posy<=120)||(x<=-815 && x>=-900 && posy>=360)||(x<=-940 && x>=-1025 && posy<=120)||(x<=-1070 && x>=-1155 && posy>=360)||(x<=-1195 && x>=-1280 && posy<=120)||(x<=-1325 && x>=-1410 && posy>=360)||(x<=-1450 && x>=-1535 && posy<=120)||(x<=-2555 && x>=-2640 && posy>=360)||(x<=-2685 && x>=-2770 && posy<=120)||(x<=-2810 && x>=-2895 && posy>=360)||(x<=-2935 && x>=-3020 && posy<=120)||(x<=-3065 && x>=-3150 && posy>=360)||(x<=-3190 && x>=-3275 && posy<=120)||(x<=-3320 && x>=-3405 && posy>=360)||(x<=-3445 && x>=-3530 && posy<=120)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la araña para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(x<=-4230){//cuando llega a la meta
            fondo.stop();//se detiene el movimiento del fondo
            papel.drawImage(vic.getImage(), 0, 0,800,800, this);//se muestra la imagen de victoria
            try{//para parar el tiempo unos cuantos milisegundos
                Thread.sleep(400);
            }catch(InterruptedException e){
                System.out.println("Error"+e);
            }
            //se hace el proceso de regresar al menu principal
            marco.dispose();//se quita la ventana para que al ir de nuevo a el menu principal no se cree una aparte
            NoteEstrelles jp4 = new NoteEstrelles();//se crea un objeto de la clase principal para volver al inicio
            jp4.juego();//se usa el metodo jugar para empezar de nuevo en el menu principal
        }
    }
    
    
    
    public void movimiento(ImageIcon araña, ImageIcon niv, ImageIcon vic, ImageIcon der){//para la araña
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
        //imagenes
        papel.drawImage(araña.getImage(), posx, posy,50,50, this);//se pone la araña
        papel.drawImage(niv.getImage(), x, y,5000,350, this);//se dibuja el nivel
        papel.drawImage(araña.getImage(), posx, posy,50,50, this);//se pone la araña

        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior

        x-=5;//solo se recorre 5 para que al acercarse la nave a los obstaculos este acercamiento sea mas preciso
        papel.drawImage(araña.getImage(), posx, posy,50,50, this);//se pone la araña
        papel.drawImage(niv.getImage(), x, y,5000,350, this);//se pone el nivel
        papel.drawImage(araña.getImage(), posx, posy,50,50, this);//se pone la araña
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior
        if(x<=-4230){//cuando llega a la meta
            fondo.stop();//se detiene el movimiento del fondo
            papel.drawImage(vic.getImage(), 0, 0,800,800, this);//se muestra la imagen de victoria
            try{//para parar el tiempo unos cuantos milisegundos
                Thread.sleep(400);
            }catch(InterruptedException e){
                System.out.println("Error"+e);
            }
            //se hace el proceso de regresar al menu principal
            marco.dispose();//se quita la ventana para que al ir de nuevo a el menu principal no se cree una aparte
            NoteEstrelles jp4 = new NoteEstrelles();//se crea un objeto de la clase principal para volver al inicio
            jp4.juego();//se usa el metodo jugar para empezar de nuevo en el menu principal
        }
        if(dif==1){//facil
            //coordenadas de los obstaculos
            if((x<=-300 && x>=-360 && posy>=360)||(x<=-670 && x>=-745 && posy<=120)||(x<=-1315 && x>=-1380 && posy>=360)||(x<=-1915 && x>=-1995 && posy<=120)||(x<=-2255 && x>=-2320 && posy>=360)||(x<=-2855 && x>=-2935 && posy<=120)||(x<=-3500 && x>=-3565 && posy>=360)||(x<=-4105 && x>=-4180 && posy<=120)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la araña para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==2){//normal
            //coordenadas de los obstaculos
            if((x<=-140 && x>=-200 && posy>=360)||(x<=-365 && x>=-435 && posy<=120)||(x<=-600 && x>=-665 && posy>=360)||(x<=-825 && x>=-940 && posy<=120)||(x<=-1105 && x>=-1170 && posy>=360)||(x<=-1350 && x>=-1420 && posy<=120)||(x<=-1585 && x>=-1652 && posy>=360)||(x<=-1807 && x>=-1930 && posy<=120)||(x<=-2090 && x>=-2155 && posy>=360)||(x<=-2485 && x>=-2560 && posy<=120)||(x<=-2720 && x>=-2830 && posy>=360)||(x<=-2990 && x>=-3065 && posy<=120)||(x<=-3240 && x>=-3310 && posy>=360)||(x<=-3470 && x>=-3550 && posy<=120)||(x<=-3705 && x>=-3817 && posy>=360)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la araña para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==3){//dificil
            //coordenadas de los obstaculos
            if((x<=-560 && x>=-645 && posy>=360)||(x<=-685 && x>=-770 && posy<=120)||(x<=-815 && x>=-900 && posy>=360)||(x<=-940 && x>=-1025 && posy<=120)||(x<=-1070 && x>=-1155 && posy>=360)||(x<=-1195 && x>=-1280 && posy<=120)||(x<=-1325 && x>=-1410 && posy>=360)||(x<=-1450 && x>=-1535 && posy<=120)||(x<=-2555 && x>=-2640 && posy>=360)||(x<=-2685 && x>=-2770 && posy<=120)||(x<=-2810 && x>=-2895 && posy>=360)||(x<=-2935 && x>=-3020 && posy<=120)||(x<=-3065 && x>=-3150 && posy>=360)||(x<=-3190 && x>=-3275 && posy<=120)||(x<=-3320 && x>=-3405 && posy>=360)||(x<=-3445 && x>=-3530 && posy<=120)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la araña para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
    }
    public void keyTyped(KeyEvent event){}//este metodo lo puse nomas porque al implementar keylistsner era obligatorio ponerlo
}
