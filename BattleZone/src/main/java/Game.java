import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import static java.awt.Font.BOLD;
import static java.lang.String.format;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.SwingConstants.CENTER;

public class Game extends JFrame implements Runnable {
    private int maxScore, leftTime, maxTime;
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

    private static boolean isMaxSpeed = false, isMinSize = false;
    private static JPanel cardPanel;
    private static CardLayout card;

    public final static int WINDOW_WIDTH = 1200;
    public final static int WINDOW_HEIGHT = 820;
    public static int boardWidth, boardHeight;
    public static int boardX, boardY;
    public static boolean isSoundTurnedOn;
    public static Thread gameThread;

    public Game() {
        customizeWindow();
        cells = new Cells(this, new SpriteCells("img/cells.png", 100, 100));
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
        add(cardPanel);
        setVisible(true);
    }

    private void customizeWindow() {
        setTitle("BattleZone");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(Game.class.getResource("/img/icon.png")).getImage());
        setCloseOperation();
    }

    private void setCloseOperation() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String[] buttons = {"Tak", "Nie"};
                Sounds.playErrorSound();
                int PromptResult = JOptionPane.showOptionDialog(null, "Czy na pewno chcesz zakończyć?",
                        "Potwierdzenie wyjścia", DEFAULT_OPTION, WARNING_MESSAGE, null, buttons, buttons[1]);
                if (PromptResult == YES_OPTION) {
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
        updateGameObjects();
    }

    private void updateGameObjects() {
        leftPlayer.updateTank();
        rightPlayer.updateTank();
        rightPlayer.checkIfShot();
        leftPlayer.checkIfShot();
        leftPlayer.updateMyBullets();
        rightPlayer.updateMyBullets();
        leftPlayer.removeUnwantedBullets();
        rightPlayer.removeUnwantedBullets();
        cells.checkIfHit(leftPlayer);
        cells.checkIfHit(rightPlayer);
        gamePanel.getLeftShotsLabel().setText("Pociski: " + leftPlayer.getBullets().size() + "/" + leftPlayer.getMaxNumberOfShots());
        gamePanel.getRightShotsLabel().setText("Pociski: " + rightPlayer.getBullets().size() + "/" + rightPlayer.getMaxNumberOfShots());
    }

    private void updateOncePerSecond() {
        decrementTimes();
        updateTimesLabels();
        leftPlayer.updateShots();
        rightPlayer.updateShots();

        if (leftTime <= 5)
            gamePanel.getTimeLabel().setForeground(Color.RED);
        if (timeToChangeBSpeedAndCSize == 0 && (maxTime - leftTime) != 0)
            prepareForChangeBSpeedAndCSize();
        if (timeToIncreaseCellsValues == 0 && (maxTime - leftTime) != 0) {
            cells.increaseValues();
            timeToIncreaseCellsValues = config.getTimeToIncreaseCellsValues();
        }
        if (timeToGenerateKidsCells == 0 && (maxTime - leftTime) != 0) {
            cells.boreChildren();
            timeToGenerateKidsCells = config.getTimeToGenerateKidsCells();
        }
        if (timeToGenerateNewCells == 0 && (maxTime - leftTime) != 0) {
            cells.createCells();
            timeToGenerateNewCells = config.getTimeToGenerateNewCells();
        }
        if (leftTime == 0)
            timeIsUp();
        if (leftPlayer.getPointsGained() >= maxScore || rightPlayer.getPointsGained() >= maxScore)
            maxScoreReached();
        clearNewScoreLabels();
    }


    private void clearNewScoreLabels() {
        if (canClearLabel)
            seconds++;
        if (canClearLabel && seconds == 3) {
            gamePanel.getLeftNewScoreLabel().setText("");
            gamePanel.getRightNewScoreLabel().setText("");
            canClearLabel = false;
            seconds = 0;
        }
    }

    private void prepareForChangeBSpeedAndCSize() {
        leftPlayer.speedUpBullets(config.getPercentageIncreaseInBulletsSpeed());
        rightPlayer.speedUpBullets(config.getPercentageIncreaseInBulletsSpeed());
        timeToChangeBSpeedAndCSize = config.getTimeToChangeBulletsSpeedAndCellsSize();
        cells.reduceSize(config.getPercentageDecreaseInCellsSize());
        timeToChangeBSpeedAndCSize = config.getTimeToChangeBulletsSpeedAndCellsSize();
    }

    private void maxScoreReached() {
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

    private void timeIsUp() {
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

    private void decrementTimes() {
        leftTime--;
        timeToGenerateNewCells--;
        timeToGenerateKidsCells--;
        timeToIncreaseCellsValues--;
        timeToChangeBSpeedAndCSize--;
    }

    private void updateTimesLabels() {
        gamePanel.getTimeLabel().setText("Pozostały czas: " + leftTime);
        updateInfoLabel();
    }

    private void updateInfoLabel() {
        int percentBullets = config.getPercentageIncreaseInBulletsSpeed();
        int percentCells = config.getPercentageDecreaseInCellsSize();
        String space = "                ";
        String info1 = format("Nowe komórki za %02ds", timeToGenerateNewCells);
        String info2 = format("Komórki dzieci za %02ds", timeToGenerateKidsCells);
        String info3 = format("Wzmocnienie komórek za %02ds", timeToIncreaseCellsValues);
        String info4 = format("Zmniejszenie komórek za %02ds o %02d%%", timeToChangeBSpeedAndCSize, percentCells);
        String info5 = format("Przyspieszenie pocisków za %02ds o %02d%%", timeToChangeBSpeedAndCSize, percentBullets);
        if(!isMaxSpeed && !isMinSize)
            gamePanel.getInfoLabel().setText(info1 + space + info2 + space + info3 + space + info4 + space + info5 );
        else if(!isMaxSpeed)
            gamePanel.getInfoLabel().setText(info1 + space + info2 + space + info3 + space + info5 );
        else if(!isMinSize)
            gamePanel.getInfoLabel().setText(info1 + space + info2 + space + info3 + space + info4 );
        else
            gamePanel.getInfoLabel().setText(info1 + space + info2 + space + info3);
    }

    public void repaint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        g.setColor(Color.white);
        g.fillRect(boardX, boardY, boardWidth, boardHeight);
        g.fillRect(5, 5, 390, 100);
        g.fillRect(790, 5, 390, 100);
        g.setColor(Color.darkGray);
        g.fillRect(340, boardY + boardHeight + 10, 500, 70);
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
        adjustBoardSize();

        leftPlayer = new LeftPlayer(config.getMaxNumberOfShots(), "/img/leftTanks.png", "/img/leftBullet.png", keysListener);
        rightPlayer = new RightPlayer(config.getMaxNumberOfShots(), "/img/rightTanks.png", "/img/rightBullet.png", keysListener);
        gamePanel.getLeftPlayerName().setText(menuPanel.getNamePlayer1());
        gamePanel.getRightPlayerName().setText(menuPanel.getNamePlayer2());
        maxTime = leftTime = menuPanel.getGameTime();
        maxScore = menuPanel.getGameScore();
        isSoundTurnedOn = menuPanel.isSoundTurnedOn();
        updateLabels();

        card.next(cardPanel);
        running = true;
        gameThread.start();
    }

    private void assignValues() {
        timeToGenerateKidsCells = config.getTimeToGenerateKidsCells();
        timeToGenerateNewCells = config.getTimeToGenerateNewCells();
        timeToChangeBSpeedAndCSize = config.getTimeToChangeBulletsSpeedAndCellsSize();
        timeToIncreaseCellsValues = config.getTimeToIncreaseCellsValues();
    }

    private void adjustBoardSize() {
        boardWidth = config.getBoardWidth();
        boardHeight = config.getBoardHeight();
        boardX = 340 + (500 - boardWidth) / 2;
        boardY = 150 + (500 - boardHeight) / 2;
        //System.out.println(boardX  +" "+ boardY  +" "+ boardHeight  +" "+ boardWidth);
    }

    private void updateLabels() {
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

            if (changeInSecondsFPS > 1.0 / 60.0) {
                updateFrame();
                render();
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

        if (player instanceof LeftPlayer)
            awardPointsToTheLeftPlayer(cell, value, inheritance);
        else if (player instanceof RightPlayer)
            awardPointsToTheRightPlayer(cell, value, inheritance);

        canClearLabel = true;
        seconds = 0;
    }

    private void awardPointsToTheRightPlayer(Cell cell, int value, int inheritance) {
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

    private void awardPointsToTheLeftPlayer(Cell cell, int value, int inheritance) {
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
    }

    public void prepareResults(Player leftPlayer, Player rightPlayer, String leftName, String rightName, Results way) {
        resultsPanel.prepareBackground(way);
        resultsPanel.preparePlayers(leftPlayer, rightPlayer, leftName, rightName);
        resultsPanel.prepareMessage(way);
        resultsPanel.repaint();
        if (way == Results.RIGHT_ARMAGEDDON || way == Results.LEFT_ARMAGEDDON)
            showArmageddon();
        else
            card.last(cardPanel);
        if (isSoundTurnedOn)
            Sounds.playGameOverSound();
    }

    private void showArmageddon() {
        JLabel armLab = new JLabel("ARMAGEDON!", CENTER);
        armLab.setForeground(Color.red);
        armLab.setFont(new Font("MyFont", BOLD, 40));
        armLab.setBounds(340, boardY + boardHeight + 10, 500, 70);
        gamePanel.add(armLab);
        leftTime++;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public InputFileReader getConfig() {
        return config;
    }

    public void setConfig(InputFileReader config) {
        this.config = config;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public static void setIsMaxSpeed(boolean isMaxSpeed) {
        Game.isMaxSpeed = isMaxSpeed;
    }

    public static void setIsMinSize(boolean isMinSize) {
        Game.isMinSize = isMinSize;
    }
}
