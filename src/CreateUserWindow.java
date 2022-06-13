import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class  CreateUserWindow {
    public CreateUserWindow() {}

    public void run(Employee employee) {
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        frame.getContentPane().setBackground(new Color(0,0,0));
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        try {
            BufferedImage myImage = ImageIO.read(new File("Resources/LEDWallpaper.jpg"));
            frame.setContentPane(new ImagePanel(myImage));
        } catch (IOException e) {}

        JLabel accessLbl = new JLabel("Access Level:");
        accessLbl.setBounds(700, 430, 200, 100);
        frame.add(accessLbl);

        JLabel userLoginLbl = new JLabel("Username:");
        userLoginLbl.setBounds(700, 460, 200, 100);
        frame.add(userLoginLbl);

        JLabel passLoginLbl = new JLabel("Password:");
        passLoginLbl.setBounds(700, 490, 200, 100);
        frame.add(passLoginLbl);

        String[] ranks = {"Officer", "Sergeant", "Lieutenant"};
        JComboBox accessBox = new JComboBox(ranks);
        accessBox.setBounds(700, 430, 100, 25);


        JTextField userField = new JTextField();
        userField.setBounds(800, 502, 105, 20);
        frame.getContentPane().add(userField);

        JTextField passField = new JTextField();
        passField.setBounds(800, 532, 105, 20);
        frame.getContentPane().add(passField);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setBackground(Color.WHITE);
        submitBtn.setBounds(700, 600, 260, 35);
        frame.getContentPane().add(submitBtn);
        frame.getRootPane().setDefaultButton(submitBtn);
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connector con = new Connector();
                try {
                    con.addNewUser((String) accessBox.getSelectedItem(), userField.getText(), passField.getText());
                } catch (SQLException ex) {}
                MainWindow main = new MainWindow();
                main.run(employee);
                frame.dispose();
            }
        });

        JButton backBtn = GUI.createButton("  Back", 15, 15);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow m = new MainWindow();
                m.run(employee);
                frame.setVisible(false);
            }
        });
        frame.add(backBtn);

        JLabel label = new JLabel();
        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                label.setLocation(x,y);
                label.setText("(" + x + ","  + y + ")");
                label.setSize(label.getPreferredSize());
                frame.add(label);
                System.out.println(x+","+y);
            }
        });
    }
}
