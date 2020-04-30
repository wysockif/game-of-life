import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.metal.MetalButtonUI;

public class ResultsPanel extends JPanel implements ActionListener {
    private BufferedImage leftWinner, rightWinner, tie;
    private BufferedImage currentBackground;

    private String massage = "Czas upłynął. Nastąpił remis!";

    private JLabel wayLabel;
    private JLabel saveLabel1, saveLabel2;
    private JCheckBox checkBox;

    private Game game;
    private JButton endButton;
    private JButton saveButton;
    private JLabel leftNameLabel, rightNameLabel;
    private JLabel leftScoreLabel, rightScoreLabel;
    private int leftScore, rightScore;


    public ResultsPanel(Game game) {
        this.game = game;
        setLayout(null);
        loadBackgrounds();
        addButtons();
        addNamesLabels();
        addScoresLabels();
        addInfoLabels();

    }

    private void addScoresLabels() {
        leftScoreLabel = new JLabel("Wynik: " + leftScore, JLabel.CENTER);
        leftScoreLabel.setBounds(155, 500, 240, 40);
        leftScoreLabel.setFont(new Font("Sans", Font.BOLD, 18));
        leftScoreLabel.setForeground(Color.WHITE);
        add(leftScoreLabel);

        rightScoreLabel = new JLabel("Wynik: " + rightScore, JLabel.CENTER);
        rightScoreLabel.setBounds(715, 500, 240, 40);
        rightScoreLabel.setFont(new Font("Sans", Font.BOLD, 18));
        rightScoreLabel.setForeground(Color.WHITE);
        rightScoreLabel.setHorizontalAlignment(JTextField.CENTER);
        add(rightScoreLabel);

    }

    private void addInfoLabels() {
        wayLabel = new JLabel(massage, JLabel.CENTER);
        wayLabel.setBounds(0, 580, currentBackground.getWidth(), 40);
        wayLabel.setFont(new Font("Sans", Font.BOLD, 30));
        wayLabel.setForeground(Color.red);
        wayLabel.setHorizontalAlignment(JTextField.CENTER);
        add(wayLabel);
    }

    private void addNamesLabels() {
        leftNameLabel = new JLabel("Gracz 1", JLabel.CENTER);
        leftNameLabel.setBounds(155, 470, 240, 40);
        leftNameLabel.setFont(new Font("Sans", Font.BOLD, 30));
        leftNameLabel.setForeground(Color.WHITE);
        add(leftNameLabel);

        rightNameLabel = new JLabel("Gracz 2", JLabel.CENTER);
        rightNameLabel.setBounds(715, 470, 240, 40);
        rightNameLabel.setFont(new Font("Sans", Font.BOLD, 30));
        rightNameLabel.setForeground(Color.WHITE);
        rightNameLabel.setHorizontalAlignment(JTextField.CENTER);
        add(rightNameLabel);
    }

    private void addButtons() {
        endButton = new JButton("Zakończ");
        endButton.setFont(new Font("Sans", Font.BOLD, 25));
        endButton.setBackground(Color.black);
        endButton.setForeground(Color.white);
        endButton.addActionListener(this);
        endButton.setBounds(500, 710, 200, 60);
        endButton.setFocusable(false);
        endButton.addActionListener(this);
        addDefaultFilesFields();
        addAttachmentFields();
        add(endButton);


    }



    private void addDefaultFilesFields() {
        saveLabel2 = new JLabel("Zapisz pliki graficzne ostatniego stanu gry tylko do domyślnej lokalizacji:", JLabel.LEFT);
        saveLabel2.setBounds(350, 645, 500, 20);
        saveLabel2.setForeground(Color.WHITE);
        add(saveLabel2);

        checkBox = new JCheckBox();
        checkBox.setBounds(765, 645, 20, 20);
        checkBox.doClick();
        checkBox.setBackground(Color.darkGray);
        checkBox.addActionListener(this);
        add(checkBox);
    }

    private void addAttachmentFields() {
        saveLabel1 = new JLabel("Zapisz także do własnej lokalizacji: ", JLabel.RIGHT);
        saveLabel1.setBounds(150, 680, 400, 20);
        saveLabel1.setForeground(Color.WHITE);
        add(saveLabel1);


        saveButton = new JButton("Wybierz lokalizację");
        saveButton.setBackground(Color.black);
        saveButton.setForeground(Color.white);
        saveButton.addActionListener(this);
        saveButton.setEnabled(false);
        saveButton.setBounds(570, 675, 200, 30);
        saveButton.setFocusable(false);

        add(saveButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.lightGray);
        g.fillRect(0, 500, currentBackground.getWidth(), 50);
        g.setColor(Color.darkGray);
        g.fillRect(0, 550, currentBackground.getWidth(), 310);
        g.drawImage(currentBackground, 0, 0, currentBackground.getWidth(), currentBackground.getHeight(), null);
        g.fillRect(0, 0, currentBackground.getWidth(), 30);

//        g.drawRect(715, 500, 240, 40);
//        g.drawRect(155, 500, 240, 40);
    }

    private void loadBackgrounds() {
        try {
            this.leftWinner = ImageIO.read(new File("src/main/resources/img/backgrounds/leftWinner.png"));
            this.rightWinner = ImageIO.read(new File("src/main/resources/img/backgrounds/rightWinner.png"));
            this.tie = ImageIO.read(new File("src/main/resources/img/backgrounds/tie.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Błąd krytyczny!\n" +
                    "Nie mogę znaleźć pliku z obrazem panelu końcowego!", "Błąd krytyczny!", JOptionPane.ERROR_MESSAGE);
            System.exit(2);
        }
        currentBackground = tie;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == endButton){
           game.dispose();

        } else if (src == checkBox) {
            if (checkBox.isSelected())
                saveButton.setEnabled(false);
            else {
                saveButton.setEnabled(true);
                saveButton.setForeground(Color.white);
                saveButton.setText("Wybierz lokalizację");

            }

        } else if (src == saveButton) {
            saveButton.setForeground(Color.white);

            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String filePath = fc.getSelectedFile().getAbsolutePath();
                    if(filePath.contains("."))
                        filePath = filePath.substring(0, filePath.lastIndexOf('.'));
                    game.getGamePanel().savePanel(filePath + "Panel");
                    game.getGamePanel().saveBoard(filePath + "Board");
                    saveButton.setEnabled(false);
                    saveButton.setText("Zapisano");
                saveButton.setUI(new MetalButtonUI() {
                    protected Color getDisabledTextColor() {
                        return Color.green;
                    }
                });
                    checkBox.setEnabled(false);
                    setFocusable(true);

            }
        }


    }
}
