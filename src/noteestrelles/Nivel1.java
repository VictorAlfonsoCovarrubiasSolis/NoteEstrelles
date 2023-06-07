/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package noteestrelles;
import java.awt.*; //Abstract Window Toolkit - una de las primeras herramientas, ya casi no se usa
//Depende de la plataforma (SO) (en cuanto a controles)
import javax.swing.*;//javax significa extension - Independente de la plataforma
import java.awt.event.*;//accion con el mouse, este se pone porque java.swing tambien tiene un event
import javax.swing.JOptionPane;//para los mensajes
import static java.lang.System.exit;//para el boton salir
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
/**
 *
 * @author victor
 */
public class Nivel1 extends JFrame implements KeyListener{//se implementan algunas interfaces que nos permiten interactuar por medio de botones, etc
    //atributos
    static String color;
    static String dificultad;
    static int dif;
    JPanel panel;
    static Nivel1 marco;//para crear la ventana del tipo de la clase
       
    //variables para las imagenes
    static ImageIcon cubo;
    static ImageIcon niv;
    static ImageIcon vic;
    static ImageIcon der;
    
    Timer fondo;//objeto de la clase Timer
    public Nivel1(){//constructor vacio
        
    }
    public Nivel1(int d, String c){//constructor
        //se asignan los valores
        if(d==1)dificultad="/imagenes/nivel1facil.png";//si la dificultad es facil
        if(d==2)dificultad="/imagenes/nivel1normal.png";//si la dificultad es normal
        if(d==3)dificultad="/imagenes/nivel1dificil.png";//si la dificultad es dificil
        dif=d;
        color=c;
        
        cubo = new ImageIcon(getClass().getResource(color));//para guardar la imagen del cubo
        niv = new ImageIcon(getClass().getResource(dificultad));//para el fondo (nivel)
        vic = new ImageIcon(getClass().getResource("/imagenes/victoria.png"));//para el fondo (nivel)
        der = new ImageIcon(getClass().getResource("/imagenes/derrota.png"));//para el fondo (nivel)
    }
    public static void nivel1(){
        marco = new Nivel1();//se crea el objeto
        marco.setSize(800, 800);// metodo de JFrame, tamaño de la ventana en px
        marco.setTitle("Nivel 1");// metodo de JFrame
        marco.setLocationRelativeTo(null);//para centrar la ventana
        marco.setVisible(true);//mostrar la ventana
        marco.cubo();//se añaden las cosas a la ventana (panel)
    }
    public void cubo(){
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
                movimiento(niv,cubo,vic,der);//se usa el metodo de movimiento del fondo
            }
        });
        fondo.start();//aqui se inicia el Timer el cual repetira el movimiento hasta que le de stop
    }
    //variable para el cubo en el juego
    int posx=50;
    int posy=400;
    //variable para el cubo en el juego invertido
    int posx2=50;
    int posy2=100;
    //variables para que el nivel se mueva
    int x=0;
    int y=100;
    //variables paraque no se habran dos ventanas al morir y al ganar ya que siempre que se salta es la parte hacia arriba y la parte hacia abajo asique si se muere arriba le falta ir hacia abajo estos booleanos evitan que se hagan acciones de mas
    boolean v=false;
    boolean d=false;
    boolean di=false;
    //metodos para las teclas
    public void keyPressed(KeyEvent event){
        
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
        papel.drawImage(niv.getImage(), x, y,5000,350, this);//se dibuja el nivel
        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior
        int key = event.getKeyCode();
        
        if(key==32){//espacio
            salto(papel,niv,cubo, vic, der);//si es la tecla espacio el cubo saltara
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
    
    public void salto(Graphics papel, ImageIcon niv, ImageIcon cubo, ImageIcon vic, ImageIcon der){//para el cubo
        if(dif==3 && x<=-1050 && x>=-2735){//si es la dificultad dificil y esta en la parte invertida el salto es alrevez
            for (int i = 0; i < 15; i++) {//ciclo para la subida
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 455, 800, 455);//limite inferior
                papel.drawLine(0, 100, 800, 100);//limite superior
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(20);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(2);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                posy2+=10;//cambia el valor de y para que el cubo vaya hacia arriba (abajo porque esta invertida)
                papel.drawImage(cubo.getImage(), posx2, posy2,50,50, this);//se dibuja el cubo
                //movimiento del juego a la vez que el salto
                x-=7;//se recorre a la izquierda
                papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx2, posy2,50,50, this);//se dibuja el cubo
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 455, 800, 455);//limite inferior
                papel.drawLine(0, 100, 800, 100);//limite superior
                //choques al momento de saltar solo estan los de la dificultad 3 en la parte inverrtida
                if((x<=-1390 && x>=-1450 && posy2<=150)||(x<=-1865 && x>=-1925 && posy2<=150)||(x<=-2395 && x>=-2493 && posy2<=120)||(x<=-2727 && x>=-2728 && posy2<=150)){
                    fondo.stop();//se detiene el movimiento del fondo
                    papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                    try{//para parar el tiempo unos cuantos milisegundos
                        Thread.sleep(400);//tiempo de la pausa
                    }catch(InterruptedException e){
                        System.out.println("Error"+e);//se muestra la excepcion lanzada
                    }
                    x=0;//x se regresa a 0
                    //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                    posx=50;
                    posy=400;
                    posx2=50;
                    posy2=100;
                    fondo.restart();//se reinicia el movimiento del fondo (nivel)
                    di=true;//para que no se repitan los pasos en la caida
                    break;//se termina el ciclo for
                }
                if(x<=-2735){//si se termina la parte inversa (se llega al portal)
                    //se reinician ya que si entra aqui puede que no haya completado el salto por lo tanto posx y posy no se quedaran donde deben
                    posx2=50;
                    posy2=100;
                    //Se limpia la pantalla
                    papel.setColor(Color.BLACK);//cambio de color
                    papel.fillRect(0, 0, 800, 800);//imprimir rectangulo
                    papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el juego de fondo
                    papel.drawImage(cubo.getImage(), posx, posy,50,50, this);//se muestra de nuevo el cubo
                    papel.setColor(Color.WHITE);//cambio de color
                    papel.drawLine(0, 455, 800, 455);//limite inferior
                    papel.drawLine(0, 100, 800, 100);//limite superior
                    break;
                }
            }
            for (int i = 0; i < 15; i++) {//ciclo para la caida
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 455, 800, 455);//limite inferior
                papel.drawLine(0, 100, 800, 100);//limite superior
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(20);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(2);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                posy2-=10;//cambia el valor de y para que el cubo vaya hacia abajo (arriba porque esta invertida)
                papel.drawImage(cubo.getImage(), posx2, posy2,50,50, this);//se dibuja el cubo
                //movimiento del juego a la vez que el salto
                x-=7;//se recorre a la izquierda
                papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx2, posy2,50,50, this);//se muestra de nuevo el cubo
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 455, 800, 455);//limite inferior
                papel.drawLine(0, 100, 800, 100);//limite superior
                //choques al momento de saltar solo estan los de la dificultad 3 porque la parte inversa solo se encuentra en esta dificultad
                if(di==false &&((x<=-1390 && x>=-1450 && posy2<=150)||(x<=-1865 && x>=-1925 && posy2<=150)||(x<=-2395 && x>=-2493 && posy2<=120)||(x<=-2727 && x>=-2728 && posy2<=150))){
                    fondo.stop();//se detiene el movimiento del fondo
                    papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                    try{//para parar el tiempo unos cuantos milisegundos
                        Thread.sleep(400);//tiempo de la pausa
                    }catch(InterruptedException e){
                        System.out.println("Error"+e);//se muestra la excepcion lanzada
                    }
                    x=0;//x se regresa a 0
                    //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                    posx=50;
                    posy=400;
                    posx2=50;
                    posy2=100;
                    fondo.restart();//se reinicia el movimiento del fondo (nivel)
                    break;//se acaba el ciclo for
                }
                if(x<=-2735){//si se acaba la parte inversa (se llega al portal)
                    //se reinician ya que sie entra aqui puede que nohaya completado el salto por lo tanto posx y posy no se quedaran donde deben
                    posx2=50;
                    posy2=100;
                    //Se limpia la pantalla
                    papel.setColor(Color.BLACK);//cambio de color
                    papel.fillRect(0, 0, 800, 800);//imprimir rectangulo
                    papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el juego de fondo
                    //se muestra de nuevo el cubo
                    papel.drawImage(cubo.getImage(), posx, posy,50,50, this);
                    papel.setColor(Color.WHITE);//cambio de color
                    papel.drawLine(0, 455, 800, 455);//limite inferior
                    papel.drawLine(0, 100, 800, 100);//limite superior
                    break;//se acaba el ciclo for
                }
            }
        }else{//salto con gravedad normal
            for (int i = 0; i < 15; i++){//ciclo para la subida
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 455, 800, 455);//limite inferior
                papel.drawLine(0, 100, 800, 100);//limite superior
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(20);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(2);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                posy-=10;//disminuye y para que el cubo vaya hacia arriba
                papel.drawImage(cubo.getImage(), posx, posy,50,50, this);//se dibuja el cubo
                //movimiento del juego a la vez que el salto
                x-=7;//se recorre a la izquierda
                papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,50,50, this);//se muestra de nuevo el cubo
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 455, 800, 455);//limite inferior
                papel.drawLine(0, 100, 800, 100);//limite superior
                //choques al momento de saltar
                if(dif==1){//si es la dificultad facil
                    //coordenadas de los obstaculos
                    if((x<=-365 && x>=-415 && posy>=380)||(x<=-690 && x>=-735 && posy>=380)||(x<=-1195 && x>=-1285 && posy>=380)||(x<=-1630 && x>=-1675 && posy>=380)||(x<=-2010 && x>=-2100 && posy>=380)||(x<=-2545 && x>=-2625 && posy>=380)||(x<=-3155 && x>=-3210 && posy>=380)||(x<=-3675 && x>=-3725 && posy>=380)){
                        fondo.stop();//se detiene el movimiento del fondo
                        papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                        try{//para parar el tiempo unos cuantos milisegundos
                            Thread.sleep(400);//tiempo de la pausa
                        }catch(InterruptedException e){
                            System.out.println("Error"+e);//se muestra la excepcion lanzada
                        }
                        x=0;//x se regresa a 0
                        //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                        posx=50;
                        posy=400;
                        posx2=50;
                        posy2=100;
                        fondo.restart();//se reinicia el movimiento del fondo (nivel)
                        d=true;//para que no se repita en la caida
                        break;//se termina el ciclo for
                    }
                }
                if(dif==2){//normal
                    //coordenadas de los obstaculos
                    if((x<=-330 && x>=-380 && posy>=350)||(x<=-1005 && x>=-1095 && posy>=380)||(x<=-1395 && x>=-1455 && posy>=350)||(x<=-1715 && x>=-1775 && posy>=350)||(x<=-2010 && x>=-2105 && posy>=380)||(x<=-3050 && x>=-3145 && posy>=380)||(x<=-3675 && x>=-3725 && posy>=380)){
                        fondo.stop();//se detiene el movimiento del fondo
                        papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                        try{//para parar el tiempo unos cuantos milisegundos
                            Thread.sleep(400);//tiempo de la pausa
                        }catch(InterruptedException e){
                            System.out.println("Error"+e);//se muestra la excepcion lanzada
                        }
                        x=0;//x se regresa a 0
                        //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                        posx=50;
                        posy=400;
                        posx2=50;
                        posy2=100;
                        fondo.restart();//se reinicia el movimiento del fondo (nivel)
                        d=true;//para que no se repita en la caida
                        break;//se termina el ciclo for
                    }
                }
                if(dif==3){//dificil
                    //coordenadas de los obstaculos
                    if((x<=-270 && x>=-365 && posy>=380)||(x<=-778 && x>=-920 && posy>=380)||(x<=-3145 && x>=-3245 && posy>=380)||(x<=-3670 && x>=-3807 && posy>=380)){
                        fondo.stop();//se detiene el movimiento del fondo
                        papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                        try{//para parar el tiempo unos cuantos milisegundos
                            Thread.sleep(400);//tiempo de la pausa
                        }catch(InterruptedException e){
                            System.out.println("Error"+e);//se muestra la excepcion lanzada
                        }
                        x=0;//x se regresa a 0
                        //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                        posx=50;
                        posy=400;
                        posx2=50;
                        posy2=100;
                        fondo.restart();//se reinicia el movimiento del fondo (nivel)
                        d=true;//para que no se repita en la caida
                        break;//se termina el ciclo for
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
                    v=true;//si se gano al comienzo del salto ya no es necesario que se gane en la caida
                    break;//para que termine el for
                }
                if(dif==3 && x<=-1050 && x>=-1100){//si esta en el modo dificil con las gravedad normal
                    //se reinician ya que si entra aqui puede que nohaya completado el salto por lo tanto posx y posy no se quedaran donde deben
                    posx=50;
                    posy=400;
                    //Se limpia la pantalla
                    papel.setColor(Color.BLACK);//cambio de color
                    papel.fillRect(0, 0, 800, 800);//imprimir rectangulo
                    papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el juego de fondo
                    //se muestra de nuevo el cubo
                    papel.drawImage(cubo.getImage(), posx2, posy2,50,50, this);
                    papel.setColor(Color.WHITE);//cambio de color
                    papel.drawLine(0, 455, 800, 455);//limite inferior
                    papel.drawLine(0, 100, 800, 100);//limite superior
                    break;//se termina el for
                }
                
            }
            for (int i = 0; i < 15; i++) {//ciclo para la caida
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 455, 800, 455);//limite inferior
                papel.drawLine(0, 100, 800, 100);//limite superior
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(20);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(2);
                }catch(InterruptedException e){
                    System.out.println("Error"+e);
                }
                posy+=10;//aumenta y para que el cubo caiga
                papel.drawImage(cubo.getImage(), posx, posy,50,50, this);//se dibuja el cubo
                //movimiento del juego a la vez que el salto
                x-=7;//se recorre a la izquierda
                papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,50,50, this);//se muestra de nuevo el cubo
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 455, 800, 455);//limite inferior
                papel.drawLine(0, 100, 800, 100);//limite superior
                //choques al momento de saltar
                if(dif==1 && d==false){//si es la dificultad facil
                    //coordenadas de los obstaculos
                    if((x<=-365 && x>=-415 && posy>=380)||(x<=-690 && x>=-735 && posy>=380)||(x<=-1195 && x>=-1285 && posy>=380)||(x<=-1630 && x>=-1675 && posy>=380)||(x<=-2010 && x>=-2100 && posy>=380)||(x<=-2545 && x>=-2625 && posy>=380)||(x<=-3155 && x>=-3210 && posy>=380)||(x<=-3675 && x>=-3725 && posy>=380)){
                        fondo.stop();//se detiene el movimiento del fondo
                        papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                        try{//para parar el tiempo unos cuantos milisegundos
                            Thread.sleep(400);//tiempo de la pausa
                        }catch(InterruptedException e){
                            System.out.println("Error"+e);//se muestra la excepcion lanzada
                        }
                        x=0;//x se regresa a 0
                        //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                        posx=50;
                        posy=400;
                        posx2=50;
                        posy2=100;
                        fondo.restart();//se reinicia el movimiento del fondo (nivel)
                        break;//se termina el ciclo for
                    }
                }
                if(dif==2 && d==false){
                    //coordenadas de los obstaculos
                    if((x<=-330 && x>=-380 && posy>=350)||(x<=-1005 && x>=-1095 && posy>=380)||(x<=-1395 && x>=-1455 && posy>=350)||(x<=-1715 && x>=-1775 && posy>=350)||(x<=-2010 && x>=-2105 && posy>=380)||(x<=-3050 && x>=-3145 && posy>=380)||(x<=-3675 && x>=-3725 && posy>=380)){
                        fondo.stop();//se detiene el movimiento del fondo
                        papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                        try{//para parar el tiempo unos cuantos milisegundos
                            Thread.sleep(400);//tiempo de la pausa
                        }catch(InterruptedException e){
                            System.out.println("Error"+e);//se muestra la excepcion lanzada
                        }
                        x=0;//x se regresa a 0
                        //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                        posx=50;
                        posy=400;
                        posx2=50;
                        posy2=100;
                        fondo.restart();//se reinicia el movimiento del fondo (nivel)
                        break;//se termina el ciclo for
                    }
                }
                if(dif==3 && d==false){//dificil
                    //coordenadas de los obstaculos
                    if((x<=-270 && x>=-365 && posy>=380)||(x<=-778 && x>=-920 && posy>=380)||(x<=-3145 && x>=-3245 && posy>=380)||(x<=-3670 && x>=-3807 && posy>=380)){
                        fondo.stop();//se detiene el movimiento del fondo
                        papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                        try{//para parar el tiempo unos cuantos milisegundos
                            Thread.sleep(400);//tiempo de la pausa
                        }catch(InterruptedException e){
                            System.out.println("Error"+e);//se muestra la excepcion lanzada
                        }
                        x=0;//x se regresa a 0
                        //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                        posx=50;
                        posy=400;
                        posx2=50;
                        posy2=100;
                        fondo.restart();//se reinicia el movimiento del fondo (nivel)
                        break;//se termina el ciclo for
                    }
                }
                
                if(x<=-4230 && v==false){//cuando llega a la meta
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
                    break;//para que termine el for
                }
                if(dif==3 && x<=-1050 && x>=-1100){//si se llega al portal
                   //se reinician ya que si entra aqui puede que nohaya completado el salto por lo tanto posx y posy no se quedaran donde deben
                    posx=50;
                    posy=400;
                    //Se limpia la pantalla
                    papel.setColor(Color.BLACK);//cambio de color
                    papel.fillRect(0, 0, 800, 800);//imprimir rectangulo
                    papel.drawImage(niv.getImage(), x, y,5000,350, this);//se muestra el juego de fondo
                    //se muestra de nuevo el cubo
                    papel.drawImage(cubo.getImage(), posx2, posy2,50,50, this);
                    papel.setColor(Color.WHITE);//cambio de color
                    papel.drawLine(0, 455, 800, 455);//limite inferior
                    papel.drawLine(0, 100, 800, 100);//limite superior
                    break;//para que termine el for
                }
            }
        }
        
    }
    public void movimiento(ImageIcon niv, ImageIcon cubo, ImageIcon vic, ImageIcon der){ //Se desata cuando se mueve el mouse
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
        papel.drawImage(niv.getImage(), x, y,5000,350, this);//se dibuja el nivel
        if(dif==3&& x<=-1050 && x>=-2725){//si es la parte inversa el cubo se dibuja con las coordenadas de la parte inversa
            papel.drawImage(cubo.getImage(), posx2, posy2,50,50, this);//se dibuja el cubo
        }else{//si no es la parte inversa se usan las coordenadas normales
            papel.drawImage(cubo.getImage(), posx, posy,50,50, this);//se dibuja el cubo
        }
        //limites
        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 455, 800, 455);//limite inferior
        papel.drawLine(0, 100, 800, 100);//limite superior
        x-=5;//solo se recorre 5 para que al acercarse el cubo a los obstaculos este acercamiento sea mas preciso
        papel.drawImage(niv.getImage(), x, y,5000,350, this);//se dibuja el nivel
        if(dif==3&& x<=-1050 && x>=-2725){//si es la parte inversa el cubo se dibuja con las coordenadas de la parte inversa
            papel.drawImage(cubo.getImage(), posx2, posy2,50,50, this);//se dibuja el cubo
        }else{//si no es la parte inversa se usan las coordenadas normales
            papel.drawImage(cubo.getImage(), posx, posy,50,50, this);//se dibuja el cubo
        }
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
        if(dif==1){//si es la dificultad facil
            //coordenadas de los obstaculos
            if((x<=-350 && x>=-430)||(x<=-675 && x>=-750)||(x<=-1180 && x>=-1300)||(x<=-1615 && x>=-1690)||(x<=-1995 && x>=-2115)||(x<=-2530 && x>=-2640)||(x<=-3140 && x>=-3225)||(x<=-3660 && x>=-3740)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                posx2=50;
                posy2=100;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==2){//normal
            //coordenadas de los obstaculos
            if((x<=-315 && x>=-395)||(x<=-990 && x>=-1110)||(x<=-1380 && x>=-1470)||(x<=-1700 && x>=-1790)||(x<=-1990 && x>=-2120)||(x<=-3035 && x>=-3160)||(x<=-3660 && x>=-3740)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                posx2=50;
                posy2=100;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
        if(dif==3){//dificil
            //coordenadas de los obstaculos
            if((x<=-255 && x>=-380)||(x<=-763 && x>=-935)||(x<=-1375 && x>=-1465)||(x<=-1850 && x>=-1940)||(x<=-2380 && x>=-2508)||(x<=-2712 && x>=-2719)||(x<=-3130 && x>=-3260)||(x<=-3655 && x>=-3822)){
                fondo.stop();//se detiene el movimiento del fondo
                papel.drawImage(der.getImage(), 0, 0,800,800, this);//se muestra la imagen de derrota
                try{//para parar el tiempo unos cuantos milisegundos
                    Thread.sleep(400);//tiempo de la pausa
                }catch(InterruptedException e){
                    System.out.println("Error"+e);//se muestra la excepcion lanzada
                }
                x=0;//x se regresa a 0
                //se reinician las posiciones del cubo para que cuando se repita no salga de forma rara
                posx=50;
                posy=400;
                posx2=50;
                posy2=100;
                fondo.restart();//se reinicia el movimiento del fondo (nivel)
            }
        }
    }
    
    public void keyTyped(KeyEvent event){}//este metodo lo puse nomas porque al implementar keylistsner era obligatorio ponerlo
}
