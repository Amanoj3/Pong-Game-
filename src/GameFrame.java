import javax.swing.*;

public class GameFrame extends JFrame {
    private PongPanel gamePanel = new PongPanel();
    GameFrame() {
        this.setBounds(10,10,710,600);
        this.setTitle("Pong");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gamePanel);
    }
}
