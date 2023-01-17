public class Connect {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final int PLAYERCOUNT = 2;
    private static final int VICTORYCHAIN = 4;

    public static void main(String[] args)
    {
        Window WINDOW = new Window(WIDTH, HEIGHT, "Connect");
        Game GAME = new Game(WIDTH, HEIGHT, COLUMNS, ROWS, PLAYERCOUNT, VICTORYCHAIN, WINDOW);
    }
}
