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
/**
 *
 * @author victor
 */
public class NivelTorre extends JFrame implements KeyListener{//se implementan algunas interfaces que nos permiten interactuar por medio de botones, etc
    //atributos
    static String color;
    JPanel panel;
    static NivelTorre marco;//para crear la ventana del tipo de la clase
    
    public NivelTorre(){//constructor vacio
        
    }
    public NivelTorre(String c){//constructor
        //se asignan los valores
        color=c;
    }
    public static void torre(){
        marco = new NivelTorre();//se crea el objeto
        marco.setSize(800, 800);// metodo de JFrame, tamaño de la ventana en px
        marco.setTitle("Torre");// metodo de JFrame
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
        panel = new Fondo();
        panel.setPreferredSize(new Dimension(800,700));
        ventana.add(panel);
        addKeyListener(this);//toda la ventana se suscribe al keylistener
    }
    //variable para el cubo en el juego
    int posx=350;
    int posy=630;
    //variables para que el nivel se mueva
    int x=0;
    int y=100;
    //metodos para las teclas
    public void keyPressed(KeyEvent event){
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
       //variables para las imagenes
        ImageIcon cubo = new ImageIcon(getClass().getResource(color));//para guardar la imagen del cubo
        ImageIcon niv = new ImageIcon(getClass().getResource("/imagenes/torrenormal.png"));//para el fondo (nivel)
        ImageIcon vic = new ImageIcon(getClass().getResource("/imagenes/victoria.png"));//para el fondo (nivel)
        ImageIcon der = new ImageIcon(getClass().getResource("/imagenes/derrota.png"));//para el fondo (nivel)
        papel.drawImage(niv.getImage(), x, y,800,550, this);
        papel.setColor(Color.WHITE);//cambio de color
        papel.drawLine(0, 650, 800, 650);//limite inferior
        int key = event.getKeyCode();
        if(key==39){//flecha derecha
            derecha(papel,cubo,niv,vic,der);
        }
        if(key==37){//flecha izquierda
            izquierda(papel,cubo,niv,vic,der);
        }
        if(key==65){//letra a, salto hacia la izquierda
            saltoizquierdo(papel,cubo,niv,vic,der);
        }
        if(key==68){//letra b, salto hacia la derecha
            saltoderecho(papel,cubo,niv,vic,der);
        }
        if(key==27){//si es la tecla escape el juego se pausara y se mostraran algunas opciones
            Object[] opciones = { "Continuar", "Menu Principal", "Salir"};//opciones del menu
            //se guarda laopcion en otro objeto
            int seleccionado=JOptionPane.showOptionDialog(null, "Escoge una opcion", "Pausa",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, opciones, opciones[0]);
            if(seleccionado==0){
            }
            if(seleccionado==1){
                marco.dispose();//se quita la ventana para que al ir de nuevo a el menu principal no se cree una aparte
                NoteEstrelles jp4 = new NoteEstrelles();//se le envia 0para ir directo al menu principal
                jp4.juego();
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
    
    public void izquierda(Graphics papel, ImageIcon cubo, ImageIcon niv, ImageIcon vic, ImageIcon der){
        if(posx>0){//si aun no se pega al borde izquierdo se puede seguir moviendo hacia alla
            //paredes
            if(posy==630 && posx>=285){
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 650, 800, 650);//limite inferior
                posx-=10;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 650, 800, 650);//limite inferior
            }
            if(posy==630 && posx<=285){
                //si esta en la pared
                papel.drawImage(cubo.getImage(), 290, posy,20,20, this);
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 650, 800, 650);//limite inferior
            }
            if(posy==560 && posx>=450 && posx<=690){//piso 2
                posx-=10;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy==560 && posx<=450){
                //si esta en el precipicio
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                posy=630;
                papel.drawImage(cubo.getImage(), 440, posy,20,20, this);
            }
            if(posy==490 && posx<=490 && posx>=0){
                posx-=10;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy==490 && posx>=480){
                //si esta en la pared
                papel.drawImage(cubo.getImage(), 240, posy,20,20, this);
            }
        }else{//si se pasa del limite nomas se dibuja el jugador en el limite
            papel.drawImage(cubo.getImage(), 0, posy,20,20, this);
        }
    }
 
