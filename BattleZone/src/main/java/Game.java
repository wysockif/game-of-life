import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import static java.awt.Font.BOLD;
import static java.lang.String.format;
import static javax.swing.SwingConstants.CENTER;

public class Game extends JFrame implements Runnable {
    public final static int WINDOW_WIDTH = 1200;
    public final static int WINDOW_HEIGHT = 820;
    public static int BOARD_WIDTH, BOARD_HEIGHT;
    public static int BOARD_X, BOARD_Y;

    private int maxScore;
    private int leftTime, maxTime;
    private int seconds;
    private int timeToGenerateNewCells, timeToGenerateKidsCells;
    private int timeToIncreaseCellsValues, timeToChangeBSpeedAndCSize;

    private boolean running = false;
    private boolean isLastTime;
    private boolean isStarted;
    private boolean canClearLabel;

    private InputFileReader config;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private ResultsPanel resultsPanel;
    private KeysListener keysListener;
    private Player leftPlayer, rightPlayer;
    private Cells cells;

    private static JPanel cardPanel;
    private static CardLayout card;
    public static Thread gameThread;


    public Game() {
        customizeWindow();
        card = new CardLayout();
        cardPanel = new JPanel(card);

        menuPanel = new MenuPanel(this);
        cardPanel.add(menuPanel);

        keysListener = new KeysListener();
        gamePanel = new GamePanel(this);
        cardPanel.addKeyListener(keysListener);
        cardPanel.setFocusable(true);
        gamePanel.addKeyListener(keysListener);
        cardPanel.add(gamePanel);

        resultsPanel = new ResultsPanel(this);
        cardPanel.add(resultsPanel);

        cells = new Cells(this);

        add(cardPanel);
        setVisible(true);
    }

    private void assignValues() {
        timeToGenerateKidsCells = config.getTimeToGenerateKidsCells();
        timeToGenerateNewCells = config.getTimeToGenerateNewCells();
        timeToChangeBSpeedAndCSize = config.getTimeToChangeBulletsSpeedAndCellsSize();
        timeToIncreaseCellsValues = config.getTimeToIncreaseCellsValues();

    }

