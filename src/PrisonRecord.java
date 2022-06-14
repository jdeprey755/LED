public class PrisonRecord {
    private String firstName;
    private String lastName;
    private int docket;
    private String sentence;
    private String facility;

    public PrisonRecord(String firstName, String lastName, int docket, String sentence, String facility) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.docket = docket;
        this.sentence = sentence;
        this.facility = facility;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDocket() {
        return docket;
    }

    public void setDocket(int docket) {
        this.docket = docket;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }
}
