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

public class CaseWindow {
    Connector con = new Connector();
    Case caseObj;
    private int caseNumber = 0;

    public void run(Employee employee) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLayout(null);
        try {
            BufferedImage myImage = ImageIO.read(new File("Resources/LEDWallpaper.jpg"));
            frame.setContentPane(new ImagePanel(myImage));
        } catch (IOException e) {
        }

        JPanel searchPanel = new JPanel(null); // cosmetic panel for search box
        searchPanel.setBounds(600, 375, 650, 350);
        searchPanel.setBackground(Color.lightGray);
        searchPanel.setBorder(GUI.getBevel());
        frame.add(searchPanel);

        JLabel title = new JLabel("Case Search");
        title.setBounds(750, 15, 500, 100);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial Pro Medium", Font.PLAIN, 32));
        frame.add(title);

        //labels for fields
        JLabel caseNumberLbl = new JLabel("Case Number");
        caseNumberLbl.setForeground(Color.darkGray);
        caseNumberLbl.setFont(new Font("Arial Pro Mediun", Font.PLAIN, 18));
        caseNumberLbl.setBounds(100, 75, 150, 25);
        searchPanel.add(caseNumberLbl);

        JTextField caseNumberField = new JTextField();
        caseNumberField.setBorder(GUI.getLine());
        caseNumberField.setBounds(275, 75, 200, 25);
        searchPanel.add(caseNumberField);

        JPanel outputPanel = new JPanel(); // cosmetic panel for search box
        outputPanel.setBounds(900, 250, 600, 400);
        outputPanel.setBackground(Color.lightGray);
        outputPanel.setBorder(GUI.getBevel());
        outputPanel.setLayout(null);
        frame.add(outputPanel);
        outputPanel.setVisible(false);

        //output labels
        JLabel outputTitle = new JLabel("CASE ");
        outputTitle.setFont(new Font("Arial", Font.BOLD, 18));
        outputTitle.setForeground(Color.darkGray);
        outputTitle.setBounds(10, 0, 350, 100);
        outputPanel.add(outputTitle);

        JLabel perpetratorLbl = new JLabel();
        perpetratorLbl.setForeground(Color.darkGray);
        perpetratorLbl.setBounds(30, 100, 150, 25);
        outputPanel.add(perpetratorLbl);

        JLabel victimLbl = new JLabel();
        victimLbl.setForeground(Color.darkGray);
        victimLbl.setBounds(30, 125, 150, 25);
        outputPanel.add(victimLbl);

        JLabel dateLbl = new JLabel();
        dateLbl.setForeground(Color.darkGray);
        dateLbl.setBounds(30, 150, 150, 25);
        outputPanel.add(dateLbl);

        JLabel descriptionLbl = new JLabel();
        descriptionLbl.setForeground(Color.darkGray);
        descriptionLbl.setBounds(30, 180, 500, 25);
        outputPanel.add(descriptionLbl);

        JButton searchBtn = GUI.createAlternateButton("SEARCH", 275, 250);
        searchPanel.add(searchBtn);
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caseNumber = Integer.parseInt(caseNumberField.getText());
                try {
                    caseObj = con.retrieveCase(caseNumber);
                } catch (SQLException er) {
                    System.out.println("Error running con.retrieveCriminalRecords\nError: " + er);
                }

                if (false) {
                    //No case found
                } else {
                    searchPanel.setVisible(false);
                    if(caseObj != null) {
                        outputTitle.setText("CASE NUMBER " + caseObj.getCaseNumber());
                        perpetratorLbl.setText("Perpetrator: " + caseObj.getPerpetrator());
                        victimLbl.setText("Victim: " + caseObj.getVictim());
                        dateLbl.setText(caseObj.getDate());
                        descriptionLbl.setText(caseObj.getDescription());
                    }
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

