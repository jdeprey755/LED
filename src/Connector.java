import java.sql.*;
import java.util.ArrayList;

public class Connector {
    private String pass = "686462s";
    //String pass = "pass12";
    public Connector() {
    }

    public void insertEvidence(int caseNumber, String description) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        PreparedStatement st = null;
        String sql = ("INSERT INTO EVIDENCE (CaseNumber, ItemDescription) values(?, ?);");
        st = con.prepareStatement(sql);
        st.setInt(1, caseNumber);
        st.setString(2, description);
        int rows = st.executeUpdate();
        if(rows == 1) {
            //success
        }
        con.close();
    }

    public ArrayList<Evidence> retrieveEvidenceLog() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        Statement st = con.createStatement();
        String sql = ("SELECT * FROM EVIDENCE");
        ResultSet rs = st.executeQuery(sql);
        ArrayList<Evidence> resultArray = new ArrayList<>();
        while(rs.next()) {
            int evidenceNumber = rs.getInt("EvidenceNumber");
            int caseNumber = rs.getInt("CaseNumber");
            String itemDescription = rs.getString("ItemDescription");

            resultArray.add(new Evidence(evidenceNumber, caseNumber, itemDescription));
        }
        con.close();

        return resultArray;
    }
    public PrisonRecord retrievePrisonRecord(String firstName, String lastName, int docket) throws SQLException {
        firstName = "%" + firstName + "%";
        lastName = "%" + lastName + "%";
        System.out.println("SEARCH INFO: \nFirstName: " + firstName + "\nLastName: " + lastName + "\nDocket: " + docket);
        PrisonRecord prisonRecord = null;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        ResultSet rs;
        if(docket == 0 ) {
            PreparedStatement st = con.prepareStatement("SELECT FirstName, LastName, Docket, Sentence, Correctional_Facility FROM DOC WHERE FirstName LIKE ? AND LastName LIKE ?");
            st.setString(1, firstName);
            st.setString(2, lastName);
            rs = st.executeQuery();
        } else {
            PreparedStatement st = con.prepareStatement("SELECT FirstName, LastName, Docket, Sentence, Correctional_Facility FROM DOC WHERE FirstName LIKE ? AND LastName LIKE ? AND Docket = ?");
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setInt(3, docket);
            rs = st.executeQuery();
        }
        while(rs.next()) {
            String firstNameResult = rs.getString("FirstName");
            String lastNameResult = rs.getString("LastName");
            int docketResult = rs.getInt("Docket");
            String sentence = rs.getString("Sentence");
            String facility = rs.getString("Correctional_Facility");
            prisonRecord = new PrisonRecord(firstNameResult, lastNameResult, docketResult, sentence, facility);
        }
        con.close();

        return prisonRecord;
    }
    public CourtCase retrieveCourtCase(int docket) throws SQLException {
        CourtCase courtCase = null;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        PreparedStatement st = con.prepareStatement("SELECT Docket, Date, Title, Plaintiff, Defendant, Verdict, Sentence, CaseNumber FROM DOJ WHERE Docket = ?");
        st.setInt(1, docket);
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            String date = rs.getString("Date");
            String title = rs.getString("Title");
            String plaintiff = rs.getString("Plaintiff");
            String defendant = rs.getString("Defendant");
            String verdict = rs.getString("Verdict");
            String sentence = rs.getString("Sentence");
            int caseNumber = rs.getInt("CaseNumber");
            courtCase = new CourtCase(docket, date, title, plaintiff, defendant, verdict, sentence, caseNumber);
        }
        con.close();

        return courtCase;
    }
    public void deleteEmployee(String username) throws SQLException {

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        PreparedStatement st = con.prepareStatement("DELETE FROM EMPLOYEE WHERE UserLogin LIKE ?");
        st.setString(1, username);
        int rows = st.executeUpdate();
        if(rows == 1) {
            //success
        }
        con.close();
    }
    public Case retrieveCase(int caseNumber) throws SQLException {
        Case caseObj = null;
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        PreparedStatement st = con.prepareStatement("SELECT Perpetrator, Victim, Date, Description FROM LED.CASE WHERE CaseNumber LIKE ?");
        st.setInt(1, caseNumber);
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            String perpetrator = rs.getString("Perpetrator");
            String victim = rs.getString("Victim");
            String date = rs.getString("DATE");
            String description = rs.getString("Description");
            caseObj = new Case(caseNumber, perpetrator, victim, date, description);
        }
        con.close();

        return caseObj;
    }

    public ArrayList<CriminalRecord> retrieveCriminalRecords(String firstName) throws SQLException {
        firstName = "%" + firstName + "%";

        //System.out.println("Connector Search Info: \nFirstName: " + firstName  + "\nLastName: " + lastName + "\nDLNumber: " + dlNumber);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        PreparedStatement st = con.prepareStatement("SELECT Perpetrator, Description FROM LED.CASE WHERE Perpetrator LIKE ?");
        st.setString(1, firstName);
        ResultSet rs = st.executeQuery();
        ArrayList<CriminalRecord> resultArray = new ArrayList<CriminalRecord>();
        while(rs.next()) {
            String nameStr = rs.getString("Perpetrator");
            String description = rs.getString("Description");
            resultArray.add(new CriminalRecord(nameStr, description));
        }
        con.close();

        return resultArray;
    }

    /*public int countEmployees() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        int count = 0;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(EmployeeID) FROM EMPLOYEE;");
        while(rs.next()) {
            count = rs.getInt("COUNT(EmployeeID)");
        }
        return count;
    }*/

    public ArrayList<String> retrieveInfo() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        Statement st = con.createStatement();
        String sql = ("SELECT * FROM DMV;");
        ResultSet rs = st.executeQuery(sql);
        ArrayList<String> resultArray = new ArrayList<String>();
        while(rs.next()) {
            String id = rs.getString("DLNumber");
            String firstName = rs.getString("FirstName");
            String state = rs.getString("State");
            resultArray.add(id + "\t" + firstName + "\t" + state);
        }

        con.close();

        return resultArray;
    }

    public String[] retrieveState() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        Statement st = con.createStatement();
        String sql = ("SELECT * FROM DMV ORDER BY State;");
        ResultSet rs = st.executeQuery(sql);
        String[] resultArray = new String[20];
        int counter = 0;
        while(rs.next()) {
            String id = rs.getString("DLNumber");
            String firstName = rs.getString("FirstName");
            String state = rs.getString("State");
            resultArray[counter] = id + "\t" + firstName + "\t" + state;
            counter++;
        }

        con.close();

        return resultArray;
    }

    public ArrayList<String> retrieveUserList() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        Statement st = con.createStatement();
        String sql = ("SELECT UserLogin FROM Employee");
        ResultSet rs = st.executeQuery(sql);
        ArrayList<String> resultArray = new ArrayList<String>();
        while(rs.next()) {
            String UserLogin = rs.getString("UserLogin");
            resultArray.add(UserLogin);
        }

        con.close();

        return resultArray;
    }

    public ArrayList<String> retrievePassList() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        Statement st = con.createStatement();
        String sql = ("SELECT PassLogin FROM Employee");
        ResultSet rs = st.executeQuery(sql);
        ArrayList<String> resultArray = new ArrayList<String>();
        while(rs.next()) {
            String UserLogin = rs.getString("PassLogin");
            resultArray.add(UserLogin);
        }

        con.close();

        return resultArray;
    }

    public void addNewUser(String accessLevel, String userLogin, String passLogin) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        PreparedStatement st = null;
        String sql = ("INSERT INTO EMPLOYEE (AccessLevel, UserLogin, PassLogin) VALUES (?, ?, ?)");
        st = con.prepareStatement(sql);
        st.setString(1, accessLevel);
        st.setString(2, userLogin);
        st.setString(3, passLogin);
        int rows = st.executeUpdate();
        if(rows == 1) {
            //success
        }
        con.close();
    }

    public String retrieveAccess(String userLogin) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        String accessLevel = "";
        PreparedStatement st = null;
        String sql = ("SELECT AccessLevel FROM EMPLOYEE WHERE UserLogin LIKE ?");
        st = con.prepareStatement(sql);
        st.setString(1, userLogin);
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            accessLevel = rs.getString(1);
        }
        con.close();

        return accessLevel;
    }

    public void insertIncident(String title, String description, String date, String reportedBy, String filedBy) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        PreparedStatement st = null;
        String sql = ("INSERT INTO INCIDENT (Title, Description, Date, Reported_By, Filed_By) values(?, ?, ?, ?, ?);");
        st = con.prepareStatement(sql);
        st.setString(1, title);
        st.setString(2, description);
        st.setString(3, date);
        st.setString(4, reportedBy);
        st.setString(5, filedBy);

        int rows = st.executeUpdate();
        if(rows == 1) {
            //success
        }
        con.close();
    }

    public ArrayList<Incident> retrieveIncidentLog() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        Statement st = con.createStatement();
        String sql = ("SELECT * FROM INCIDENT");
        ResultSet rs = st.executeQuery(sql);
        ArrayList<Incident> resultArray = new ArrayList<Incident>();
        while(rs.next()) {
            int incidentNumber = rs.getInt("IncidentNumber");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String date = rs.getString("Date");
            String reportedBy = rs.getString("Reported_By");
            String filedBy = rs.getString("Filed_By");

            resultArray.add(new Incident(incidentNumber, title, date, reportedBy, filedBy, description));
        }

        for(Incident incident : resultArray) {
            incident.toString();
        }
        con.close();

        return resultArray;
    }

    public ArrayList<DMVRecord> retrieveDMVRecords(String firstName, String lastName, String address, String make, String model, String dlNumber) throws SQLException {
        if(firstName.equals(" ")) {
            firstName = "%";
        } else {
            firstName = "%" + firstName + "%";
        }

        if(lastName.equals(" ")) {
            lastName = "%";
        } else {
            lastName = "%" + lastName + "%";
        }
        if(address.equals(" ")) {
            address = "%";
        } else {
            address = "%" + address + "%";
        }
        if(dlNumber.equals(" ")) {
            dlNumber = "%";
        } else {
            dlNumber = "%" + dlNumber + "%";
        }
        if(make.equals(" ")) {
            make = "%";
        } else {
            make = "%" + make + "%";
        }
        if(model.equals(" ")) {
            model = "%";
        } else {
            model = "%" + model + "%";
        }

        //System.out.println("Search Info in Connector:\nFirst name: " + firstName + "\nLast name: " + lastName + "\nAddress: " + address + "\nDL Number: " + dlNumber + "\nMake: " + make + "\nModel: " + model);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/LED", "root", pass);
        PreparedStatement st = con.prepareStatement("SELECT DMV.DLNumber, State, FirstName, LastName, DOB, Height, Eyes, Address, DateIssued, Sex, VEHICLE.Make, VEHICLE.Model FROM DMV JOIN VEHICLE ON DMV.DMVID = VEHICLE.DMVID WHERE FirstName LIKE ? AND LastName LIKE ? AND Address LIKE ? AND DMV.DMVID LIKE ? AND Model LIKE ? AND Make LIKE ?");
        st.setString(1, firstName);
        st.setString(2, lastName);
        st.setString(3, address);
        st.setString(4, dlNumber);
        st.setString(5, make);
        st.setString(6, model);
        ResultSet rs = st.executeQuery();
        ArrayList<DMVRecord> resultArray = new ArrayList<DMVRecord>();
        while(rs.next()) {
            String dlNumberStr = rs.getString("DLNumber");
            String state = rs.getString("State");
            String firstNameStr = rs.getString("FirstName");
            String lastNameStr = rs.getString("LastName");
            String DOB = rs.getString("DOB");
            String height = rs.getString("Height");
            String eyes = rs.getString("Eyes");
            String addressStr = rs.getString("Address");
            String dateIssued = rs.getString("DateIssued");
            String sex = rs.getString("Sex");
            String makeStr = rs.getString("Make");
            String modelStr = rs.getString("Model");
            //System.out.println("DEBUG: " + firstNameStr);
            resultArray.add(new DMVRecord(dlNumberStr, state, firstNameStr, lastNameStr, DOB, height, eyes, addressStr, makeStr, modelStr, dateIssued, sex));
        }
        con.close();

        return resultArray;
    }
}
