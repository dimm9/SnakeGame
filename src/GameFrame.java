import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        GamePanel panel = new GamePanel();
        this.add(panel);
        this.setTitle("snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); // for our frame to appear in the middle of the screen
    }
}
