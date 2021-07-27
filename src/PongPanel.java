import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
//TO DO (AS OF 7/27/21 12:40 AM - WORK ON THE PHYSICS BEHIND LETTING THE BALL BOUNCE)
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
    private int greenScore;
    private int redScore;
    private String spaceToBegin;


    PongPanel() {
        heldKeys = new HashSet<>();
        play = false;

        spaceToBegin = "Press space to start.";

        //paddle coordinates
        player1X = 10; // green paddle
        player2X = 680; //red paddle
        player1Y = 240;
        player2Y = 240;

        //ball info
        ballX = 55;
        ballY = 240;
        ballXdir = 2;
        ballYdir = -4;

        //scores
        greenScore = 0;
        redScore = 0;

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

        //green paddle's score
        g.setColor(Color.blue);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString(String.valueOf(greenScore), 173, 20);

        g.setColor(Color.blue);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString(String.valueOf(redScore),519,20);

        // press space to begin a round
        if (!play) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString(spaceToBegin, 246, 220);
        }
        if (play) {
            g.drawString("",246,220);
        }


        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballX, ballY, 20,20)
                    .intersects(new Rectangle(player1X, player1Y,5,100)) || new Rectangle(ballX, ballY, 20,20)
                    .intersects(new Rectangle(player2X, player2Y,5,100))) { // if the ball hits either paddle, it will bounce
                /*if (new Rectangle(ballX, ballY, 20,20)
                        .intersects(new Rectangle(player1X, player1Y,5,100))) {
                    System.out.println("Ball is at: " + ballY);
                    System.out.println("Collision at player1Y: " + player1Y);
                }
                if (new Rectangle(ballX, ballY, 20,20)
                        .intersects(new Rectangle(player2X, player2Y,5,100))) {
                    System.out.println("Ball is at: " + ballY);
                    System.out.println("Collision at player2Y: " + player2Y);
                }*/
                ballXdir = -ballXdir;
                ballXdir = ballXdir*1.1;
                ballYdir = ballYdir*1.1;
            }
            ballX += ballXdir;
            ballY += ballYdir;
            if (ballY < 0 || ballY > 540) { // the ball bounces when it hits the top or the bottom of the window
                ballYdir = -ballYdir;
            }
            if (ballX < 0) {
                play = false;
                ballX = 55;
                ballY = 240;
                ballXdir = 2;
                ballYdir = -4;
                redScore++;
            }
            if (ballX > 712) {
                play = false;
                ballX = 450;
                ballY = 240;
                ballXdir = -2;
                ballYdir = 4;
                greenScore++;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            play = true;
        }
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
        if (player2Moving) {
            player2Y = player2Y - 20;
            System.out.println("player2Y loc: " + player2Y);
        }
        else {
            player1Y = player1Y - 20;
            System.out.println("player1Y loc: " + player1Y);
        }
    }

    void moveDown(boolean player2Moving) {
        if (player2Moving) {
            player2Y = player2Y + 20;
            System.out.println("player2Y loc: " + player2Y);
        }
        else {
            player1Y = player1Y + 20;
            System.out.println("player1Y loc: " + player1Y);
        }
    }
}