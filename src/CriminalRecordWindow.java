import javax.imageio.ImageIO;
import javax.swing.*;
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

public class CriminalRecordWindow {
    Connector con = new Connector();
    ArrayList<CriminalRecord> crList = new ArrayList<CriminalRecord>();
    private String firstName = "";
    private String lastName = "";
    private String dlNumber = "";
    public void run(Employee employee) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLayout(null);
        try { BufferedImage myImage = ImageIO.read(new File("Resources/LEDWallpaper.jpg"));
            frame.setContentPane(new ImagePanel(myImage));
        } catch (IOException e) {}

        JLabel title = new JLabel("Criminal Records Database");
        title.setBounds(700, 15, 500, 100);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial Pro Medium", Font.PLAIN, 32));
        frame.add(title);

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

        JTextField nameField = new JTextField(); //takes in name as field
        nameField.setBorder(GUI.getLine());
        nameField.setBounds(115, 50, 200, 25);
        searchPanel.add(nameField);

        JPanel outputPanel = new JPanel(); // cosmetic panel for search box
        outputPanel.setBounds(900, 250, 350, 400);
        outputPanel.setBackground(Color.lightGray);
        outputPanel.setBorder(GUI.getBevel());
        outputPanel.setLayout(null);
        frame.add(outputPanel);
        outputPanel.setVisible(false);

        JLabel outputTitle = new JLabel("CRIMINAL RECORD");
        outputTitle.setFont(new Font("Arial", Font.BOLD, 18));
        outputTitle.setForeground(Color.darkGray);
        outputTitle.setBounds(10, 0, 350, 25);
        outputPanel.add(outputTitle);

        //labels for fields
        JLabel selectedNameLbl = new JLabel();
        selectedNameLbl.setForeground(Color.darkGray);
        selectedNameLbl.setBounds(30, 75, 150, 25);
        outputPanel.add(selectedNameLbl);

        JLabel descriptionLbl = new JLabel();
        descriptionLbl.setForeground(Color.darkGray);
        descriptionLbl.setBounds(30, 100, 250, 25);
        outputPanel.add(descriptionLbl);

        DefaultListModel listModel; //DefaultListModel object is created, used when creating list obj
        listModel = new DefaultListModel();

        JButton searchBtn = GUI.createAlternateButton("SEARCH", 750, 700);
        frame.add(searchBtn);

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get fields from textfields
                firstName = nameField.getText();
                try {
                    crList = con.retrieveCriminalRecords(firstName);
                } catch (SQLException er) {System.out.println("Error running con.retrieveCriminalRecords\nError: " + er);}
                System.out.println();

                if(crList.size() == 0) {
                    //No records found
                } else {
                    //populate listModel object
                    listModel.clear();
                    for (CriminalRecord cRecord : crList) {
                        listModel.addElement(cRecord.getFirstName());
                    }

                    //make JList component using listModel object
                    JList list = new JList(listModel);
                    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    list.setBounds(300, 100, 200, 500);
                    list.setVisible(true);
                    list.addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            if(list.getSelectedIndex() > -1) {
                                selectedNameLbl.setText(crList.get(list.getSelectedIndex()).getFirstName());
                                descriptionLbl.setText(crList.get(list.getSelectedIndex()).getDescription());
                            }
                        }
                    });
                    frame.add(list);

                    outputPanel.setVisible(true);
                    //hide search GUI
                    nameLbl.setVisible(false);
                    nameField.setVisible(false);
                    searchBtn.setVisible(false);
                    searchPanel.setVisible(false);

                    JButton backToSearchBtn = GUI.createButton("  Back to Search", 15, 80);
                    backToSearchBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            list.setVisible(false);
                            backToSearchBtn.setVisible(false);
                            nameLbl.setVisible(true);
                            nameField.setVisible(true);
                            searchBtn.setVisible(true);
                            outputPanel.setVisible(false);
                            searchPanel.setVisible(true);
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
        frame.setVisible(true);
        }
    }

