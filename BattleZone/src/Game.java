import javax.swing.JFrame;

public class Game extends JFrame{
    public static int WINDOW_WIDTH = 1200;
    public static int WINDOW_HEIGHT = 800;

    private GamePanel gamePanel;

    public Game(){
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }


    public static void main(String[] args){
        Game game = new Game();
    }


}
