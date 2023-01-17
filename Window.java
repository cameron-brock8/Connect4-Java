import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    public Window(int width, int height, String title)
    {
        setTitle(title);
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(null);
    }
}
