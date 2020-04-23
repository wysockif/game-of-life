import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0,0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
    }
}
