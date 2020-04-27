import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

public class Game extends JFrame implements Runnable{
    public final static int WINDOW_WIDTH = 1200;
    public final static int WINDOW_HEIGHT = 820;
    public static int BOARD_WIDTH, BOARD_HEIGHT;
    public static int BOARD_X, BOARD_Y;

    private int leftTime = 30;
    private int maxTime = 30;

    private boolean running = true;

    private InputFileReader config;
    private GamePanel gamePanel;
    private KeysListener keysListener;
    private Player leftPlayer, rightPlayer;


    public Game(){
        customizeWindow();
        config = new InputFileReader("src/main/resources/config/ConfigFile.txt");
        BOARD_WIDTH = config.getBoardWidth();
        BOARD_HEIGHT = config.getBoardHeight();
        BOARD_X  = 340 + (500 - BOARD_WIDTH)/2;
        BOARD_Y = 150 + (500 - BOARD_HEIGHT)/2;

        keysListener = new KeysListener();
        leftPlayer = new LeftPlayer(config.getMaxNumberOfShots(),
                "src/main/resources/img/leftTanks.png", "src/main/resources/img/leftBullet.png", keysListener);
        rightPlayer = new RightPlayer(config.getMaxNumberOfShots(),
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


        if ((maxTime - leftTime) % config.getTimeToIncreaseBulletsSpeed() == 0 && (maxTime - leftTime) != 0) {
            leftPlayer.speedUpBullets(config.getPercentageIncreaseInBulletsSpeed());
            rightPlayer.speedUpBullets(config.getPercentageIncreaseInBulletsSpeed());
        }

        leftPlayer.updateShots();
        rightPlayer.updateShots();

        if(leftTime == 0 )
            running = false;

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
        g.fillRect(0, 750, getWidth(), 20);

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
}
