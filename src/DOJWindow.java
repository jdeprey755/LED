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

public class DOJWindow {
    private int docketNumber = 0;
    private CourtCase courtCase;

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

        //border for panels and textfields
        BevelBorder bevelBorder = new BevelBorder(BevelBorder.RAISED, new Color(227, 227, 227), new Color(33, 33, 33));
        LineBorder lineBorder = new LineBorder(Color.DARK_GRAY, 1);

        JPanel searchPanel = new JPanel(null); // cosmetic panel for search box
        searchPanel.setBounds(600, 375, 650, 350);
        searchPanel.setBackground(Color.lightGray);
        searchPanel.setForeground(Color.WHITE);
        searchPanel.setBorder(bevelBorder);
        frame.add(searchPanel);

        JLabel title = new JLabel("Department of Justice Records");
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

        JTextField docketField = new JTextField();
        docketField.setBorder(lineBorder);
        docketField.setBounds(275, 75, 200, 25);
        searchPanel.add(docketField);

        JPanel outputPanel = new JPanel(); // cosmetic panel for search box
        outputPanel.setBounds(900, 250, 350, 400);
        outputPanel.setBackground(Color.lightGray);
        outputPanel.setBorder(GUI.getBevel());
        outputPanel.setLayout(null);
        frame.add(outputPanel);
        outputPanel.setVisible(false);

        //output labels
        JLabel outputTitle = new JLabel();
        outputTitle.setFont(new Font("Arial", Font.BOLD, 18));
        outputTitle.setForeground(Color.darkGray);
        outputTitle.setBounds(10, 0, 350, 50);
        outputPanel.add(outputTitle);

        JLabel titleLbl = new JLabel();
        titleLbl.setForeground(Color.darkGray);
        titleLbl.setBounds(30, 50, 150, 25);
        outputPanel.add(titleLbl);

        JLabel dateLbl = new JLabel("Perp");
        dateLbl.setForeground(Color.darkGray);
        dateLbl.setBounds(30, 75, 150, 25);
        outputPanel.add(dateLbl);

        JLabel plaintiffLbl = new JLabel();
        plaintiffLbl.setForeground(Color.darkGray);
        plaintiffLbl.setBounds(30, 100, 150, 25);
        outputPanel.add(plaintiffLbl);

        JLabel defendantLbl = new JLabel();
        defendantLbl.setForeground(Color.darkGray);
        defendantLbl.setBounds(30, 125, 150, 25);
        outputPanel.add(defendantLbl);

        JLabel verdictLbl = new JLabel();
        verdictLbl.setForeground(Color.darkGray);
        verdictLbl.setBounds(30, 150, 150, 25);
        outputPanel.add(verdictLbl);

        JLabel sentenceLbl = new JLabel();
        sentenceLbl.setForeground(Color.darkGray);
        sentenceLbl.setBounds(30, 175, 300, 25);
        outputPanel.add(sentenceLbl);

        JButton searchBtn = GUI.createAlternateButton("SEARCH", 250, 250);
        searchPanel.add(searchBtn);
        searchPanel.add(searchBtn);
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                docketNumber = Integer.parseInt(docketField.getText());
                try {
                    Connector con = new Connector();
                    courtCase = con.retrieveCourtCase(docketNumber);
                } catch (SQLException er) {
                    System.out.println("Error running con.retrieveCourtCase\nError: " + er);
                }

                if (false) {
                    //No case found
                } else {
                    searchPanel.setVisible(false);
                    outputTitle.setText("DOCKET " + courtCase.getDocket());
                    titleLbl.setText(courtCase.getTitle());
                    dateLbl.setText(courtCase.getDate());
                    plaintiffLbl.setText("Plaintiff: " + courtCase.getPlaintiff());
                    defendantLbl.setText("Defendant: " + courtCase.getDefendant());
                    verdictLbl.setText("Verdict: " + courtCase.getVerdict());
                    sentenceLbl.setText("Sentence: " + courtCase.getSentence());
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
