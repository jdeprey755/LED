public class DMVRecord {
    private String dlNumber;
    private String state;
    private String firstName;
    private String lastName;
    private String DOB;
    private String height;
    private String eyes;
    private String address;
    private String make;
    private String model;
    private String dateIssued;
    private String sex;

    public DMVRecord() {
    }

    public DMVRecord(String dlNumber, String state, String firstName, String lastName, String DOB, String height, String eyes, String address, String make, String model, String dateIssued, String sex) {
        this.dlNumber = dlNumber;
        this.state = state;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.height = height;
        this.eyes = eyes;
        this.address = address;
        this.make = make;
        this.model = model;
        this.dateIssued = dateIssued;
        this.sex = sex;
    }

    public String getDlNumber() {
        return dlNumber;
    }

    public void setDlNumber(String dlNumber) {
        this.dlNumber = dlNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
