package pl.battlezone.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pl.battlezone.Game;

import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Font.BOLD;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static javax.swing.SwingConstants.CENTER;

public class GamePanel extends JPanel {
    private Game game;
    private JLabel timeLabel, infoLabel;
    private JLabel leftPlayerName, rightPlayerName, leftShotsLabel, rightShotsLabel;
    private JLabel leftScoreLabel, leftNewScoreLabel, rightScoreLabel, rightNewScoreLabel;

    public GamePanel(Game game) {
        this.game = game;
        setLayout(null);
        addNamesLabels();
        addShotsLabels();
        addScoresLabels();
        addNewScoresLabels();
        addLeftLabels();
        setFocusable(true);
        requestFocus();
    }

    private void addNamesLabels() {
        leftPlayerName = new JLabel("Gracz 1", CENTER);
        leftPlayerName.setFont(new Font("MyFont", BOLD, 20));
        leftPlayerName.setBounds(5, 10, 390, 20);
        leftPlayerName.setForeground(Color.BLACK);
        add(leftPlayerName);

        rightPlayerName = new JLabel("Gracz 2", CENTER);
        rightPlayerName.setFont(new Font("MyFont", BOLD, 20));
        rightPlayerName.setBounds(790, 10, 390, 20);
        rightPlayerName.setForeground(Color.BLACK);
        add(rightPlayerName);
    }

    private void addShotsLabels() {
        leftShotsLabel = new JLabel("Pociski: ", CENTER);
        leftShotsLabel.setBounds(5, 30, 390, 20);
        leftShotsLabel.setForeground(Color.BLACK);
        add(leftShotsLabel);

        rightShotsLabel = new JLabel("Pociski: ", CENTER);
        rightShotsLabel.setBounds(790, 30, 390, 20);
        rightShotsLabel.setForeground(Color.BLACK);
        add(rightShotsLabel);
    }

    private void addScoresLabels() {
        leftScoreLabel = new JLabel("Zdobyte punkty: ", CENTER);
        leftScoreLabel.setBounds(5, 45, 390, 20);
        leftScoreLabel.setForeground(Color.BLACK);
        add(leftScoreLabel);

        rightScoreLabel = new JLabel("Zdobyte punkty: ", CENTER);
        rightScoreLabel.setBounds(790, 45, 390, 20);
        rightScoreLabel.setForeground(Color.BLACK);
        add(rightScoreLabel);
    }

    private void addNewScoresLabels() {
        leftNewScoreLabel = new JLabel("", CENTER);
        leftNewScoreLabel.setBounds(5, 65, 390, 30);
        leftNewScoreLabel.setFont(new Font("MyFont", BOLD, 30));
        leftNewScoreLabel.setForeground(Color.BLACK);
        add(leftNewScoreLabel);

        rightNewScoreLabel = new JLabel("", CENTER);
        rightNewScoreLabel.setBounds(790, 65, 390, 30);
        rightNewScoreLabel.setFont(new Font("MyFont", BOLD, 30));
        rightNewScoreLabel.setForeground(Color.BLACK);
        add(rightNewScoreLabel);
    }

    private void addLeftLabels() {
        timeLabel = new JLabel("Pozostały czas: ", CENTER);
        timeLabel.setFont(new Font("MyFont", BOLD, 15));
        timeLabel.setForeground(WHITE);
        timeLabel.setBounds(500, 15, 200, 20);
        add(timeLabel);

        JLabel gameTitle = new JLabel("BattleZone");
        gameTitle.setFont(new Font("MyFont", BOLD, 40));
        gameTitle.setBounds(490, 35, 300, 45);
        gameTitle.setForeground(RED);
        add(gameTitle);

        infoLabel = new JLabel("", CENTER);
        infoLabel.setFont(new Font("MyFont", BOLD, 13));
        infoLabel.setBounds(0, 750, game.getWidth() - 10, 20);
        infoLabel.setForeground(WHITE);
        add(infoLabel);
    }

    public void countDown() {
        JLabel countingDown = new JLabel("", CENTER);
        countingDown.setFont(new Font("MyFont", BOLD, 60));
        countingDown.setBounds(Game.boardX + Game.boardWidth / 2 - 30, Game.boardY + Game.boardHeight / 2 - 30,
                60, 60);
        countingDown.setForeground(RED);
        add(countingDown);
        try {
            for (int i = 3; i > 0; i--) {
                countingDown.setText(i + "");
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setStarted(true);
        remove(countingDown);
    }

    public void savePanel(String name) {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        paint(g2);
        try {
            ImageIO.write(image, "png", new File(name + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveBoard(String name) {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        image = image.getSubimage(Game.boardX, Game.boardY, Game.boardWidth, Game.boardHeight);
        paint(g2);
        try {
            ImageIO.write(image, "png", new File(name + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.repaint(g);
    }

    public JLabel getLeftPlayerName() {
        return leftPlayerName;
    }

    public JLabel getRightPlayerName() {
        return rightPlayerName;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public JLabel getLeftShotsLabel() {
        return leftShotsLabel;
    }

    public JLabel getRightShotsLabel() {
        return rightShotsLabel;
    }

    public JLabel getLeftScoreLabel() {
        return leftScoreLabel;
    }

    public JLabel getLeftNewScoreLabel() {
        return leftNewScoreLabel;
    }

    public JLabel getRightScoreLabel() {
        return rightScoreLabel;
    }

    public JLabel getRightNewScoreLabel() {
        return rightNewScoreLabel;
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

}