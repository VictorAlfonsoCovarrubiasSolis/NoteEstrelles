/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package noteestrelles;
import java.awt.*; //Abstract Window Toolkit - una de las primeras herramientas, ya casi no se usa
//Depende de la plataforma (SO) (en cuanto a controles)
import javax.swing.*;//javax significa extension - Independente de la plataforma
import java.awt.event.*;//accion con el mouse, este se pone porque java.swing tambien tiene un event
import static java.lang.System.exit;//para el boton salir
//para los mensajes
import javax.swing.JOptionPane;
/**
 *
 * @author victor
 */
public class NoteEstrelles extends JFrame implements ActionListener, KeyListener{//se implementan algunas interfaces que nos permiten interactuar por medio de botones, etc
    JPanel panel; // J - indica que es de Swing
    JButton inicio, salir;//botones
    static int ans=0;//respuesta para saber a que metodo dirigirse
    static int difi=1;//para guardar la dificultad (facil predeterminada)
    //para que los joptionpane solo se muestren una vez
    static boolean control1=false;
    static boolean control2=false;
    static boolean control3=false;
    static boolean control4=false;
    static boolean control5=false;
    //agregados para los dos submenus
    static boolean control6=false;
    static boolean control7=false;
    static String color="/imagenes/red.png";//para guardar el color (rojo predeterminado)
    static String color2="/imagenes/redship.png";//para guardar el color de la nave (rojo predeterminado)
    static String color3="/imagenes/redspider.png";//para guardar el color de la araña (rojo predeterminado)
    static NoteEstrelles marco;//para crear la ventana del tipo de la clase
    
    public NoteEstrelles(){//constructor
        ans=0;//para que al regresar a el inicio desde el juego ya empezado este no se meta de nuevo al juego directamente ya que vuelve con asn =1
    }
    
    public static void main(String[] args) {
        juego();//se llama a juego el cual es el que crea todo
    }
    
    public static void juego(){//esto por lo regular va en el main pero si lo hago en un metodo aparte puedo llamarlo cuando quiera
        marco = new NoteEstrelles();//se crea el objeto
        marco.setSize(800, 800);// metodo de JFrame, tamaño de la ventana en px
        marco.setTitle("¡No te estrelles!");// metodo de JFrame
        marco.setLocationRelativeTo(null);//para centrar la ventana
        marco.setVisible(true);//mostrar la ventana
        marco.menus();//se añaden las cosas a la ventana (panel, botones, etc)
    }
    
    //metodos para los menus
    
