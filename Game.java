import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel {

    private int frameWIDTH, frameHEIGHT;
    private int boardWIDTH, boardHEIGHT;
    private int spaceWIDTH, spaceHEIGHT;
    private int boardX, boardY;
    
    private int columns, rows;
    private int victoryChain;

    private Color playerColor;
    private int playerCount;
    private int turn;
    private int player;

    private int[][] state;

    Window window;

    public Game(int wit, int h, int c, int r, int pC, int vC, Window win)
    {
        frameWIDTH = wit;
        frameHEIGHT = h;
        columns = c;
        rows = r;
        playerCount = pC;
        victoryChain = vC;
        window = win;

        turn = -1;

        state = new int[columns][rows];

        boardWIDTH = (int) (frameWIDTH * .7);
        boardHEIGHT = (int) (frameHEIGHT * .6);

        boardX = (int) (frameWIDTH * .15);
        boardY = (int) (frameHEIGHT * .2);

        spaceWIDTH = boardWIDTH / columns;
        spaceHEIGHT = boardHEIGHT / rows;

        clear();
        nextTurn();
        setBackground(Color.CYAN);
        setLayout(null);
        setBounds(0, 0, frameWIDTH, frameHEIGHT);
        window.add(this);
    }

    //used by areas to tell which color piece to have under mouse
    public Color getPlayerColor()
    {
        return playerColor;
    }

    //increases turn, mods it with playercount to get current player's turn, sets appropriate color
    public void nextTurn()
    {
        player = (++turn % playerCount) + 1;

        switch (player) {
            case 1:
                playerColor = Color.RED;
                break;
            case 2:
                playerColor = Color.BLUE;
                break;
            case 3:
                playerColor = Color.YELLOW;
                break;
            case 4:
                playerColor = Color.GREEN;
                break;
        }
    }

    //"drops" a piece down on the current column, it will stay in the lowest (aka i value) empty space. repaint and check for victory
    public void drop(int c)
    {
        for (int i = rows - 1; i >= 0; i--)
            if (state[c][i] == 0) {
                state[c][i] = player;
                break;
            }

        validate();
        repaint();

        if (victoryCheck())
            displayVictory();

        nextTurn();
    }

    //ILL ADD DIAGONAL SOON
    public boolean victoryCheck()
    {
        return (horizontalCheck() || verticalCheck());
    }

    //checks for consecutive pieces of current player in rows
    private boolean horizontalCheck()
    {
        int pieces = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                if (state[j][i] == player) {
                    pieces++;
                    if (pieces >= victoryChain)
                        return true;
                }
                else
                    pieces = 0;

            pieces = 0;
        }
        return false;
    }

    //checks for consecutive pieces of current player in columns
    private boolean verticalCheck()
    {
        int pieces = 0;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++)
                if (state[i][j] == player) {
                    pieces++;
                    if (pieces >= victoryChain)
                        return true;
                }
                else
                    pieces = 0;
            
            pieces = 0;
        }
        return false;
    }

    //creates a dialog frame that displays victory and gives flow of control options
    private void displayVictory()
    {
        JDialog victory = new JDialog(window, String.format("Player %d Victory!", player), true);
        victory.setLocationRelativeTo(null);
        victory.setSize(frameWIDTH / 4, frameHEIGHT / 4);
        victory.setLayout(new FlowLayout());

        ActionListener exitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        };

        ActionListener againListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clear();
                turn = -1;
                victory.setVisible(false);
            }
        };

        JButton again = new JButton("Play Again");  victory.add(again);
        again.addActionListener(againListener);

        JButton exit = new JButton("Close Game");   victory.add(exit);
        exit.addActionListener(exitListener);

        //JButton mainMenu = new JButton("Return to Main Menu");  victory.add(mainMenu);

        victory.setVisible(true);
    }

    //emptys board and repaints
    private void clear()
    {
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++)
                state[i][j] = 0;
        validate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawBoard(g2d);
        drawGrid(g2d);
        drawMouseAreas(g2d);
    }

    //draws solid rectangle behind space grid
    private void drawBoard(Graphics2D g)
    {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(boardX, boardY, boardWIDTH, boardHEIGHT);
    }

    //draws space grid in front of "board" rectangle
    private void drawGrid(Graphics2D g)
    {
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++) {
                switch(state[i][j]) {
                    case 1:
                        g.setColor(Color.RED);
                        break;
                    case 2:
                        g.setColor(Color.BLUE);
                        break;
                    case 3:
                        g.setColor(Color.YELLOW);
                        break;
                    case 4:
                        g.setColor(Color.GREEN);
                        break;
                    default:
                        g.setColor(Color.CYAN);
                        break;
                }
                g.fillOval(spaceWIDTH * i + boardX, spaceHEIGHT * j + boardY, spaceWIDTH, spaceHEIGHT);
            }
    }

    //draw JLabels that act as mouselisteners
    private void drawMouseAreas(Graphics2D g)
    {
        for (int i = 0; i < columns; i++)
            window.add(new Area(i, spaceWIDTH, spaceHEIGHT, boardX, boardY, this));
    }
}


