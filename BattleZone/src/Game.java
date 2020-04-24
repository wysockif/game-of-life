import javax.swing.*;
import java.awt.*;

public class Game extends JFrame implements Runnable{
    public static int WINDOW_WIDTH = 1200;
    public static int WINDOW_HEIGHT = 820;
    public int  tempW = 750, tempH = 550;
    public int tempX = 340 + (500 - tempW)/2;
    public int tempY = 150 + (500 - tempH)/2;

    private boolean running = true;



    private JLabel leftPlayerName, rightPlayerName;
    private GamePanel gamePanel;

    public Game(){
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLocationRelativeTo(null);

        gamePanel = new GamePanel(this);

        leftPlayerName = new JLabel("Imię gracza", JLabel.CENTER);
//        leftPlayerName.setFont(new Font("MyFont", Font.BOLD, 20));
        leftPlayerName.setBounds(5, 5, 390, 100);
//        leftPlayerName.setForeground(Color.BLACK);

        rightPlayerName = new JLabel("Imię gracza", JLabel.CENTER);
//        rightPlayerName.setFont(new Font("MyFont", Font.BOLD, 20));
        rightPlayerName.setBounds(790, 5, 390, 100);
//        rightPlayerName.setForeground(Color.BLACK);

        gamePanel.add(leftPlayerName);
        gamePanel.add(rightPlayerName);
        add(gamePanel);


        setVisible(true);
    }


    public static void main(String[] args){
        Thread gameThread = new Thread();
        Game game = new Game();
        gameThread.start();
    }



    private void update(){}

    private void updateOncePerSecond(){

    }
    public void repaint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);

        g.setColor(Color.white);
        g.fillRect(tempX, tempY, tempW, tempH);

        g.fillRect(5, 5, 390, 100);
//        g.fillRect(400, 5, 385, 100);
        g.fillRect(790, 5, 390, 100);

        g.setColor(Color.darkGray);
        g.fillRect(340, tempY + tempH + 10, 500, 70);

    }

    private void render(){
        gamePanel.repaint();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1000000000;
        double changeInSeconds = 0;
        double changeInSecondsFPS = 0;


        while (running) {
            long now = System.nanoTime();
            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            changeInSecondsFPS += (now - lastTime) / nanoSecondConversion;
            render();

            if (changeInSecondsFPS > 1.0 / 60.0) {
                update();
                changeInSecondsFPS = 0;
            }

            if(changeInSeconds >= 1.0) {
                updateOncePerSecond();
                changeInSeconds = 0;
            }
            lastTime = now;
        }
    }



}