    private void menus(){
        setDefaultCloseOperation(EXIT_ON_CLOSE); //EXIT_ON_CLOSE Es una enumeracion, es para que deje de correr el programa (termina la ejecucion al cerrar la ventana)
        Container ventana = getContentPane();
        
        ventana.setLayout(new FlowLayout());//ADMINISTRADOR DE CONTENIDO, flowlayout los acomoda como van llegando donde caben
        
        //Colocar los objtos en la ventana.
        //PANEL
        panel = new Fondo();//ahora el panel se crea de forma diferente usando una clase creada por mi que esta en la parte de hasta abajo de esta clase
        panel.setPreferredSize(new Dimension(800,700));
        ventana.add(panel);
        addKeyListener(this);//toda la ventana se suscribe al keylistener
        
        inicio = new JButton("inicio");//boton para regresar al menu prinicipal
        inicio.addActionListener(this);//suscribir el boton al listener
        ventana.add(inicio);//se agrega el boton
        inicio.setFocusable(false);//para que sirvan los botones junto al keylistsner
        salir = new JButton("salir");//BOTON para terminar el programa
        salir.addActionListener(this);//suscribir el boton al listener
        ventana.add(salir);//se agrega el boton
        salir.setFocusable(false);//para que sirvan los botones junto al keylistsner
        while(control1==false){
            JOptionPane.showInternalMessageDialog(null, "Para moverte por el menu se usan las teclas de flecha arriba y flecha abajo.\nPara seleccionar una opcion se usa el enter.","Menu principal", JOptionPane.INFORMATION_MESSAGE);
            //pongo aqui el encabezado para que tambien solo salga una vez
            System.out.println("-------Encabezado------");//datos del alumno que creo el codigo
            System.out.println("Nombre: Victor Alfonso Covarrubias Solis");
            System.out.println("Matricula: 177294");
            System.out.println("Materia: labo de progra 3");
            System.out.println("grupo: D Jueves de 16:00-18:00");
            System.out.println("-----------------------");
            control1= true;//para que no vuelva a entrar
        }
        
    }
    //variables para los menus
    int x=100;
    int y=150;
    //variable para el menu dificultad
    int y2=150;
    //variable para el menu seleccion de presonaje
    int y3=120;
    //variable para el menu de nivel
    int y4=125;
    //metodos para el funcionamiento de las teclas
    public void keyPressed(KeyEvent event){//aqui estara la matoria del programa
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
        int key = event.getKeyCode();
        if(ans==0){//si es el menu principal
            ImageIcon img0 = new ImageIcon(getClass().getResource("/imagenes/menu.png"));//imagen del menu principal
            papel.drawImage(img0.getImage(), 50, 0,700,700, this);
            papel.setColor(Color.MAGENTA);///cambio de color para el circulo que se mueve con las teclas de flecha
            //como todos los if solo tenian una sentencia los puse de esa forma para no ocupar tanto espacio
            if(key==38){
                if(y>150)y-=150;//si la tecla es la flecha arriba se disminuye y solo si no es la primera opcion
            }
            if(key==40){
                if(y<450)y+=150;//si la tecla es la flecha arriba se disminuye y solo si no es la ultima opcion
            }
            if(key==10){//si se presiona enter
                if(y==150)ans=1;
                if(y==300)ans=2;
                if(y==450)ans=3;
                //se les da el valor inicial a y para que en los menus salga de manera correcta
                y=150;
                y2=150;
                y3=120;
                y4=140;
            }
            papel.fillOval(x, y, 50, 50);//se imprime el circulo
        }
        if(ans==1){//si se selecciono juego
            ImageIcon img2 = new ImageIcon(getClass().getResource("/imagenes/niveles.png"));
            papel.drawImage(img2.getImage(), 50, 0,700,650, this);
            papel.setColor(Color.MAGENTA);//cambiar color
            if(key==38){
                if(y4>140)y4-=140;//si la tecla es la flecha arriba se disminuye y solo su no es la primera opcion
            }
            if(key==40){
                if(y4<560)y4+=140;//si la tecla es la flecha arriba se disminuye y solo su no es la ultima opcion
            }
            if(key==32){//si se presiona enter y se guarda la dificultad
                if(y4==140){//si es el primer nivel se usa la clase de nivel1
                    while(control2==false){//para saber si es la primera vez que se muestra la info
                        JOptionPane.showInternalMessageDialog(null, "Para que el cubo salte debes presionar la tecla espacio.\nEvita tocar los obstaculos.\nPresiona ESC para pausar.","NIVEL 1", JOptionPane.INFORMATION_MESSAGE);
                        control2= true;//para que no vuelva a entrar
                    }
                    Nivel1 n1 = new Nivel1(difi,color);
                    marco.dispose();//se quita la ventana para que no haya dos
                    n1.nivel1();
                }
                if(y4==280){//si es el segundo nivel se usa la clase de nivel2
                    while(control3==false){//para saber si es la primera vez que se muestra la info
                        JOptionPane.showInternalMessageDialog(null, "Para que el cohete se impulse hacia arriba debes mantener la tecla espacio.\nAl soltar la tecla espaciadora el cohete aterrizara.\nEvita tocar los obstaculos.\nPresiona ESC para pausar.","NIVEL 2", JOptionPane.INFORMATION_MESSAGE);
                        control3= true;//para que no vuelva a entrar
                    }
                    Nivel2 n2 = new Nivel2(difi,color2);
                    marco.dispose();//se quita la ventana para que no haya dos
                    n2.nivel2();
                }
                if(y4==420){//si es el tecer nivel se usa la clase de nivel3
                    while(control4==false){//para saber si es la primera vez que se muestra la info
                        JOptionPane.showInternalMessageDialog(null, "Para que la araña se teletransporte de arriba a abajo y viceverza se usa la tecla espacio.\nEvita tocar los obstaculos.\nPresiona ESC para pausar.","NIVEL 3", JOptionPane.INFORMATION_MESSAGE);
                        control4= true;//para que no vuelva a entrar
                    }
                    Nivel3 n3 = new Nivel3(difi,color3);
                    marco.dispose();//se quita la ventana para que no haya dos
                    n3.nivel3();
                }
                if(y4==560){//si es la torre se usa la clase de niveltorre
                    while(control5==false){//para saber si es la primera vez que se muestra la info
                        JOptionPane.showInternalMessageDialog(null, "Para comenzar presiona cualquier tecla.\nPara que el cubo se mueva de derecha a izquierda se usan las teclas de flecha derecha e izquierda.\nPara que el cubo salte se presiona primero una tecla de flecha (izquierda, arriba y derecha) posteriormente se presiona las teclas (a y d) para saltar a izquierda o derecha.\nEvita tocar los obstaculos.\nPresiona ESC para pausar.","NIVEL 4", JOptionPane.INFORMATION_MESSAGE);
                        control5= true;//para que no vuelva a entrar
                    }
                    NivelTorre t = new NivelTorre(color);
                    marco.dispose();//se quita la ventana para que no haya dos
                    t.torre();
                }
                
            }
            papel.fillOval(x, y4, 50, 50);//se imprime el circulo
        }
        if(ans==2){//si es el menu de dificultad
            while(control6==false){//para saber si es la primera vez que se muestra la info
                JOptionPane.showInternalMessageDialog(null, "Para moverte por el menu se usan las teclas de flecha arriba y flecha abajo.\nPara seleccionar una opcion se usa el espacio.","Menu dificultad", JOptionPane.INFORMATION_MESSAGE);
                control6= true;//para que no vuelva a entrar
            }
            ImageIcon img2 = new ImageIcon(getClass().getResource("/imagenes/dificultad.png"));
            papel.drawImage(img2.getImage(), 50, 0,700,700, this);
            papel.setColor(Color.RED);//cambiar color
            if(key==38){
                if(y2>150)y2-=150;//si la tecla es la flecha arriba se disminuye y solo su no es la primera opcion
            }
            if(key==40){
                if(y2<450)y2+=150;//si la tecla es la flecha arriba se disminuye y solo su no es la ultima opcion
            }
            if(key==32){//si se presiona enter y se guarda la dificultad
                if(y2==150)difi=1;
                if(y2==300)difi=2;
                if(y2==450)difi=3;
                ImageIcon img = new ImageIcon(getClass().getResource("/imagenes/menu.png"));//imagen del menu principal
                papel.drawImage(img.getImage(), 50, 0,700,700, this);//se dibuja el menu principal
                ans=0;
            }
            papel.fillOval(x, y2, 50, 50);//se imprime el circulo
        }
        if(ans==3){//si es el menu de seleccion
            while(control7==false){//para saber si es la primera vez que se muestra la info
                JOptionPane.showInternalMessageDialog(null, "Para moverte por el menu se usan las teclas de flecha arriba y flecha abajo.\nPara seleccionar una opcion se usa el espacio.","Menu seleccion", JOptionPane.INFORMATION_MESSAGE);
                control7= true;//para que no vuelva a entrar
            }
            ImageIcon img3 = new ImageIcon(getClass().getResource("/imagenes/seleccion.png"));
            papel.drawImage(img3.getImage(), 50, 0,700,720, this);
            papel.setColor(Color.BLUE);//puede ser mayuscula o minuscula pero al ser una enumeracion es mejor en mayuscula
            if(key==38){
                if(y3>120)y3-=100;//si la tecla es la flecha arriba se disminuye y solo si no es la primera opcion
            }
            if(key==40){
                if(y3<520)y3+=100;//si la tecla es la flecha arriba se disminuye y solo si no es la ultima opcion
            }
            if(key==32){//si se presiona enter y se guarda el color para cada nivel
                if(y3==120){color="/imagenes/red.png";color2="/imagenes/redship.png";color3="/imagenes/redspider.png";}//se le asigna un string que contiene la ruta de la imagen a cada color
                if(y3==220){color="/imagenes/green.png";color2="/imagenes/greenship.png";color3="/imagenes/greenspider.png";}//se le asigna un string que contiene la ruta de la imagena a cada color
                if(y3==320){color="/imagenes/yellow.png";color2="/imagenes/yellowship.png";color3="/imagenes/yellowspider.png";}//se le asigna un string que contiene la ruta de la imagena a cada color
                if(y3==420){color="/imagenes/purple.png";color2="/imagenes/purpleship.png";color3="/imagenes/purplespider.png";}//se le asigna un string que contiene la ruta de la imagena a cada color
                if(y3==520){color="/imagenes/white.png";color2="/imagenes/whiteship.png";color3="/imagenes/whitespider.png";}//se le asigna un string que contiene la ruta de la imagena a cada color
                ImageIcon img0 = new ImageIcon(getClass().getResource("/imagenes/menu.png"));//imagen del menu principal
                papel.drawImage(img0.getImage(), 50, 0,700,700, this);//se dibuja el menu principal
                ans=0;
            }
            papel.fillOval(x, y3, 50, 50);//se imprime el circulo
        }
    }
    //estos dos metodos los puse nomas porque al implementar keylistsner era obligatorio ponerlos
    public void keyTyped(KeyEvent event){}
    public void keyReleased(KeyEvent event){}
    
