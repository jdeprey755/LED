import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class DOCWindow {
    private int docketNumber = 0;
    private CourtCase courtCase;
    private PrisonRecord prisonRecord;
    String firstName = "";
    String lastName = "";

    public void run(Employee employee) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLayout(null);
        try {
            BufferedImage myImage = ImageIO.read(new File("Resources/LEDWallpaper.jpg"));
            frame.setContentPane(new ImagePanel(myImage));
        } catch (
                IOException e) {
        }

        JPanel searchPanel = new JPanel(null); //panel for search box
        searchPanel.setBounds(600, 375, 650, 350);
        searchPanel.setBackground(Color.lightGray);
        searchPanel.setForeground(Color.WHITE);
        searchPanel.setBorder(GUI.getBevel());
        frame.add(searchPanel);

        JLabel title = new JLabel("Department of Corrections Records");
        title.setBounds(700, 15, 500, 100);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial Pro Medium", Font.PLAIN, 32));
        frame.add(title);

        //labels for fields
        JLabel docketLbl = new JLabel("Docket");
        docketLbl.setForeground(Color.darkGray);
        docketLbl.setFont(new Font("Arial Pro Mediun", Font.PLAIN, 18));
        docketLbl.setBounds(100, 75, 150, 25);
        searchPanel.add(docketLbl);

        JLabel nameLbl = new JLabel("Name");
        nameLbl.setForeground(Color.darkGray);
        nameLbl.setFont(new Font("Arial Pro Mediun", Font.PLAIN, 18));
        nameLbl.setBounds(100, 125, 150, 25);
        searchPanel.add(nameLbl);

        JTextField docketField = new JTextField();
        docketField.setBorder(GUI.getLine());
        docketField.setBounds(275, 75, 200, 25);
        searchPanel.add(docketField);

        JTextField nameField = new JTextField();
        nameField.setBorder(GUI.getLine());
        nameField.setBounds(275, 125, 200, 25);
        searchPanel.add(nameField);

        //output panel
        JPanel outputPanel = new JPanel(null); // cosmetic panel for search box
        outputPanel.setBounds(900, 250, 350, 400);
        outputPanel.setBackground(Color.lightGray);
        outputPanel.setBorder(GUI.getBevel());
        frame.add(outputPanel);
        outputPanel.setVisible(false);

        //output labels
        JLabel outputTitle = new JLabel("Department of Corrections");
        outputTitle.setFont(new Font("Arial", Font.BOLD, 18));
        outputTitle.setForeground(Color.darkGray);
        outputTitle.setBounds(10, 0, 350, 100);
        outputPanel.add(outputTitle);

        JLabel outputNameLbl = new JLabel();
        outputNameLbl.setForeground(Color.darkGray);
        outputNameLbl.setBounds(30, 100, 150, 25);
        outputPanel.add(outputNameLbl);

        JLabel sentenceLbl = new JLabel();
        sentenceLbl.setForeground(Color.darkGray);
        sentenceLbl.setBounds(30, 125, 250, 25);
        outputPanel.add(sentenceLbl);

        JLabel facilityLbl = new JLabel();
        facilityLbl.setForeground(Color.darkGray);
        facilityLbl.setBounds(30, 150, 250, 25);
        outputPanel.add(facilityLbl);


        JButton searchBtn = GUI.createAlternateButton("SEARCH", 250, 250);
        searchPanel.add(searchBtn);
        searchPanel.add(searchBtn);
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {String fullName = nameField.getText();
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
                if(docketField.getText().isEmpty()) {
                    docketNumber = 0;
                } else {
                    docketNumber = Integer.parseInt(docketField.getText());
                }

                try {
                    Connector con = new Connector();
                    prisonRecord = con.retrievePrisonRecord(firstName, lastName, docketNumber);
                } catch (SQLException er) {
                    System.out.println("Error running con.retrievePrisonRecord\nError: " + er);
                }

                if (prisonRecord == null) {

                } else {
                    searchPanel.setVisible(false);
                    outputNameLbl.setText(prisonRecord.getFirstName() + " " + prisonRecord.getLastName());
                    sentenceLbl.setText(prisonRecord.getSentence() + " Years");
                    facilityLbl.setText(prisonRecord.getFacility());
                    outputPanel.setVisible(true);

                    JButton backToSearchBtn = GUI.createButton("  Back to Search", 15, 80);
                    backToSearchBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            backToSearchBtn.setVisible(false);
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
