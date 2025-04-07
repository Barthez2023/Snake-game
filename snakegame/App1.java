import javax.swing.*;
public class App1 {
    public static void main(String[] args) {
        JFrame frame=new JFrame("Game");
        frame.setVisible(true);
        frame.setSize(600,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //on creer l'instance de notre jeu dans la classe main
        Game game=new Game(600, 600);
        frame.add(game); //add le panel dans le frame
        frame.pack();    //adapte notre JFrame a la taille de notre panel
        game.requestFocus();                   //sert à donner le focus clavier à un composant, c’est-à-dire Elle demande que ce composant(notre JFrame) reçoive les événements clavier.
    }

    
}