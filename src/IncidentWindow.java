import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class IncidentWindow {
    private JButton reportBtn;
    private ArrayList<Incident> incidentLog = new ArrayList<Incident>();

    public void run(Employee employee) throws SQLException {
        Connector con = new Connector();
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        try {
            BufferedImage myImage = ImageIO.read(new File("Resources/LEDWallpaper.jpg"));
            frame.setContentPane(new ImagePanel(myImage));
        } catch (IOException e) {}

        JLabel title = new JLabel("Incident Report Log");
        title.setBounds(800, 15, 500, 100);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial Pro Medium", Font.PLAIN, 32));
        frame.add(title);

        //output panel
        JPanel outputPanel = new JPanel(null); // cosmetic panel for search box
        outputPanel.setBounds(1100, 250, 350, 400);
        outputPanel.setBackground(Color.lightGray);
        outputPanel.setBorder(GUI.getBevel());
        frame.add(outputPanel);

        //output labels
        JLabel outputTitle = new JLabel();
        outputTitle.setFont(new Font("Arial", Font.BOLD, 18));
        outputTitle.setForeground(Color.darkGray);
        outputTitle.setBounds(10, 0, 350, 100);
        outputPanel.add(outputTitle);

        JLabel descriptionLbl = new JLabel();
        descriptionLbl.setForeground(Color.darkGray);
        descriptionLbl.setBounds(30, 100, 150, 25);
        outputPanel.add(descriptionLbl);

        JLabel dateLbl = new JLabel();
        dateLbl.setForeground(Color.darkGray);
        dateLbl.setBounds(30, 125, 150, 25);
        outputPanel.add(dateLbl);

        JLabel reportedByLbl = new JLabel();
        reportedByLbl.setForeground(Color.darkGray);
        reportedByLbl.setBounds(30, 150, 250, 25);
        outputPanel.add(reportedByLbl);

        JLabel filedByLbl = new JLabel();
        filedByLbl.setForeground(Color.darkGray);
        filedByLbl.setBounds(30, 150, 250, 25);
        outputPanel.add(filedByLbl);

        JButton backBtn = GUI.createButton("  Back", 15, 15);
        frame.add(backBtn);
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainWindow main = new MainWindow();
                main.run(employee);
                frame.setVisible(false);
            }
        });

        try {
            incidentLog = con.retrieveIncidentLog();
        } catch (SQLException e) {};

        String[] incidentArr = getIncidents();
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        for(String incidentTitle : incidentArr) {
            listModel.addElement(incidentTitle);
        }

        JList list = new JList(listModel);  //list component to store incidents
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setBorder(GUI.getLine());
        list.setBounds(300, 20, 400, 1000);
        list.setFont(new Font("Arial", Font.BOLD, 16));
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(list.getSelectedIndex() > -1) {
                    outputTitle.setText(incidentLog.get(list.getSelectedIndex()).getTitle());
                }
            }
        });
        frame.add(list);
        JPanel createPanel = new JPanel(null);
        createPanel.setBounds(600, 375, 650, 350);
        createPanel.setBackground(Color.lightGray);
        createPanel.setForeground(Color.WHITE);
        createPanel.setBorder(GUI.getBevel());
        frame.add(createPanel);
        createPanel.setVisible(false);

        JLabel createTitleLbl = new JLabel("Title");
        createTitleLbl.setBounds(200, 75, 100, 15);
        createPanel.add(createTitleLbl);

        JLabel createReportedByLbl = new JLabel("Reported By");
        createReportedByLbl.setBounds(200, 100, 200, 15);
        createPanel.add(createReportedByLbl);

        JLabel createDescLbl = new JLabel("Description");
        createDescLbl.setBounds(200, 125, 200, 15);
        createPanel.add(createDescLbl);

        JTextField titleField = new JTextField();
        titleField.setBorder(GUI.getLine());
        titleField.setBounds(275, 75, 105, 20);
        createPanel.add(titleField);

        JTextField reportedByField = new JTextField();
        reportedByField.setBorder(GUI.getLine());
        reportedByField.setBounds(275, 100, 105, 20);
        createPanel.add(reportedByField);

        JTextArea descField = new JTextArea();
        descField.setBorder(GUI.getLine());
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        descField.setBounds(275, 125, 200, 150);
        createPanel.add(descField);

        //button for submitting new employee information
        JButton submitBtn = GUI.createAlternateButton("SUBMIT", 250, 300);
        createPanel.add(submitBtn);
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Connector con = new Connector();
                try {
                    con.insertIncident(titleField.getText(), descField.getText(), dateFormat.format(date), reportedByField.getText(), employee.getUserName());
                } catch (SQLException ex) {}
                //repopulate employee data
                DefaultListModel<String> model = new DefaultListModel<>();
                String[] incArr = null;
                try {incArr = getIncidents();incidentLog = con.retrieveIncidentLog();} catch (SQLException ex) {System.out.println("Error retrieving Incidents at incArr = getIncidents()");}

                 for(String incident : incArr) {
                    model.addElement(incident);
                }
                list.setModel(model);
                list.setVisible(true);
                createPanel.setVisible(false);
                reportBtn.setVisible(true);
                outputPanel.setVisible(true);
            }
        });

        reportBtn = GUI.createButton("  Report Incident", 15, 80);
        frame.add(reportBtn);
        reportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list.setVisible(false);
                reportBtn.setVisible(false);
                outputPanel.setVisible(false);
                createPanel.setVisible(true);
            }
        });

        frame.setVisible(true);
    }
    public String[] getIncidents() throws SQLException {
        Connector con = new Connector();
        ArrayList<Incident> incidentArrayList = con.retrieveIncidentLog();
        String[] nameListArr = new String[incidentArrayList.size()];
        for (int i = 0; i < nameListArr.length; i++) {
            nameListArr[i] = incidentArrayList.get(i).getTitle();
        }
        return nameListArr;
    }
}
