import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class Game extends JPanel{
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

    // la cible recherche genre la noiurriture que le serpent dois manger
    Tile cible;

    // creation du constructeur en quelque sorte on creer le panel
    public Game(int boardWith,int boarHeigth){
        this.boardWith=boardWith;
        this.boarHeigth=boarHeigth;
        setPreferredSize(new Dimension(this.boardWith,this.boarHeigth));
        setBackground(Color.BLACK);
        debutSerpent=new Tile(5, 5);
        cible=new Tile(10, 10);
    }
    
    // on vas tarcer le rectangle de couleur verte
    public void drawRectangle(Graphics g){
        // on vas dessiner la grid
        for (int i = 0; i < boardWith/tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize,boarHeigth);
            g.drawLine(0, i*tileSize, boardWith,i*tileSize);
        }

        // ici on dessine la cible
        g.setColor(Color.red);                                                            
        g.fillRect(cible.x*tileSize, cible.y*tileSize, tileSize, tileSize);

        //ici on dessine le serpent
        g.setColor(Color.green);                                                            //met la couleur du rectangle en vert
        g.fillRect(debutSerpent.x*tileSize, debutSerpent.y*tileSize, tileSize, tileSize);  //permet de tracer le rectangle avec des dimension de tile et palcer ȧla position debutSerpent.x*tileSize en x, debutSerpent.y*tileSize en y
    }

    //cette fonction initailiser le fonctionnement de la biblitheque graphique
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawRectangle(g);
    }
}