    private void customizeWindow() {
        setTitle("BattleZone");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("/img/icon.png").getImage());
        setCloseOperation();
    }

    private void setCloseOperation() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String[] ObjButtons = {"Tak", "Nie"};

                int PromptResult = JOptionPane.showOptionDialog(null, "Czy na pewno chcesz zakończyć?",
                        "Potwierdzenie wyjścia", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }


    public static void main(String[] args) {
        Game game = new Game();
        gameThread = new Thread(game);
    }


    private void updateFrame() {
        if (isLastTime) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            running = false;
            card.last(cardPanel);
        }

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

        cells.checkIfHit(leftPlayer);
        cells.checkIfHit(rightPlayer);

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

            int percent = config.getPercentageDecreaseInCellsSize();
            cells.reduceSize(percent);
            timeToChangeBSpeedAndCSize = config.getTimeToChangeBulletsSpeedAndCellsSize();
        }

        if (timeToIncreaseCellsValues == 0 && (maxTime - leftTime) != 0) {
            cells.increaseValues();
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

        if (canClearLabel) {
            seconds++;
        }

        if (canClearLabel && seconds == 3) {
            gamePanel.getLeftNewScoreLabel().setText("");
            gamePanel.getRightNewScoreLabel().setText("");
            canClearLabel = false;
            seconds = 0;
        }

        if (leftTime == 0 || leftPlayer.getPointsGained() >= maxScore || rightPlayer.getPointsGained() >= maxScore) {
            running = false;
            if (leftPlayer.getPointsGained() == rightPlayer.getPointsGained())
                prepareResults(leftPlayer, rightPlayer,
                        gamePanel.getLeftPlayerName().getText(), gamePanel.getRightPlayerName().getText(), Results.TIE);
            else if (leftPlayer.getPointsGained() > rightPlayer.getPointsGained())
                prepareResults(leftPlayer, rightPlayer,
                        gamePanel.getLeftPlayerName().getText(), gamePanel.getRightPlayerName().getText(), Results.LEFT_TIME_IS_UP);
            else if (leftPlayer.getPointsGained() < rightPlayer.getPointsGained())
                prepareResults(leftPlayer, rightPlayer,
                        gamePanel.getLeftPlayerName().getText(), gamePanel.getRightPlayerName().getText(), Results.RIGHT_TIME_IS_UP);
        }

        if (leftPlayer.getPointsGained() >= maxScore) {
            running = false;
            prepareResults(leftPlayer, rightPlayer,
                    gamePanel.getLeftPlayerName().getText(), gamePanel.getRightPlayerName().getText(), Results.LEFT_MAX_SCORE);
        } else if (rightPlayer.getPointsGained() >= maxScore) {
            running = false;
            prepareResults(leftPlayer, rightPlayer,
                    gamePanel.getLeftPlayerName().getText(), gamePanel.getRightPlayerName().getText(), Results.RIGHT_MAX_SCORE);
        }
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

        gamePanel.getInfoLabel().setText(format("Nowe komórki za %02ds", timeToGenerateNewCells) + space
                + format("Komórki dzieci za %02ds", timeToGenerateKidsCells) + space
                + format("Wzmocnienie komórek za %02ds", timeToIncreaseCellsValues) + space
                + format("Zmniejszenie komórek za %02ds o %02d%%", timeToChangeBSpeedAndCSize, percentCells) + space
                + format("Przyspieszenie pocisków za %02ds o %02d%%", timeToChangeBSpeedAndCSize, percentBullets));
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

        if (!isLastTime)
            cells.drawTipBar(g);

        leftPlayer.drawTank(g);
        leftPlayer.drawBullets(g);
        rightPlayer.drawTank(g);
        rightPlayer.drawBullets(g);

        if (isStarted)
            cells.paintCells(g);
    }

    private void render() {
        gamePanel.repaint();
    }

    public void closeStartMenu() {
        assignValues();
        BOARD_WIDTH = config.getBoardWidth();
        BOARD_HEIGHT = config.getBoardHeight();
        BOARD_X = 340 + (500 - BOARD_WIDTH) / 2;
        BOARD_Y = 150 + (500 - BOARD_HEIGHT) / 2;

        leftPlayer = new LeftPlayer(config.getMaxNumberOfShots(), "/img/leftTanks.png", "/img/leftBullet.png", keysListener);
        rightPlayer = new RightPlayer(config.getMaxNumberOfShots(), "/img/rightTanks.png", "/img/rightBullet.png", keysListener);

        gamePanel.getLeftPlayerName().setText(menuPanel.getNamePlayer1());
        gamePanel.getRightPlayerName().setText(menuPanel.getNamePlayer2());
        maxTime = leftTime = menuPanel.getGameTime();
        maxScore = menuPanel.getGameScore();

        updateLabels();

        card.next(cardPanel);
        running = true;
        gameThread.start();

    }

    private void updateLabels() {
        String space = "                ";
        int percentBullets = config.getPercentageIncreaseInBulletsSpeed();
        int percentCells = config.getPercentageDecreaseInCellsSize();
        gamePanel.getInfoLabel().setText(format("Nowe komórki za %02ds", timeToGenerateNewCells) + space
                + format("Komórki dzieci za %02ds", timeToGenerateKidsCells) + space
                + format("Wzmocnienie komórek za %02ds", timeToIncreaseCellsValues) + space
                + format("Zmniejszenie komórek za %02ds o %02d%%", timeToChangeBSpeedAndCSize, percentCells) + space
                + format("Przyspieszenie pocisków za %02ds o %02d%%", timeToChangeBSpeedAndCSize, percentBullets));


        gamePanel.getTimeLabel().setText("Pozostały czas: " + leftTime);
        gamePanel.getLeftScoreLabel().setText("Zdobyte punkty: 0/" + maxScore);
        gamePanel.getRightScoreLabel().setText("Zdobyte punkty: 0/" + maxScore);
        gamePanel.getLeftShotsLabel().setText("Pociski: 0/" + config.getMaxNumberOfShots());
        gamePanel.getRightShotsLabel().setText("Pociski: 0/" + config.getMaxNumberOfShots());
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1000000000;
        double changeInSeconds = 0;
        double changeInSecondsFPS = 0;
        gamePanel.countDown();
        cells.createCells();


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

    public void refreshScores(Cell cell, Player player) {
        int value = cell.getGreatestValue();
        int inheritance = cell.getInheritance();

        player.addPointsGained(value + inheritance);

        if (player instanceof LeftPlayer) {
            if (inheritance != 0) {
                gamePanel.getLeftNewScoreLabel().setForeground(Color.red);
                gamePanel.getLeftNewScoreLabel().setText("+" + value + " +" + inheritance);
            } else {
                gamePanel.getLeftNewScoreLabel().setForeground(Color.blue);
                gamePanel.getLeftNewScoreLabel().setText("+" + value);
            }
            gamePanel.getLeftScoreLabel().setText("Zdobyte punkty: " + leftPlayer.getPointsGained());


            if (cell.isArmageddon()) {
                isLastTime = true;
                gamePanel.getLeftNewScoreLabel().setForeground(Color.red);
                gamePanel.getLeftNewScoreLabel().setText("ARMAGEDON!");

                prepareResults(leftPlayer, rightPlayer,
                        gamePanel.getLeftPlayerName().getText(), gamePanel.getRightPlayerName().getText(), Results.LEFT_ARMAGEDDON);

            }

        } else if (player instanceof RightPlayer) {

            if (inheritance != 0) {
                gamePanel.getRightNewScoreLabel().setForeground(Color.red);
                gamePanel.getRightNewScoreLabel().setText("+" + value + " +" + inheritance);
            } else {
                gamePanel.getRightNewScoreLabel().setForeground(Color.blue);
                gamePanel.getRightNewScoreLabel().setText("+" + value);
            }
            gamePanel.getRightScoreLabel().setText("Zdobyte punkty: " + rightPlayer.getPointsGained());

            if (cell.isArmageddon()) {
                isLastTime = true;
                gamePanel.getRightNewScoreLabel().setForeground(Color.red);
                gamePanel.getRightNewScoreLabel().setText("ARMAGEDON!");
                prepareResults(leftPlayer, rightPlayer,
                        gamePanel.getLeftPlayerName().getText(), gamePanel.getRightPlayerName().getText(), Results.RIGHT_ARMAGEDDON);
            }
        }

        canClearLabel = true;
        seconds = 0;

    }


    public void prepareResults(Player leftPlayer, Player rightPlayer, String leftName, String rightName, Results way) {
        resultsPanel.prepareBackground(way);
        resultsPanel.preparePlayers(leftPlayer, rightPlayer, leftName, rightName);
        resultsPanel.prepareMassage(way);
        resultsPanel.repaint();
        if (way == Results.RIGHT_ARMAGEDDON || way == Results.LEFT_ARMAGEDDON)
            showArmageddon();
        else
            card.last(cardPanel);
    }

    private void showArmageddon() {
        JLabel armLab = new JLabel("ARMAGEDON!", CENTER);
        armLab.setForeground(Color.red);
        armLab.setFont(new Font("MyFont", BOLD, 40));
        armLab.setBounds(340, BOARD_Y + BOARD_HEIGHT + 10, 500, 70);
        gamePanel.add(armLab);
        leftTime++;
    }


    public void setConfig(InputFileReader config) {
        this.config = config;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public InputFileReader getConfig() {
        return config;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