    public void derecha(Graphics papel, ImageIcon cubo, ImageIcon niv, ImageIcon vic, ImageIcon der){

        if(posx<780){//si aun no se pega al borde derecho se puede seguir moviendo hacia alla
            //paredes
            if(posy==630 && posx<=440){
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 650, 800, 650);//limite inferior
                posx+=10;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 650, 800, 650);//limite inferior
            }
            if(posy==630 && posx>=440){
                //si esta en la pared
                papel.drawImage(cubo.getImage(), 440, posy,20,20, this);
                papel.setColor(Color.WHITE);//cambio de color
                papel.drawLine(0, 650, 800, 650);//limite inferior
            }
            if(posy==560 && posx>=440 && posx<=670){
                posx+=10;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy==560 && posx>=670){
                //si esta en la pared
                papel.drawImage(cubo.getImage(), 660, posy,20,20, this);
            }
            if(posy==490 && posx<=490 && posx>=0){
                posx+=10;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy==490 && posx>=490){
                //si esta en el precipicio
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                posy=560;
                papel.drawImage(cubo.getImage(), 500, posy,20,20, this);
            }
        }else{//si se pasa del limite nomas se dibuja el jugador en el limite
            papel.drawImage(cubo.getImage(), 780, posy,20,20, this);
        }
    }
    
    public void saltoizquierdo(Graphics papel, ImageIcon cubo, ImageIcon niv, ImageIcon vic, ImageIcon der){//para el cubo
        boolean suelo2=false;//booleano para que al saltar se sepa donde esta el suelo
        for (int i = 0; i < 10; i++) {
            suelo2=false;
            try{//para parar el tiempo unos cuantos milisegundos
                Thread.sleep(25);
            }catch(InterruptedException e){
                System.out.println("Error"+e);
            }
            papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
            papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            posy-=10;
            posx-=4;
            //orillas y paredes
            if(posx<=0){//si esta en la orilla por alguna razon se dibuja en la orilla
                posy=630;
                posx=0;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy<=630 && posy>=530 && posx<=285){
                posy=630;
                posx=285;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy<=540 && posy>=510 && posx>=450 && posx<=480){
                posy=560;
                posx=490;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            //suelos
            if((posy>=630 && posx>=0 && posx<=780)||(posy==560 && posx>=440)||(posy==490 && posx<=490)||(posy==430 && posx<=780 && posx>=480)){
                suelo2=true;
            }
            if((posy==430 && posx<=780 && posx>=480)){
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
                break;
            }
        }
        while(suelo2==false){
            try{//para parar el tiempo unos cuantos milisegundos
                Thread.sleep(25);
            }catch(InterruptedException e){
                System.out.println("Error"+e);
            }
            papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
            papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            posy+=10;
            posx-=4;
            //orillas y paredes
            if(posx<=0){//si esta en la orilla por alguna razon se dibuja en la orilla
                posy=630;
                posx=0;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy<=630 && posy>=530 && posx<=285){
                posy=630;
                posx=285;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy<=540 && posy>=510 && posx>=450 && posx<=480){
                posy=560;
                posx=490;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            //suelos
            if((posy>=630 && posx>=0 && posx<=780)||(posy==560 && posx>=440)||(posy==490 && posx<=490)||(posy==430 && posx<=780 && posx>=480)){
                suelo2=true;
            }
            if((posy==430 && posx<=780 && posx>=480)){
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
                break;
            }
        }
    }
    public void saltoderecho(Graphics papel, ImageIcon cubo, ImageIcon niv, ImageIcon vic, ImageIcon der){//para el cubo
        boolean suelo=false;//booleano para que al saltar se sepa donde esta el suelo
        for (int i = 0; i < 10; i++) {
            suelo=false;
            try{//para parar el tiempo unos cuantos milisegundos
                Thread.sleep(25);
            }catch(InterruptedException e){
                System.out.println("Error"+e);
            }
            papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
            papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            posy-=10;
            posx+=4;
            //orillas y paredes
            if(posx>=780){//si esta en la orilla por alguna razon se dibuja en la orilla
                posy=630;
                posx=780;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy>=590 && posx>=440){
                posy=630;
                posx=440;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy>=490 && posx>=670){
                posy=560;
                posx=670;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            //suelos
            if((posy>=630 && posx>=0 && posx<=780)||(posy==560 && posx>=440)||(posy==490 && posx<=490)||(posy==430 && posx<=780 && posx>=480)){
                suelo=true;
            }
            if((posy==430 && posx<=780 && posx>=480)){
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
                break;
            }
        }
        while(suelo==false){
            papel.setColor(Color.WHITE);//cambio de color
            papel.drawLine(0, 650, 800, 650);//limite inferior
            try{//para parar el tiempo unos cuantos milisegundos
                Thread.sleep(25);
            }catch(InterruptedException e){
                System.out.println("Error"+e);
            }
            papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
            papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            posy+=10;
            posx+=4;
            //orillas y paredes
            if(posx>=780){//si esta en la orilla por alguna razon se dibuja en la orilla
                posy=630;
                posx=780;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy>=580 && posx>=440){
                posy=630;
                posx=440;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            if(posy>=490 && posx>=670){
                posy=560;
                posx=670;
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
                papel.drawImage(niv.getImage(), x, y,800,550, this);//se muestra el juego de fondo
                papel.drawImage(cubo.getImage(), posx, posy,20,20, this);
            }
            //suelos
            if((posy>=630 && posx>=0 && posx<=780)||(posy==560 && posx>=440)||(posy==490 && posx<=490)||(posy==430 && posx<=780 && posx>=480)){
                suelo=true;
            }
            if((posy==430 && posx<=780 && posx>=480)){
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
                break;
            }
        }
    }
    public void keyTyped(KeyEvent event){}//este metodo lo puse nomas porque al implementar keylistsner era obligatorio ponerlo
    //clase para que el jpanel inicie ya con el fondo, no tenga que esperar un click
    class Fondo extends javax.swing.JPanel{//funcion creada para que se cambie el fondo del panel
        private java.awt.Image torre;//se crea una variable para la imagen del fondo
        @Override//viene de JFrame por eso se sobreescribio
        public void paint(java.awt.Graphics g){//funcion que recibe graficos y sirve para cambiar fondos
            torre = new javax.swing.ImageIcon(getClass().getResource("/imagenes/torrenormal.png")).getImage();
            g.drawImage(torre, x, y, 800, 550, this);//se dibuja la imagen
            setOpaque(false);//para que se vea
            super.paint(g);//se usa el metodo de la clase padre con super
        }
    }
}
