import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.midi.SysexMessage;
import javax.swing.*;

public class LoginWindow {
    private JTextField username;
    private JTextField password;
    private MainWindow main;
    private Employee employeeObj;
    private ArrayList<String> userList;
    private ArrayList<String> passList;
    public LoginWindow() throws SQLException {
        createUI();
    }
    public static void main(String[] args) throws SQLException {
        new LoginWindow();

    }
    public void createUI() throws SQLException {
        JFrame frame = new JFrame(); //create login window
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        //set wallpaper for login window
        try {BufferedImage myImage = ImageIO.read(new File("Resources/LEDWallpaper.jpg"));
            frame.setContentPane(new ImagePanel(myImage));
        } catch (IOException e) {}

        Timer timer = new Timer();
        /*timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        }, 0, 5000);*/

        //retrieve list of usernames and passwords
		Connector con = new Connector();
        userList = con.retrieveUserList();
        passList = con.retrievePassList();

        //Label for title of application
        JLabel title = new JLabel("Massachusetts Law Enforcement Database");
        title.setBounds(700, 15, 1000, 100);
        title.setFont(new Font("Roboto", Font.PLAIN, 32));
        title.setForeground(Color.WHITE);
        frame.add(title);

        //Label for username box
        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(850, 400, 200, 100);
        frame.add(userLbl);
        userLbl.setForeground(Color.WHITE);


        //Label for password box
        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(850, 410, 200, 140);
        frame.add(passLbl);
        passLbl.setForeground(Color.WHITE);

        //textfield for username
        //x+75 y+42
        username = new JTextField("Jer_Deprey");
        username.setBounds(925, 442, 105, 20);
        frame.getContentPane().add(username);
        //userfield.setColumns(10);

        //textfield for password
        JPasswordField password = new JPasswordField("pass12");
        password.setBounds(925, 472, 105, 20);
        frame.getContentPane().add(password);

        JPanel loginPanel = new JPanel(null);

        //login button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(Color.WHITE);
        loginBtn.setBounds(825, 550, 260, 35);
        frame.getContentPane().add(loginBtn);
        frame.getRootPane().setDefaultButton(loginBtn);
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                String pass = password.getText();
                boolean authenticated = false;

                for(int i = 0;i<userList.size();i++) {
                    if (user.equals(userList.get(i)) && pass.equals(passList.get(i))) {
                        //instantiate user object
                        try {
                            employeeObj = new Employee(user, con.retrieveAccess(userList.get(i)));
                        } catch (SQLException ex) {System.out.println("SQL EXCEPTION");}
                        authenticated = true;
                        frame.dispose();
                    }
                } if(!authenticated) {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password");
                } else {
                    MainWindow main = new MainWindow();
                    main.run(employeeObj);
                }
            }
        });
        frame.setVisible(true);
    }
}