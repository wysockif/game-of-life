import static java.lang.String.*;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

public class Game extends JFrame implements Runnable {
    public final static int WINDOW_WIDTH = 1200;
    public final static int WINDOW_HEIGHT = 820;
    public static int BOARD_WIDTH, BOARD_HEIGHT;
    public static int BOARD_X, BOARD_Y;

    private int leftTime = 50;
    private int maxTime = 50;
    private int timeToGenerateNewCells, timeToGenerateKidsCells;
    private int timeToIncreaseCellsValues, timeToChangeBSpeedAndCSize;

    private boolean running = true;

    private InputFileReader config;
    private GamePanel gamePanel;
    private KeysListener keysListener;
    private Player leftPlayer, rightPlayer;


    public Game() {
        customizeWindow();
        assignValues();

        BOARD_WIDTH = config.getBoardWidth();
        BOARD_HEIGHT = config.getBoardHeight();
        BOARD_X = 340 + (500 - BOARD_WIDTH) / 2;
        BOARD_Y = 150 + (500 - BOARD_HEIGHT) / 2;

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

    private void assignValues() {
        config = new InputFileReader("src/main/resources/config/ConfigFile.txt");
        timeToGenerateKidsCells = config.getTimeToGenerateKidsCells();
        timeToGenerateNewCells = config.getTimeToGenerateNewCells();
        timeToChangeBSpeedAndCSize = config.getTimeToChangeBulletsSpeedAndCellsSize();
        timeToIncreaseCellsValues = config.getTimeToIncreaseCellsValues();

    }

    private void customizeWindow() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }


    private void updateFrame() {
        leftPlayer.updateTank();
        rightPlayer.updateTank();

        rightPlayer.checkIfShot();
        leftPlayer.checkIfShot();

        Iterator<Bullet> it = leftPlayer.getBullets().iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.updateBullet();
        }

        it = rightPlayer.getBullets().iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.updateBullet();
        }


        leftPlayer.removeUnwantedBullets();
        rightPlayer.removeUnwantedBullets();

        gamePanel.getLeftShotsLabel().setText("Pociski: " + leftPlayer.getBullets().size() + "/" + leftPlayer.getMaxNumberOfShots());
        gamePanel.getRightShotsLabel().setText("Pociski: " + rightPlayer.getBullets().size() + "/" + rightPlayer.getMaxNumberOfShots());


    }

    private void updateOncePerSecond() {
        updateTimeAndInfoLabels();

        if (leftTime <= 5)
            gamePanel.getTimeLabel().setForeground(Color.RED);


        if (timeToChangeBSpeedAndCSize == 0 && (maxTime - leftTime) != 0) {
            leftPlayer.speedUpBullets(config.getPercentageIncreaseInBulletsSpeed());
            rightPlayer.speedUpBullets(config.getPercentageIncreaseInBulletsSpeed());
            timeToChangeBSpeedAndCSize = config.getTimeToChangeBulletsSpeedAndCellsSize();
        }

        if (timeToIncreaseCellsValues == 0 && (maxTime - leftTime) != 0) {
            // zwiększenie wartości komórek
            timeToGenerateKidsCells = config.getTimeToIncreaseCellsValues();
        }

        if (timeToIncreaseCellsValues == 0 && (maxTime - leftTime) != 0) {
            int percent = config.getPercentageDecreaseInCellsSize();
            // zmniejszenie rozmiaru komórek o percent
            timeToIncreaseCellsValues = config.getTimeToIncreaseCellsValues();
        }

        if (timeToGenerateKidsCells == 0 && (maxTime - leftTime) != 0) {
            // pojawienie się komórek dzieci
            timeToGenerateKidsCells = config.getTimeToGenerateKidsCells();
        }

        if (timeToGenerateNewCells == 0 && (maxTime - leftTime) != 0) {
            // generowanie nowych komórek
            timeToGenerateNewCells = config.getTimeToGenerateNewCells();
        }

        leftPlayer.updateShots();
        rightPlayer.updateShots();

        if (leftTime == 0)
            running = false;

    }

    private void updateTimeAndInfoLabels() {
        leftTime--;
        timeToGenerateNewCells--;
        timeToGenerateKidsCells--;
        timeToIncreaseCellsValues--;
        timeToChangeBSpeedAndCSize--;

        gamePanel.getTimeLabel().setText("Pozostały czas: " + leftTime);

        String space = "                ";
        int percentBullets = config.getPercentageIncreaseInBulletsSpeed();
        int percentCells = config.getPercentageDecreaseInCellsSize();

        gamePanel.getInfoLabel().setText(format("Nowe komórki za %03ds", timeToGenerateNewCells) + space
                + format("Komórki dzieci za %03ds", timeToGenerateKidsCells) + space
                + format("Wzmocnienie komórek za %03ds", timeToIncreaseCellsValues) + space
                + format("Zmniejszenie komórek za %03ds o %3d%%", timeToChangeBSpeedAndCSize, percentCells) + space
                + format("Przyspieszenie pocisków za %03ds o %3d%%", timeToChangeBSpeedAndCSize, percentBullets));
    }

    public void repaint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
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

    private void render() {
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

            if (changeInSeconds >= 1.0) {
                updateOncePerSecond();
                changeInSeconds = 0;
            }
            lastTime = now;
        }
    }
}
