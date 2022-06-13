import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditUserWindow {
    private Timer timerLB;
    private JLabel deletedLbl;
    private JLabel addedLbl;
    boolean show = false;

    public EditUserWindow() {
    }

    public void run(Employee employee) throws SQLException {
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        frame.setBackground(new Color(0, 0, 0));
        frame.setBackground(Color.WHITE);
        frame.setResizable(false);
        try {
            BufferedImage myImage = ImageIO.read(new File("Resources/LEDWallpaper.jpg"));
            frame.setContentPane(new ImagePanel(myImage));
        } catch (IOException e) {
        }
        frame.setVisible(true);

        JLabel title = new JLabel("Massachusetts Law Enforcement Employee Database");
        title.setForeground(Color.WHITE);
        title.setBounds(600, 15, 1000, 100);
        title.setFont(new Font("Roboto", Font.PLAIN, 32));
        frame.add(title);

        JComboBox userListBox = new JComboBox(getEmployees());

        //components for creating an employee
        JPanel createPanel = new JPanel(null);
        createPanel.setBounds(600, 375, 650, 350);
        createPanel.setBackground(Color.lightGray);
        createPanel.setForeground(Color.WHITE);
        createPanel.setBorder(GUI.getBevel());
        frame.add(createPanel);
        createPanel.setVisible(false);

        JLabel accessLbl = new JLabel("Access Level:");
        accessLbl.setBounds(150, 50, 150, 15);
        createPanel.add(accessLbl);

        JLabel userLoginLbl = new JLabel("Username:");
        userLoginLbl.setBounds(150, 90, 150, 15);
        createPanel.add(userLoginLbl);

        JLabel passLoginLbl = new JLabel("Password:");
        passLoginLbl.setBounds(150,130, 150, 15);
        createPanel.add(passLoginLbl);

        String[] ranks = {"Officer", "Sergeant", "Lieutenant", "Forensic Technician"};
        JComboBox accessBox = new JComboBox(ranks);
        accessBox.setBounds(250, 50, 100, 25);
        createPanel.add(accessBox);

        JTextField userField = new JTextField();
        userField.setBounds(250, 90, 105, 25);
        createPanel.add(userField);

        JTextField passField = new JTextField();
        passField.setBounds(250, 130, 105, 25);
        createPanel.add(passField);

        //button for submitting new employee information
        JButton submitBtn = GUI.createAlternateButton("SUBMIT", 200, 200);
        createPanel.add(submitBtn);
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connector con = new Connector();
                String addedUser = userField.getText();
                try {con.addNewUser((String) accessBox.getSelectedItem(), userField.getText(), passField.getText());} catch (SQLException ex) {}
                addedLbl = new JLabel(addedUser + " has been Added");
                addedLbl.setForeground(Color.white);
                addedLbl.setFont(new Font("Arial Pro Medium", Font.BOLD, 24));
                addedLbl.setBounds(800, 300, 400, 100);
                timerLB = new Timer(250, new EditUserWindow.LbBlink(addedLbl));
                frame.add(addedLbl);
                frame.repaint();
                timerLB.start();
                createPanel.setVisible(false);
            }
        });

        JButton createBtn = GUI.createButton("  New Employee", 15, 80);
        frame.add(createBtn);
        createBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createPanel.setVisible(true);
                frame.repaint();
            }
        });

        //components for deleting employee
        JPanel deletePanel = new JPanel(null);
        deletePanel.setBounds(600, 375, 650, 350);
        deletePanel.setBackground(Color.lightGray);
        deletePanel.setForeground(Color.WHITE);
        deletePanel.setBorder(GUI.getBevel());
        frame.add(deletePanel);
        deletePanel.setVisible(false);

        JLabel selectLbl = new JLabel("Select Employee to Delete");
        selectLbl.setForeground(Color.darkGray);
        selectLbl.setFont(new Font("Arial Pro Medium", Font.PLAIN, 20));
        selectLbl.setBounds(115, 100, 250, 50);
        deletePanel.add(selectLbl);

        userListBox.setBounds(365, 115, 115, 25);
        deletePanel.add(userListBox);

        JButton deleteBtn = GUI.createAlternateButton("DELETE", 250, 300);
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePanel.setVisible(false);
                //delete the employee in the database
                String deleteUser = (String) userListBox.getSelectedItem();
                try {
                    Connector con = new Connector();
                    con.deleteEmployee(deleteUser);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                //repopulate employee data
                DefaultComboBoxModel<String> model;
                try {
                    model = new DefaultComboBoxModel<String>(getEmployees());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                userListBox.setModel(model);

                //display output text
                deletedLbl = new JLabel(deleteUser + " has been Deleted");
                deletedLbl.setForeground(Color.white);
                deletedLbl.setFont(new Font("Arial Pro Medium", Font.BOLD, 24));
                deletedLbl.setBounds(800, 300, 400, 100);
                timerLB = new Timer(250, new EditUserWindow.LbBlink(deletedLbl));
                frame.add(deletedLbl);
                frame.repaint();
                timerLB.start();
            }
        });
        deletePanel.add(deleteBtn);

        frame.repaint();

        JButton deleteEmployeeBtn = GUI.createButton("  Delete Employee", 15, 145);
        frame.add(deleteEmployeeBtn);
        deleteEmployeeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePanel.setVisible(true);
            }
        });

        JButton backBtn = GUI.createButton("  Back", 15, 15);
        frame.add(backBtn);
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainWindow m = new MainWindow();
                m.run(employee);
                frame.dispose();
            }
        });
    }

    public Employee createUser() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pane = new JPanel();

        JLabel idLbl = new JLabel("EmployeeID:");
        idLbl.setBounds(700, 400, 200, 100);

        JLabel accessLbl = new JLabel("Access Level:");
        accessLbl.setBounds(700, 430, 200, 100);

        JLabel userLoginLbl = new JLabel("Username:");
        userLoginLbl.setBounds(700, 460, 200, 100);

        JLabel passLoginLbl = new JLabel("Password:");
        passLoginLbl.setBounds(700, 490, 200, 100);
        pane.add(idLbl);
        pane.add(accessLbl);
        pane.add(userLoginLbl);
        pane.add(passLoginLbl);

        JTextField idField = new JTextField();
        idField.setBounds(800, 442, 105, 20);

        JTextField accessField = new JTextField();
        accessField.setBounds(800, 472, 105, 20);

        JTextField userField = new JTextField();
        userField.setBounds(800, 502, 105, 20);

        JTextField passField = new JTextField();
        passField.setBounds(800, 532, 105, 20);
        pane.add(idField);
        pane.add(accessField);
        pane.add(userField);
        pane.add(passField);

        JButton createBtn = new JButton("Submit");
        createBtn.setBounds(200, 200, 100, 100);
        pane.add(createBtn);
        createBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JLabel label = new JLabel();
        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                label.setLocation(x, y);
                label.setText("(" + x + "," + y + ")");
                label.setSize(label.getPreferredSize());
                label.setForeground(Color.WHITE);
                frame.add(label);
                System.out.println(x + "," + y);
            }
        });
        return new Employee();
        //return new User(idField.getText(), accessField.getText(), userField.getText(), passField.getText());
    }

    public String[] getEmployees() throws SQLException {
        Connector con = new Connector();
        ArrayList<String> employeeListArrList = con.retrieveUserList();
        String[] nameListArr = new String[employeeListArrList.size()];
        for (int i = 0; i < nameListArr.length; i++) {
            nameListArr[i] = employeeListArrList.get(i);
        }
        return nameListArr;
    }

    class LbBlink implements ActionListener {
        private JLabel label;
        private Color cor1 = Color.red;
        private Color cor2 = Color.white;
        private int count;

        public LbBlink(javax.swing.JLabel label) {
            this.label = label;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (count % 2 == 0)
                label.setForeground(cor1);
            else
                label.setForeground(cor2);
            count++;
            if(count == 6) {
                timerLB.stop();
                label.setText(" ");
            }
        }
    }
    public void toggleButtons(JButton[] btnArr) {
        //for(Jbutton)
    }
}
