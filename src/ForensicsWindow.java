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
import java.util.ArrayList;
import java.util.Date;

public class ForensicsWindow {
    private JButton logBtn;
    private ArrayList<Evidence> evidenceLog = new ArrayList<>();

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

        JLabel title = new JLabel("Forensics Locker");
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
            evidenceLog = con.retrieveEvidenceLog();
        } catch (SQLException e) {};

        String[] evidenceArr = getEvidence();
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        for(String incidentTitle : evidenceArr) {
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
                    outputTitle.setText(evidenceLog.get(list.getSelectedIndex()).getItemDescription());
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

        JLabel caseNumberLbl = new JLabel("Case Number");
        caseNumberLbl.setBounds(175, 75, 200, 15);
        createPanel.add(caseNumberLbl);

        JLabel descLbl = new JLabel("Item Description");
        descLbl.setBounds(175, 100, 200, 15);
        createPanel.add(descLbl);

        JTextField caseNumberField = new JTextField();
        caseNumberField.setBorder(GUI.getLine());
        caseNumberField.setBounds(275, 75, 105, 20);
        createPanel.add(caseNumberField);

        JTextField descField = new JTextField();
        descField.setBorder(GUI.getLine());
        descField.setBounds(275, 100, 105, 20);
        createPanel.add(descField);

        logBtn = GUI.createButton("  Log Evidence", 15, 80);
        try {
            if (employee.getUserAccess().equals("Forensic Technician")) {
                frame.getContentPane().add(logBtn);
            }
        } catch(NullPointerException e) {}
        logBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list.setVisible(false);
                logBtn.setVisible(false);
                outputPanel.setVisible(false);
                createPanel.setVisible(true);
            }
        });

        JButton logEvidenceBtn = GUI.createAlternateButton("SUBMIT", 250, 300);
        createPanel.add(logEvidenceBtn);
        logEvidenceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Connector con = new Connector();
                try {
                    con.insertEvidence(Integer.parseInt(caseNumberField.getText()), descField.getText());
                } catch (SQLException ex) {}
                //repopulate employee data
                DefaultListModel<String> model = new DefaultListModel<>();
                String[] evArr = null;
                try {evArr = getEvidence();evidenceLog = con.retrieveEvidenceLog();} catch (SQLException ex) {System.out.println("Error retrieving Incidents at incArr = getIncidents()");}

                for(String incident : evArr) {
                    model.addElement(incident);
                }
                list.setModel(model);
                list.setVisible(true);
                createPanel.setVisible(false);
                logBtn.setVisible(true);
                outputPanel.setVisible(true);
            }
        });
        frame.setVisible(true);
    }
    public String[] getEvidence() throws SQLException {
        Connector con = new Connector();
        ArrayList<Evidence> evidenceAL = con.retrieveEvidenceLog();
        String[] nameListArr = new String[evidenceAL.size()];
        for (int i = 0; i < nameListArr.length; i++) {
            nameListArr[i] = "Evidence #" + evidenceAL.get(i).getEvidenceNumber() + " involved in Case " + evidenceAL.get(i).getCaseNumber();
        }
        return nameListArr;
    }
}
