import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame() {
        this.setBounds(10,10,710,600);
        this.setTitle("Pong");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PongPanel gamePanel = new PongPanel();
        this.add(gamePanel);
    }

}