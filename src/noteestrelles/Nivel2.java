/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package noteestrelles;
import java.awt.*; //Abstract Window Toolkit - una de las primeras herramientas, ya casi no se usa
//Depende de la plataforma (SO) (en cuanto a controles)
import javax.swing.*;//javax significa extension - Independente de la plataforma
import java.awt.event.*;//accion con el mouse, este se pone porque java.swing tambien tiene un event
import static java.lang.System.exit;//para el boton salir
import javax.swing.JOptionPane;//para los mensajes
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
/**
 *
 * @author victor
 */
public class Nivel2 extends JFrame implements KeyListener{//se implementan algunas interfaces que nos permiten interactuar por medio de botones, etc
    //atributos
    static String color2;
    static String dificultad;
    static int dif;
    JPanel panel;
    static Nivel2 marco;//para crear la ventana del tipo de la clase
    
    //variables para las imagenes
    static ImageIcon nave;
    static ImageIcon niv;
    static ImageIcon vic;
    static ImageIcon der;
    Timer fondo;//objeto de la clase Timer
    public Nivel2(){//constructor vacio
        
    }
    public Nivel2(int d, String c){//constructor
        //se asignan los valores
        if(d==1)dificultad="/imagenes/nivel2facil.png";//si la dificultad es facil
        if(d==2)dificultad="/imagenes/nivel2normal.png";//si la dificultad es normal
        if(d==3)dificultad="/imagenes/nivel2dificil.png";//si la dificultad es dificil
        dif=d;
        color2=c;
        nave = new ImageIcon(getClass().getResource(color2));//para guardar la imagen de la nave
        niv = new ImageIcon(getClass().getResource(dificultad));//para el fondo (nivel)
        vic = new ImageIcon(getClass().getResource("/imagenes/victoria.png"));//para el fondo (nivel)
        der = new ImageIcon(getClass().getResource("/imagenes/derrota.png"));//para el fondo (nivel)
    }
    public static void nivel2(){
        marco = new Nivel2();//se crea el objeto
        marco.setSize(800, 800);// metodo de JFrame, tamaño de la ventana en px
        marco.setTitle("Nivel 2");// metodo de JFrame
        marco.setLocationRelativeTo(null);//para centrar la ventana
        marco.setVisible(true);//mostrar la ventana
        marco.nave();//se añaden las cosas a la ventana (panel)
    }
    public void nave(){
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
                movimiento(nave,niv,vic,der);//se usa el metodo de movimiento del fondo
            }
        });
        fondo.start();//aqui se inicia el Timer el cual repetira el movimiento hasta que le de stop
        
    }
    
    //metodos para las teclas
    public void keyPressed(KeyEvent event){
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
        
        papel.drawImage(niv.getImage(), x, y,5000,350, this);
        //variables para las imagenes
        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior
        int key = event.getKeyCode();
        if(key==32){//espacio
            volar(papel, nave, niv, vic, der);
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
    
    
    
    //variable para la nave en el juego
    int posx=50;
    int posy=400;
    //variables para que el nivel se mueva
    int x=0;
    int y=100;
    
    public void movimiento(ImageIcon nave, ImageIcon niv, ImageIcon vic, ImageIcon der){//para que el nivel se mueva
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
        //imagenes
        papel.drawImage(nave.getImage(), posx, posy,50,50, this);//se pone la nave
        papel.drawImage(niv.getImage(), x, y,5000,350, this);
        papel.drawImage(nave.getImage(), posx, posy,50,50, this);//se pone la nave

        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior

        x-=5;//solo se recorre 5 para que al acercarse la nave a los obstaculos este acercamiento sea mas preciso
        try{//para parar el tiempo unos cuantos milisegundos
            Thread.sleep(20);
        }catch(InterruptedException e){
            System.out.println("Error"+e);
        }
        papel.drawImage(niv.getImage(), x, y,5000,350, this);
        papel.drawImage(nave.getImage(), posx, posy,50,50, this);//se pone la nave
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
        if(dif==1){
            //coordenadas de los obstaculos
            if((x<=-370 && x>=-460 && posy<=180)||(x<=-910 && x>=-1000 && posy<=180)||(x<=-1170 && x>=-1260 && posy>=300)||(x<=-1900 && x>=-1990 && posy>=300)||(x<=-2155 && x>=-2240 && posy<=180)||(x<=-2910 && x>=-3170 && posy>=300)||(x<=-3170 && x>=-3230 && posy>=350)||(x<=-2980 && x>=-3120 && posy>=220)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la nave para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==2){//normal
            //coordenadas de los obstaculos
            if((x<=-190 && x>=-280 && posy>=210)||(x<=-460 && x>=-560 && posy<=250)||(x<=-800 && x>=-950 && posy>=253 && posy<=410)||(x<=-1155 && x>=-1220 && posy>=360)||(x<=-1155 && x>=-1220 && posy<=134)||(x<=-1675 && x>=-1740 && posy>=360)||(x<=-1675 && x>=-1740 && posy<=134)||(x<=-2615 && x>=-2920 &&  posy<=200)||(x<=-2695 && x>=-2835 &&  posy<=300)||(x<=-2725 && x>=-2805 &&  posy<=350)||(x<=-3200 && x>=-3420 && posy>=340)||(x<=-3260 && x>=-3360 && posy>=290)||(x<=-3295 && x>=-3325 && posy>=250)||(x<=-3675 && x>=-3745 && posy>=360)||(x<=-3675 && x>=-3745 && posy<=134)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la nave para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==3){//dificil
            //coordenadas de los obstaculos
            if((x<=-225 && x>=-365 && posy>=340)||(x<=-415 && x>=-645 && posy<=300)||(x<=-910 && x>=-1350 && posy>=120)||(x<=-1580 && x>=-1660 && posy>=350)||(x<=-1580 && x>=-1660 && posy<=120)||(x<=-1930 && x>=-2360 && posy<=390)
                    ||(x<=-2805 && x>=-2905 && posy>=350)||(x<=-2845 && x>=-2945 && posy>=300)||(x<=-2885 && x>=-2985 && posy>=250)||(x<=-2925 && x>=-3425 && posy>=200)||(x<=-3425 && x>=-3465 && posy>=250)||(x<=-3465 && x>=-3520 && posy>=300)||(x<=-3520 && x>=-3565 && posy>=350)//parte final abajo
                    ||(x<=-2725 && x>=-2800 && posy<=230)||(x<=-2775 && x>=-2865 && posy<=180)||(x<=-2825 && x>=-2900 && posy<=100)||(x<=-3448 && x>=-3600 && posy<=100)||(x<=-3498 && x>=-3600 && posy<=180)||(x<=-3548 && x>=-3650 && posy<=230)){//parte final arriba
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la nave para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
    }
    public void volar(Graphics papel, ImageIcon nave, ImageIcon niv, ImageIcon vic, ImageIcon der){//para la nave
        papel.setColor(Color.BLACK);//cambio de color
        papel.fillRect(posx, posy+10, 50, 50);//imprimir rectangulo
        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior
        if(posy>100){
            posy-=10;//disminuye
        }
        x-=3;//se recorre a la izquierda el fondo
        papel.drawImage(nave.getImage(), posx, posy,50,50, this);//se pone la nave
        papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el fondo 
        papel.drawImage(nave.getImage(), posx, posy,50,50, this);//se pone la nave de nuevo
        papel.setColor(Color.WHITE);//cambio de color
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
        if(dif==1){
            //coordenadas de los obstaculos
            if((x<=-370 && x>=-460 && posy<=180)||(x<=-910 && x>=-1000 && posy<=180)||(x<=-1170 && x>=-1260 && posy>=300)||(x<=-1900 && x>=-1990 && posy>=300)||(x<=-2155 && x>=-2240 && posy<=180)||(x<=-2910 && x>=-3170 && posy>=300)||(x<=-3170 && x>=-3230 && posy>=350)||(x<=-2980 && x>=-3120 && posy>=220)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la nave para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==2){//normal
            //coordenadas de los obstaculos
            if((x<=-190 && x>=-280 && posy>=210)||(x<=-460 && x>=-560 && posy<=250)||(x<=-800 && x>=-950 && posy>=253 && posy<=410)||(x<=-1155 && x>=-1220 && posy>=360)||(x<=-1155 && x>=-1220 && posy<=134)||(x<=-1675 && x>=-1740 && posy>=360)||(x<=-1675 && x>=-1740 && posy<=134)||(x<=-2615 && x>=-2920 &&  posy<=200)||(x<=-2695 && x>=-2835 &&  posy<=300)||(x<=-2725 && x>=-2805 &&  posy<=350)||(x<=-3200 && x>=-3420 && posy>=340)||(x<=-3260 && x>=-3360 && posy>=290)||(x<=-3295 && x>=-3325 && posy>=250)||(x<=-3675 && x>=-3745 && posy>=360)||(x<=-3675 && x>=-3745 && posy<=134)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la nave para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==3){//dificil
            //coordenadas de los obstaculos
            if((x<=-225 && x>=-365 && posy>=340)||(x<=-415 && x>=-645 && posy<=300)||(x<=-910 && x>=-1350 && posy>=120)||(x<=-1580 && x>=-1660 && posy>=350)||(x<=-1580 && x>=-1660 && posy<=120)||(x<=-1930 && x>=-2360 && posy<=390)
                    ||(x<=-2805 && x>=-2905 && posy>=350)||(x<=-2845 && x>=-2945 && posy>=300)||(x<=-2885 && x>=-2985 && posy>=250)||(x<=-2925 && x>=-3425 && posy>=200)||(x<=-3425 && x>=-3465 && posy>=250)||(x<=-3465 && x>=-3520 && posy>=300)||(x<=-3520 && x>=-3565 && posy>=350)//parte final abajo
                    ||(x<=-2725 && x>=-2800 && posy<=230)||(x<=-2775 && x>=-2865 && posy<=180)||(x<=-2825 && x>=-2900 && posy<=100)||(x<=-3448 && x>=-3600 && posy<=100)||(x<=-3498 && x>=-3600 && posy<=180)||(x<=-3548 && x>=-3650 && posy<=230)){//parte final arriba
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones de la nave para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
    }
   
    public void keyTyped(KeyEvent event){}//este metodo lo puse nomas porque al implementar keylistsner era obligatorio ponerlo
    public void keyReleased(KeyEvent event){//sirve par el efecto de caida del cohete
        
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
        //variables para las imagenes
        ImageIcon nave = new ImageIcon(getClass().getResource(color2));//para guardar la imagen de la nave
        ImageIcon niv = new ImageIcon(getClass().getResource(dificultad));//para el fondo (nivel)
        ImageIcon vic = new ImageIcon(getClass().getResource("/imagenes/victoria.png"));//imagen de victoria
        ImageIcon der = new ImageIcon(getClass().getResource("/imagenes/derrota.png"));//imagen de derrota
        int key = event.getKeyCode();
        if(key==32){
            papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el fondo
            papel.setColor(Color.WHITE);//cambio de color
            papel.drawLine(0, 455, 800, 455);//limite inferior
            papel.drawLine(0, 100, 800, 100);//limite superior
            do{//si llega a la linea del suelo ya no puede caer
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(20);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 455, 800, 455);//limite inferior
                papel.drawLine(0, 100, 800, 100);//limite superior
                papel.drawImage(nave.getImage(), posx, posy,50,50, this);//se pone la nave
                x-=5;//se recorre a la izquierda el fondo
                papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el fondo
                posy+=10;//aumenta
                papel.drawImage(nave.getImage(), posx, posy,50,50, this);//se pone la nave
                papel.setColor(Color.WHITE);//cambio de color
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
                    break;//para que no se abran muchas ventanas (ya no se repita el do while)
                }
                if(dif==1){
                    //coordenadas de los obstaculos
                    if((x<=-370 && x>=-460 && posy<=180)||(x<=-910 && x>=-1000 && posy<=180)||(x<=-1170 && x>=-1260 && posy>=300)||(x<=-1900 && x>=-1990 && posy>=300)||(x<=-2155 && x>=-2240 && posy<=180)||(x<=-2910 && x>=-3170 && posy>=300)||(x<=-3170 && x>=-3230 && posy>=350)||(x<=-2980 && x>=-3120 && posy>=220)){
                        fondo.stop();//se detiene el movimiento del fondo
                        papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                        try{//para parar el tiempo unos cuantos milisegundos
                            Thread.sleep(400);//tiempo de la pausa
                        }catch(InterruptedException e){
                            System.out.println("Error"+e);//se muestra la excepcion lanzada
                        }
                        x=0;//x se regresa a 0
                        //se reinician las posiciones de la nave para que cuando se repita no salga de forma rara
                        posx=50;
                        posy=400;
                        fondo.restart();//se reinicia el movimiento del fondo (nivel)
                    }
                }
                if(dif==2){//normal
                    //coordenadas de los obstaculos
                    if((x<=-190 && x>=-280 && posy>=210)||(x<=-460 && x>=-560 && posy<=250)||(x<=-800 && x>=-950 && posy>=253 && posy<=410)||(x<=-1155 && x>=-1220 && posy>=360)||(x<=-1155 && x>=-1220 && posy<=134)||(x<=-1675 && x>=-1740 && posy>=360)||(x<=-1675 && x>=-1740 && posy<=134)||(x<=-2615 && x>=-2920 &&  posy<=200)||(x<=-2695 && x>=-2835 &&  posy<=300)||(x<=-2725 && x>=-2805 &&  posy<=350)||(x<=-3200 && x>=-3420 && posy>=340)||(x<=-3260 && x>=-3360 && posy>=290)||(x<=-3295 && x>=-3325 && posy>=250)||(x<=-3675 && x>=-3745 && posy>=360)||(x<=-3675 && x>=-3745 && posy<=134)){
                        fondo.stop();//se detiene el movimiento del fondo
                        papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                        try{//para parar el tiempo unos cuantos milisegundos
                            Thread.sleep(400);//tiempo de la pausa
                        }catch(InterruptedException e){
                            System.out.println("Error"+e);//se muestra la excepcion lanzada
                        }
                        x=0;//x se regresa a 0
                        //se reinician las posiciones de la nave para que cuando se repita no salga de forma rara
                        posx=50;
                        posy=400;
                        fondo.restart();//se reinicia el movimiento del fondo (nivel)
                    }
                }
                if(dif==3){//dificil
                    //coordenadas de los obstaculos
                    if((x<=-225 && x>=-365 && posy>=340)||(x<=-415 && x>=-645 && posy<=300)||(x<=-910 && x>=-1350 && posy>=120)||(x<=-1580 && x>=-1660 && posy>=350)||(x<=-1580 && x>=-1660 && posy<=120)||(x<=-1930 && x>=-2360 && posy<=390)
                            ||(x<=-2805 && x>=-2905 && posy>=350)||(x<=-2845 && x>=-2945 && posy>=300)||(x<=-2885 && x>=-2985 && posy>=250)||(x<=-2925 && x>=-3425 && posy>=200)||(x<=-3425 && x>=-3465 && posy>=250)||(x<=-3465 && x>=-3520 && posy>=300)||(x<=-3520 && x>=-3565 && posy>=350)//parte final abajo
                            ||(x<=-2725 && x>=-2800 && posy<=230)||(x<=-2775 && x>=-2865 && posy<=180)||(x<=-2825 && x>=-2900 && posy<=100)||(x<=-3448 && x>=-3600 && posy<=100)||(x<=-3498 && x>=-3600 && posy<=180)||(x<=-3548 && x>=-3650 && posy<=230)){//parte final arriba
                        fondo.stop();//se detiene el movimiento del fondo
                        papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                        try{//para parar el tiempo unos cuantos milisegundos
                            Thread.sleep(400);//tiempo de la pausa
                        }catch(InterruptedException e){
                            System.out.println("Error"+e);//se muestra la excepcion lanzada
                        }
                        x=0;//x se regresa a 0
                        //se reinician las posiciones de la nave para que cuando se repita no salga de forma rara
                        posx=50;
                        posy=400;
                        fondo.restart();//se reinicia el movimiento del fondo (nivel)
                    }
                }
            }while(posy<=400);//mientras no llege al suelo seguira callendo
        }
    }
}
