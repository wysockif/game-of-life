import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private Game game;
    private JLabel timeLabel, gameTitle, infoLabel, countingDown;
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
        leftPlayerName = new JLabel("Gracz 1", JLabel.CENTER);
        leftPlayerName.setFont(new Font("MyFont", Font.BOLD, 20));
        leftPlayerName.setBounds(5, 10, 390, 20);
        leftPlayerName.setForeground(Color.BLACK);
        add(leftPlayerName);

        rightPlayerName = new JLabel("Gracz 2", JLabel.CENTER);
        rightPlayerName.setFont(new Font("MyFont", Font.BOLD, 20));
        rightPlayerName.setBounds(790, 10, 390, 20);
        rightPlayerName.setForeground(Color.BLACK);
        add(rightPlayerName);
    }

    private void addShotsLabels() {
//        leftShotsLabel = new JLabel("Pociski: "+ leftPlayer.getBullets().size()
//                + "/" + leftPlayer.getMaxNumberOfShots(), JLabel.CENTER);
        leftShotsLabel = new JLabel("Pociski: ", JLabel.CENTER);
        leftShotsLabel.setBounds(5, 30, 390, 20);
        leftShotsLabel.setForeground(Color.BLACK);
        add(leftShotsLabel);

//        rightShotsLabel = new JLabel("Pociski: "+ rightPlayer.getBullets().size()
//                + "/" + rightPlayer.getMaxNumberOfShots(), JLabel.CENTER);
        rightShotsLabel = new JLabel("Pociski: ", JLabel.CENTER);
        rightShotsLabel.setBounds(790, 30, 390, 20);
        rightShotsLabel.setForeground(Color.BLACK);
        add(rightShotsLabel);
    }

    private void addScoresLabels() {
//        leftScoreLabel = new JLabel("Zdobyte punkty: " + leftPlayer.getPointsGained(), JLabel.CENTER);
        leftScoreLabel = new JLabel("Zdobyte punkty: ", JLabel.CENTER);
        leftScoreLabel.setBounds(5, 45, 390, 20);
        leftScoreLabel.setForeground(Color.BLACK);
        add(leftScoreLabel);

//        rightScoreLabel = new JLabel("Zdobyte punkty: " + rightPlayer.getPointsGained(), JLabel.CENTER);
        rightScoreLabel = new JLabel("Zdobyte punkty: ", JLabel.CENTER);
        rightScoreLabel.setBounds(790, 45, 390, 20);
        rightScoreLabel.setForeground(Color.BLACK);
        add(rightScoreLabel);
    }

    private void addNewScoresLabels() {
        leftNewScoreLabel = new JLabel("", JLabel.CENTER);
        leftNewScoreLabel.setBounds(5, 60, 390, 20);
        leftNewScoreLabel.setForeground(Color.BLACK);
        add(leftNewScoreLabel);

        rightNewScoreLabel = new JLabel("", JLabel.CENTER);
        rightNewScoreLabel.setBounds(790, 60, 390, 20);
        rightNewScoreLabel.setForeground(Color.BLACK);
        add(rightNewScoreLabel);
    }

    private void addLeftLabels() {
//        timeLabel = new JLabel("Pozostały czas: " + leftTime, JLabel.CENTER);
        timeLabel = new JLabel("Pozostały czas: ", JLabel.CENTER);
        timeLabel.setFont(new Font("MyFont", Font.BOLD, 15));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBounds(500, 15, 200, 20);
        add(timeLabel);

        gameTitle = new JLabel("BattleZone");
        gameTitle.setFont(new Font("MyFont", Font.BOLD, 40));
        gameTitle.setBounds(490, 35, 300, 45);
        gameTitle.setForeground(Color.RED);
        add(gameTitle);

        infoLabel = new JLabel("", JLabel.CENTER);
        infoLabel.setFont(new Font("MyFont", Font.TYPE1_FONT, 13));
        infoLabel.setBounds(0, 750, game.getWidth() - 10, 20);
        infoLabel.setForeground(Color.white);
        add(infoLabel);

    }

    public void countDown(){
        countingDown = new JLabel("", JLabel.CENTER);
        countingDown.setFont(new Font("MyFont", Font.TYPE1_FONT, 60));
        countingDown.setBounds(Game.BOARD_X + Game.BOARD_WIDTH/2 - 30 , Game.BOARD_Y + Game.BOARD_HEIGHT/2 -30, 60, 60);
        countingDown.setForeground(Color.RED);
        add(countingDown);


        try {
            for(int i = 3; i >= 0; i--) {
                countingDown.setText(i + "");
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        remove(countingDown);

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