    //metodo para los botones
    public void actionPerformed(ActionEvent event){//event objeto de la clase Actionevent
        Graphics papel = panel.getGraphics();//devuelve un objeto de tipo grafics que se guarda en el objeto papel
        if(inicio == event.getSource()){//esto boton sirve para regresar al menu principal
            ans=0;//se le da el valor de 0
            marco.dispose();//se quita la ventana para que al ir de nuevo a el menu principal no se cree una aparte
            juego();//simplemente se regresa a el principio
        }
        if(salir == event.getSource()){//este boton sirve para finalizar el programa (juego)
            //limpieza
            papel.setColor(Color.BLACK);//cambiar color
            papel.fillRect(0, 0, 800, 700);//imprimir rectangulo
            ImageIcon img4 = new ImageIcon(getClass().getResource("/imagenes/salir.png"));//imagen de despedida
            papel.drawImage(img4.getImage(), 50, 0,700,700, this);
            try{//para parar el tiempo unos cuantos milisegundos
                Thread.sleep(600);
            }catch(InterruptedException e){
                System.out.println("Error"+e);
            }
            exit(0);//terminar el programa
        }
    }
    //clase para que el jpanel inicie ya con el fondo, no tenga que esperar un click
    class Fondo extends javax.swing.JPanel{//funcion creada para que se cambie el fondo del panel
        private java.awt.Image menu;//se crea una variable para la imagen del fondo
        @Override//viene de JFrame por eso se sobreescribio
        public void paint(java.awt.Graphics g){//funcion que recibe graficos y sirve para cambiar fondos
            menu = new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu.png")).getImage();
            g.drawImage(menu, 50, 0, 700, 700, this);//se dibuja la imagen
            setOpaque(false);//para que se vea
            super.paint(g);//se usa el metodo de la clase padre con super
        }
    }
}
