import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class MainWindow {
    JFrame frame;
    public MainWindow() {}

    public void run(Employee employee) {
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        frame.getContentPane().setBackground(new Color(19, 74, 240));
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        try {
            BufferedImage myImage = ImageIO.read(new File("Resources/LEDWallpaper.jpg"));
            frame.setContentPane(new ImagePanel(myImage));
        } catch (IOException e) {}

        JLabel employeeLbl = new JLabel("Welcome Back " + employee.getUserName());
        employeeLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        employeeLbl.setBounds(1475, 0, 175, 50);
        employeeLbl.setForeground(Color.WHITE);
        frame.add(employeeLbl);

        //button settings
        int xBtnPadding = 15;
        Color btnColor = new Color(5, 9, 105);
        BevelBorder bevelBorder = new BevelBorder(BevelBorder.RAISED, new Color(227, 227, 227), new Color(33, 33, 33));

        //start of buttons
        JButton caseBtn = GUI.createButton("  Case Search", 15, 15);
        frame.add(caseBtn);
        caseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               CaseWindow caseWindow = new CaseWindow();
               caseWindow.run(employee);
               frame.dispose();
            }
        });

        JButton courtRecordsBtn = GUI.createButton("  Court Records", xBtnPadding, 80);
        frame.getContentPane().add(courtRecordsBtn);
        courtRecordsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DOJWindow dojWindow = new DOJWindow();
                dojWindow.run(employee);
                frame.dispose();
            }
        });

        JButton criminalRecordsBtn = new JButton("  Criminal Records");
        criminalRecordsBtn.setBackground(btnColor);
        criminalRecordsBtn.setForeground(Color.WHITE);
        criminalRecordsBtn.setBounds(xBtnPadding, 15, 180, 50);
        criminalRecordsBtn.setFont(new Font("Arial Pro Medium", Font.BOLD, 16));
        criminalRecordsBtn.setHorizontalAlignment(SwingConstants.LEFT);
        criminalRecordsBtn.setBorder(bevelBorder);
        criminalRecordsBtn.setBounds(xBtnPadding, 145, 180, 50);
        frame.getContentPane().add(criminalRecordsBtn);
        criminalRecordsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CriminalRecordWindow crw = new CriminalRecordWindow();
                crw.run(employee);
                frame.dispose();
            }
        });

        JButton incidentReportBtn = new JButton("  Incident Report");
        incidentReportBtn.setBackground(btnColor);
        incidentReportBtn.setForeground(Color.WHITE);
        incidentReportBtn.setBounds(xBtnPadding, 15, 180, 50);
        incidentReportBtn.setFont(new Font("Arial Pro Medium", Font.BOLD, 16));
        incidentReportBtn.setHorizontalAlignment(SwingConstants.LEFT);
        incidentReportBtn.setBorder(bevelBorder);
        incidentReportBtn.setBounds(xBtnPadding, 210, 180, 50);
        frame.getContentPane().add(incidentReportBtn);
        incidentReportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IncidentWindow incidentWindow = new IncidentWindow();
                try {incidentWindow.run(employee);} catch (SQLException ex) {throw new RuntimeException(ex);}
                frame.dispose();
            }
        });

        JButton dmvRecordBtn = new JButton("  DMV Records");
        dmvRecordBtn.setBackground(btnColor);
        dmvRecordBtn.setForeground(Color.WHITE);
        dmvRecordBtn.setBounds(xBtnPadding, 15, 180, 50);
        dmvRecordBtn.setFont(new Font("Arial Pro Medium", Font.BOLD, 16));
        dmvRecordBtn.setHorizontalAlignment(SwingConstants.LEFT);
        dmvRecordBtn.setBorder(bevelBorder);
        dmvRecordBtn.setBounds(xBtnPadding, 275, 180, 50);
        dmvRecordBtn.setBackground(btnColor);
        dmvRecordBtn.setForeground(Color.WHITE);
        dmvRecordBtn.setHorizontalAlignment(SwingConstants.LEFT);

        frame.getContentPane().add(dmvRecordBtn);
        dmvRecordBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DMVWindow dmv = new DMVWindow();
                dmv.run(employee);
                frame.dispose();
            }
        });

        JButton docRecordsBtn = new JButton("  DOC Records");
        docRecordsBtn.setBackground(btnColor);
        docRecordsBtn.setForeground(Color.WHITE);
        docRecordsBtn.setFont(new Font("Arial Pro Medium", Font.BOLD, 16));
        docRecordsBtn.setHorizontalAlignment(SwingConstants.LEFT);
        docRecordsBtn.setBorder(bevelBorder);
        docRecordsBtn.setBounds(xBtnPadding, 340, 180, 50);
        frame.getContentPane().add(docRecordsBtn);
        docRecordsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DOCWindow docWindow = new DOCWindow();
                docWindow.run(employee);
                frame.dispose();
            }
        });

        JButton forensicRecordsBtn = GUI.createButton("  Forensics Locker", xBtnPadding, 405);
        frame.getContentPane().add(forensicRecordsBtn);
        forensicRecordsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ForensicsWindow forensicsWindow = new ForensicsWindow();
                try {forensicsWindow.run(employee); frame.dispose();} catch (SQLException ex) {throw new RuntimeException(ex);}
            }
        });

        JButton changeUserBtn = GUI.createButton("  Edit Users", xBtnPadding, 470);
        //check if user has proper permissions
        try {
            if (employee.getUserAccess().equals("Lieutenant")) {
                frame.getContentPane().add(changeUserBtn);
            }
        } catch(NullPointerException e) {}
        changeUserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditUserWindow editUserWindow = new EditUserWindow();
                try {
                    editUserWindow.run(employee);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frame.setVisible(false);
            }
        });

        JButton logoutBtn = GUI.createButton("  Logout", 1715, 15);
        frame.getContentPane().add(logoutBtn);
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    LoginWindow login = new LoginWindow();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //clock
        JLabel clock = new JLabel();
        clock.setFont(new Font("Arial", Font.BOLD, 20));
        clock.setBounds(900, 50, 200, 100);
        clock.setForeground(Color.WHITE);
        frame.add(clock);
        //update clock
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        new Timer().scheduleAtFixedRate(new TimerTask() {

            public void run() {
                //System.out.println(LocalTime.now().format(formatter));
                clock.setText(LocalTime.now().format(formatter));
            }
        }, 0, 1000);

        /*JLabel label = new JLabel();
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                label.setLocation(x,y);
                label.setText("(" + x + "," + y + ")");
                label.setSize(label.getPreferredSize());
                frame.add(label);
                System.out.println(x+","+y);
            }
        });*/
    }

    public JFrame getFrame() {
        return frame;
    }
}
