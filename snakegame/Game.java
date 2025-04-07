import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener{
    int boardWith,boarHeigth;
    int tileSize=25;
    private class Tile {     // cette classe nous permet d'initialiser le creation dune tuile genre un carreau
        int x,y;
        Tile(int x,int y){
            this.x=x;
            this.y=y;
        }
    }

    //debut du sertpent genre la tete du serpent
    Tile debutSerpent;
    ArrayList<Tile> corpserpent;

    // la cible recherche genre la noiurriture que le serpent dois manger
    Tile cible;
    Random random;              //c'est un object random qui vas permetre de deplacer la cible

    //le Timer est  un moyen de programmer une action à faire après un certain délai, ou à intervalles réguliers.
    Timer gameloop;
    int newX,newY;
    boolean gameOver=false;

    // creation du constructeur en quelque sorte on creer le panel
    public Game(int boardWith,int boarHeigth){
        this.boardWith=boardWith;
        this.boarHeigth=boarHeigth;
        setPreferredSize(new Dimension(this.boardWith,this.boarHeigth));
        setBackground(Color.BLACK);
        addKeyListener(this);                     //ajout l'evenement listener au panel
        setFocusable(true);             //Autorise à recevoir le focus


        debutSerpent=new Tile(5, 5);
        corpserpent=new ArrayList<Tile>();     //permert de stocker le corps du serpenty au fur et a mesure qu'il touche la cible
        cible=new Tile(10, 10);
        random=new Random();
        palceCible();                   //cette fonction permettra de deplacer la cible
        
        //initialiasation de valeurs  de x te y
        newX=0;
        newY=0;
        
        gameloop=new Timer(100, this);   // apres chaque 100ms une instruction sera execute dans notre panel
        gameloop.start();
    }
    
    // on vas tarcer le rectangle de couleur verte
    public void drawRectangle(Graphics g){
        // on vas dessiner la grid
        /*for (int i = 0; i < boardWith/tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize,boarHeigth);
            g.drawLine(0, i*tileSize, boardWith,i*tileSize);
        } */ //code repere 1.1.2

        // ici on dessine la cible
        g.setColor(Color.red);                                                            
        //g.fillRect(cible.x*tileSize, cible.y*tileSize, tileSize, tileSize);   //code repere 1.1.2
        g.fill3DRect(cible.x*tileSize, cible.y*tileSize, tileSize, tileSize,true);

        //ici on dessine le serpent
        g.setColor(Color.green);                                                            //met la couleur du rectangle en vert
        //g.fillRect(debutSerpent.x*tileSize, debutSerpent.y*tileSize, tileSize, tileSize);  //permet de tracer le rectangle avec des dimension de tile et palcer ȧla position debutSerpent.x*tileSize en x, debutSerpent.y*tileSize en y   //code repere 1.1.2
        g.fill3DRect(debutSerpent.x*tileSize, debutSerpent.y*tileSize, tileSize, tileSize,true);

        //ici on dessine le corp du serpent
        for (int i = 0; i <corpserpent.size() ; i++) {
            Tile body=corpserpent.get(i);
            g.setColor(Color.green);                                                           
            //g.fillRect(body.x*tileSize, body.y*tileSize, tileSize, tileSize);    //code repere 1.1.2
            g.fill3DRect(body.x*tileSize, body.y*tileSize, tileSize, tileSize,true);  

        }

        // ici on ecrit le score
        g.setFont(new Font("Arial", Font.PLAIN, 17));
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over : "+String.valueOf(corpserpent.size()), tileSize-16, tileSize);
            
        }
        else{
            g.drawString("Score :"+String.valueOf(corpserpent.size()), tileSize-16, tileSize);
        }





        
    }

    //cette fonction initailiser le fonctionnement de la biblitheque graphique
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawRectangle(g);
    }

    public void palceCible(){
        cible.x=random.nextInt(boardWith/tileSize);        //donne un numbre entre 0 et 24 dans notre cas car on a 600/25
        cible.y=random.nextInt(boarHeigth/tileSize);       //donne un numbre entre 0 et 24 dans notre cas car on a 600/25
    }


    public boolean collision(Tile tile1,Tile tile2){                          //cette fonction verifie une quelconque colision entre le serpent et la cible

        if (tile1.x==tile2.x && tile1.y==tile2.y){
            return true;
        }
        return false;
    }
    public void move(){
        if (collision(debutSerpent, cible)==true) {
            corpserpent.add(new Tile(cible.x, cible.y));
            palceCible();
        }

        //deplacement du corp entier du serpent lorsqu'il atteint la cible
        for (int i = corpserpent.size()-1; i >=0; i--) {
            Tile newpartieSerpent=corpserpent.get(i);
            if (i==0) {
                newpartieSerpent.x=debutSerpent.x;
                newpartieSerpent.y=debutSerpent.y;
            }
            else{
                Tile partieAvant=corpserpent.get(i-1);
                newpartieSerpent.x=partieAvant.x;
                newpartieSerpent.y=partieAvant.y;
            }
        }




        //debutSerpent comme commencement
        debutSerpent.x+=newX;
        debutSerpent.y+=newY;

        //game over condition
        for (int i = 0; i < corpserpent.size(); i++) {
            Tile partieSerpent=corpserpent.get(i);
            if (collision(partieSerpent, debutSerpent)==true) {
                gameOver=true;
            }
        }
        if (debutSerpent.x*tileSize<0 || debutSerpent.x*tileSize>=boardWith || debutSerpent.y*tileSize<0 || debutSerpent.y*tileSize>=boarHeigth) {
            gameOver=true;
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // Ce qu'on  veux faire à chaque tick du timer
        move();
        repaint();                                          // cette fonction vas appeler la fonction draw
        
        if (gameOver==true) {
            gameloop.stop();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==e.VK_UP && newY!=1 ) {
            newX=0;
            newY=-1;
        }
        else if (e.getKeyCode()==e.VK_DOWN && newY!=-1) {
            newX=0;
            newY=1;
        }
        else if (e.getKeyCode()==e.VK_LEFT && newX!=1) {
            newX=-1;
            newY=0;
        }
        else if (e.getKeyCode()==e.VK_RIGHT && newX!=-1) {
            newX=1;
            newY=0;
        }
    }


    //ces fonctions ne seront pas utiliser elles sont la a cause de l'inplementation de l'interfaces keypressed
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}