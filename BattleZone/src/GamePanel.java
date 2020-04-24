import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {
    private Game game;
    public GamePanel(Game game) {
        this.game = game;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.repaint(g);
    }
}
