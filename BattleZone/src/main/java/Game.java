import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Game extends JFrame implements Runnable{
    public final static int WINDOW_WIDTH = 1200;
    public final static int WINDOW_HEIGHT = 820;
    public static int BOARD_WIDTH, BOARD_HEIGHT;
    public static int BOARD_X, BOARD_Y;

    private int leftTime = 30;
    private int maxTime = 30;
    private boolean running = true;


    private GamePanel gamePanel;
    private KeysListener keysListener;
    private Player leftPlayer, rightPlayer;


    public Game(){
        customizeWindow();
        InputFileReader inputFileReader = new InputFileReader("src/main/resources/config/ConfigFile.txt");
        BOARD_WIDTH = inputFileReader.getBoardWidth();
        BOARD_HEIGHT = inputFileReader.getBoardHeight();
        BOARD_X  = 340 + (500 - BOARD_WIDTH)/2;
        BOARD_Y = 150 + (500 - BOARD_HEIGHT)/2;

        keysListener = new KeysListener();
        leftPlayer = new LeftPlayer(inputFileReader.getMaxNumberOfShots(),
                "src/main/resources/img/leftTanks.png", "src/main/resources/img/leftBullet.png", keysListener);
        rightPlayer = new RightPlayer(inputFileReader.getMaxNumberOfShots(),
                "src/main/resources/img/rightTanks.png", "src/main/resources/img/rightBullet.png", keysListener);

        gamePanel = new GamePanel(this);
        gamePanel.addKeyListener(keysListener);


        add(gamePanel);
        setVisible(true);
    }

    private void customizeWindow(){
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }


    public static void main(String[] args){
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }



    private void updateFrame(){
        leftPlayer.updateTank();
        rightPlayer.updateTank();

        rightPlayer.checkIfShot();
        leftPlayer.checkIfShot();

        Iterator<Bullet> it = leftPlayer.getBullets().iterator();
        while( it.hasNext() ) {
            Bullet b = it.next();
            b.updateBullet();
        }

        it = rightPlayer.getBullets().iterator();
        while( it.hasNext() ) {
            Bullet b = it.next();
            b.updateBullet();
        }


        leftPlayer.removeUnwantedBullets();
        rightPlayer.removeUnwantedBullets();

        gamePanel.getLeftShotsLabel().setText("Pociski: "+ leftPlayer.getBullets().size() + "/" + leftPlayer.getMaxNumberOfShots());
        gamePanel.getRightShotsLabel().setText("Pociski: "+ rightPlayer.getBullets().size() + "/" + rightPlayer.getMaxNumberOfShots());


    }
    private void updateOncePerSecond(){
        leftTime--;
        gamePanel.getTimeLabel().setText("Pozostały czas: " + leftTime);


        if (leftTime <= 5)
            gamePanel.getTimeLabel().setForeground(Color.RED);
//        if (leftTime == 0) {
//            gamePanel.getTimeLabel().saveScreen("src/zdj", "png");
//            gamePanel.getTimeLabel().saveBoard("src/board", "png");
//            running = false;
//        }

        if (maxTime - leftTime % 5 == 0 && maxTime - leftTime != 0) {
            leftPlayer.speedUpBullets(15);
            rightPlayer.speedUpBullets(15);
        }

        leftPlayer.updateShots();
        rightPlayer.updateShots();

    }
    public void repaint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);

        g.setColor(Color.white);
        g.fillRect(BOARD_X, BOARD_Y, BOARD_WIDTH, BOARD_HEIGHT);

        g.fillRect(5, 5, 390, 100);

        g.fillRect(790, 5, 390, 100);

        g.setColor(Color.darkGray);
        g.fillRect(340, BOARD_Y + BOARD_HEIGHT + 10, 500, 70);

        leftPlayer.drawTank(g);
        leftPlayer.drawBullets(g);
        rightPlayer.drawTank(g);
        rightPlayer.drawBullets(g);


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
                updateFrame();
                changeInSecondsFPS = 0;
            }

            if(changeInSeconds >= 1.0) {
                updateOncePerSecond();
                changeInSeconds = 0;
            }
            lastTime = now;
        }
    }

    public static class InputReader {

    }
}
