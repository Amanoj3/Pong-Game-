import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class PongPanel extends JPanel implements KeyListener, ActionListener {

    private final int player1X;
    private final int player2X;
    private int player1Y;
    private int player2Y;
    private final Timer timer;
    private boolean play;
    private final Set<Integer> heldKeys;
    private int ballX;
    private int ballY;
    private double ballXdir;
    private double ballYdir;

    PongPanel() {
        heldKeys = new HashSet<>();
        play = false;

        //paddle coordinates
        player1X = 10;
        player2X = 680;
        player1Y = 240;
        player2Y = 240;

        //ball info
        ballX = 15;
        ballY = 240;
        ballXdir = 1;
        ballYdir = -2;

        int delay = 8;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        requestFocus(true); // this ensures that the keyListener will work every time you run the program
        //background color
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //line in the middle
        g.setColor(Color.white);
        g.drawLine(346,0,346,592);

        //paddle #1
        g.setColor(Color.green);
        g.fillRect(player1X,player1Y,5,100);

        //paddle #2
        g.setColor(Color.red);
        g.fillRect(player2X,player2Y,5,100);

        //ball
        g.setColor(Color.orange);
        g.fillOval(ballX,ballY,20,20);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            ballX += ballXdir;
            ballY += ballYdir;
            if (ballY < 0 || ballY > 540) {
                ballYdir = -ballYdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        heldKeys.add(e.getKeyCode());
        //paddle movements for paddle #2
        if (heldKeys.contains(KeyEvent.VK_UP)) {
            if (player2Y <= 3){
                player2Y = 3;
            }
            else {
                moveUp(true);
            }
        }

        if (heldKeys.contains(KeyEvent.VK_DOWN)) {
            if (player2Y >= 460) {
                player2Y = 460;
            }
            else {
                moveDown(true);
            }
        }

        if (heldKeys.contains(KeyEvent.VK_W)) {
            if (player1Y <=3) {
                player1Y = 3;
            }
            else {
                moveUp(false);
            }
        }

        if (heldKeys.contains(KeyEvent.VK_S)) {
            if (player1Y >= 460) {
                player1Y = 460;
            }
            else {
                moveDown(false);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        heldKeys.remove(e.getKeyCode());
    }

    void moveUp(boolean player2Moving) {
        play = true;
        if (player2Moving) {
            player2Y = player2Y - 20;
        }
        else {
            player1Y = player1Y - 20;
        }
    }

    void moveDown(boolean player2Moving) {
        play = true;
        if (player2Moving) {
            player2Y = player2Y + 20;
        }
        else {
            player1Y = player1Y + 20;
        }
    }

}