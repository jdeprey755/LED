import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

//make createFrame, createSearchPanel, addNewInfoPanel
public class GUI {
    public static JButton createButton(String buttonName, int x, int y) {
        JButton button = new JButton(buttonName);

        Color btnColor = new Color(5, 9, 105);
        BevelBorder bevelBorder = new BevelBorder(BevelBorder.RAISED, new Color(227, 227, 227), new Color(33, 33, 33));
        button.setBackground(btnColor);
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, 180, 50);
        button.setFont(new Font("Arial Pro Medium", Font.BOLD, 16));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(bevelBorder);
        return button;
    }

    public static JButton createAlternateButton(String buttonName, int x, int y) {
        JButton button = new JButton(buttonName);

        BevelBorder bevelBorder = new BevelBorder(BevelBorder.RAISED, new Color(227, 227, 227), new Color(33, 33, 33));
        button.setBackground(Color.lightGray);
        button.setForeground(Color.white);
        button.setBounds(x, y, 160, 35);
        button.setFont(new Font("Arial Pro Medium", Font.BOLD, 16));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBorder(bevelBorder);
        return button;
    }

    //new method to create a search box so each window doesnt have to hava custom box for ui.
    public static JPanel createBox(int row) {
        JPanel pane = new JPanel(new GridLayout(row, 1, 0, 50));
        //maybe change size depending on amount of rows
        pane.setBounds(650, 250, 350, 400);
        return pane;
    }

    public static BevelBorder getBevel() {
        return new BevelBorder(BevelBorder.RAISED, new Color(227, 227, 227), new Color(33, 33, 33));
    }

    public static LineBorder getLine() {
        return new LineBorder(Color.DARK_GRAY, 1);
    }

    public static JPanel createSearch() {
        JPanel searchPanel = new JPanel(null);

        return new JPanel();
    }
}
