import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DMVWindow {
    Connector con = new Connector();
    ArrayList<DMVRecord> dmvList = new ArrayList<DMVRecord>();
    String firstName = " ";
    String lastName = " ";
    String address = " ";
    String make = " ";
    String model = " ";
    String dlNumber = " ";
    private Timer timerLB;
    public DMVWindow() {}
    public void run(Employee employee) {
        JFrame frame = new JFrame(); //make DMVWindow frame
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        frame.getContentPane().setBackground(new Color(0,0,0));
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setResizable(false);
        frame.setLayout(null);
        try {
            BufferedImage myImage = ImageIO.read(new File("Resources/LEDWallpaper.jpg"));
            frame.setContentPane(new ImagePanel(myImage));
        } catch (IOException e) {}
        frame.setVisible(true);

        JPanel searchPanel = new JPanel(); // cosmetic panel for search box
        searchPanel.setBounds(650, 250, 350, 400);
        searchPanel.setBackground(Color.lightGray);
        searchPanel.setBorder(GUI.getBevel());
        searchPanel.setLayout(null);
        frame.add(searchPanel);

        //labels for fields
        JLabel nameLbl = new JLabel("Name");
        nameLbl.setForeground(Color.darkGray);
        nameLbl.setBounds(30, 50, 50, 25);
        searchPanel.add(nameLbl);

        JLabel addressLbl = new JLabel("Address");
        addressLbl.setForeground(Color.darkGray);
        addressLbl.setBounds(30, 90, 100, 25);
        searchPanel.add(addressLbl);

        JLabel makeLbl = new JLabel("Make");
        makeLbl.setForeground(Color.darkGray);
        makeLbl.setBounds(30, 130, 100, 25);
        searchPanel.add(makeLbl);

        JLabel modelLbl = new JLabel("Model");
        modelLbl.setForeground(Color.darkGray);
        modelLbl.setBounds(30, 170, 100, 25);
        searchPanel.add(modelLbl);

        JLabel dlNumberLbl = new JLabel("DL Number");
        dlNumberLbl.setForeground(Color.darkGray);
        dlNumberLbl.setBounds(30, 210, 100, 25);
        searchPanel.add(dlNumberLbl);

        JTextField nameField = new JTextField(); //takes in name as field
        nameField.setBorder(GUI.getLine());
        nameField.setBounds(115, 50, 200, 25);
        searchPanel.add(nameField);

        JTextField addressField = new JTextField(); //takes in address as field
        addressField.setBorder(GUI.getLine());
        addressField.setBounds(115, 90, 200, 25);
        searchPanel.add(addressField);

        JTextField makeField = new JTextField(); //takes in vehicle make
        makeField.setBorder(GUI.getLine());
        makeField.setBounds(115, 130, 200, 25);
        searchPanel.add(makeField);

        JTextField modelField = new JTextField(); //takes in vehicle model
        modelField.setBorder(GUI.getLine());
        modelField.setBounds(115, 170, 200, 25);
        searchPanel.add(modelField);

        JTextField dlNumberField = new JTextField(); //takes in driver's license number as field
        dlNumberField.setBorder(GUI.getLine());
        dlNumberField.setBounds(115, 210, 200, 25);
        searchPanel.add(dlNumberField);

        JPanel outputPanel = new JPanel(); // cosmetic panel for search box
        outputPanel.setBounds(900, 250, 350, 400);
        outputPanel.setBackground(Color.lightGray);
        outputPanel.setBorder(GUI.getBevel());
        outputPanel.setLayout(null);
        frame.add(outputPanel);
        outputPanel.setVisible(false);

        JLabel outputTitle = new JLabel("DEPARTMENT OF MOTOR VEHICLES");
        outputTitle.setFont(new Font("Arial", Font.BOLD, 18));
        outputTitle.setForeground(Color.darkGray);
        outputTitle.setBounds(10, 0, 350, 100);
        outputPanel.add(outputTitle);

        //labels for fields
        JLabel selectedNameLbl = new JLabel("Name");
        selectedNameLbl.setForeground(Color.darkGray);
        selectedNameLbl.setBounds(30, 75, 150, 25);
        outputPanel.add(selectedNameLbl);

        JLabel selectedAddressLbl = new JLabel("Address");
        selectedAddressLbl.setForeground(Color.darkGray);
        selectedAddressLbl.setBounds(30, 100, 250, 25);
        outputPanel.add(selectedAddressLbl);

        JLabel selectedMakeLbl = new JLabel("Make");
        selectedMakeLbl.setForeground(Color.darkGray);
        selectedMakeLbl.setBounds(30, 125, 150, 25);
        outputPanel.add(selectedMakeLbl);

        JLabel selectedModelLbl = new JLabel("Model");
        selectedModelLbl.setForeground(Color.darkGray);
        selectedModelLbl.setBounds(30, 150, 150, 25);
        outputPanel.add(selectedModelLbl);

        JLabel selectedDLNumberLbl = new JLabel("DL Number");
        selectedDLNumberLbl.setForeground(Color.darkGray);
        selectedDLNumberLbl.setBounds(30, 175, 150, 25);
        outputPanel.add(selectedDLNumberLbl);


        DefaultListModel listModel; //DefaultListModel object is created, used when creating list obj
        listModel = new DefaultListModel();
        JButton searchBtn = GUI.createAlternateButton("SEARCH", 140, 300); //search button for dmv database
        searchPanel.add(searchBtn);
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get fields from textfields
                String fullName = nameField.getText();
                //checking if user entered first and last name together
                boolean whitespace = false;
                if(fullName.length() > 2) {
                    for (int i = 0; i < fullName.length(); i++) {
                        if (fullName.charAt(i) == ' ') {
                            whitespace = true;
                        }
                    }
                }
                if(whitespace) {
                    String[] nameArr = fullName.split(" ");
                    firstName = nameArr[0];
                    lastName = nameArr[1];
                } else {
                    firstName = fullName;
                }
                address = addressField.getText();
                model = modelField.getText();
                make = makeField.getText();
                dlNumber = dlNumberField.getText();
                try {
                    dmvList = con.retrieveDMVRecords(firstName, lastName, address, make, model, dlNumber);
                } catch (SQLException er) {System.out.println("Error running con.retrieveDMVRecords" + er);}

                //populate listModel object
                listModel.clear();
                for(DMVRecord dmvRecord: dmvList) {
                    listModel.addElement(dmvRecord.getFirstName());
                }

                if(dmvList.size() == 0 || dmvList.size() < 0) {
                    JLabel noRecordsLbl = new JLabel("No Records were Found");
                    noRecordsLbl.setForeground(Color.white);
                    noRecordsLbl.setFont(new Font("Arial Pro Medium", Font.BOLD, 24));
                    noRecordsLbl.setBounds(800, 300, 400, 100);
                    timerLB = new Timer(250, new DMVWindow.LbBlink(noRecordsLbl));
                    frame.repaint();
                    timerLB.start();
                    searchPanel.add(noRecordsLbl);

                } else {
                    JLabel recordsFoundLbl = new JLabel(dmvList.size() + " Records Found");
                    recordsFoundLbl.setForeground(Color.WHITE);
                    recordsFoundLbl.setFont(new Font("Arial", Font.BOLD, 20));
                    recordsFoundLbl.setBounds(600, 25, 200, 100);
                    frame.add(recordsFoundLbl);

                    searchPanel.setVisible(false);
                    //make JList component using listModel object
                    JList list = new JList(listModel);
                    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    list.setBounds(600, 100, 200, 800);
                    list.setBorder(GUI.getLine());
                    list.addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            if(list.getSelectedIndex() > -1) {
                                selectedNameLbl.setText("Name: " + dmvList.get(list.getSelectedIndex()).getFirstName() + " " + dmvList.get(list.getSelectedIndex()).getLastName());
                                selectedAddressLbl.setText("Address: " + dmvList.get(list.getSelectedIndex()).getAddress());
                                selectedMakeLbl.setText("Make: " + dmvList.get(list.getSelectedIndex()).getMake());
                                selectedModelLbl.setText("Model: " + dmvList.get(list.getSelectedIndex()).getModel());
                                selectedDLNumberLbl.setText("DLNumber: " + dmvList.get(list.getSelectedIndex()).getDlNumber());
                            }
                            outputPanel.setVisible(true);
                        }
                    });
                    JScrollPane scroll = new JScrollPane(list);
                    scroll.setBounds(575, 105, 300, 700);
                    frame.add(scroll);
                    frame.repaint();

                    JButton backToSearchBtn = GUI.createButton("  Back to Search", 15, 80);
                    backToSearchBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            searchPanel.setVisible(true);
                            backToSearchBtn.setVisible(false);
                            scroll.setVisible(false);
                            recordsFoundLbl.setVisible(false);
                            outputPanel.setVisible(false);
                        }
                    });
                    frame.add(backToSearchBtn);
                    frame.repaint();
                }
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
}
