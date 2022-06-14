public class Incident {
    private int incidentNumber;
    private String title;
    private String description;
    private String date;
    private String reportedBy; //citizen that reported the incident
    private String filedBy; //employee that entered the incident into the log

    public Incident(int incidentNumber, String title, String description, String date, String reportedBy, String filedBy) {
        this.incidentNumber = incidentNumber;
        this.title = title;
        this.description = description;
        this.date = date;
        this.reportedBy = reportedBy;
        this.filedBy = filedBy;
    }
    public Incident() {
    }

    public int getIncidentNumber() {
        return incidentNumber;
    }

    public void setIncidentNumber(int incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getFiledBy() {
        return filedBy;
    }

    public void setFiledBy(String filedBy) {
        this.filedBy = filedBy;
    }

    public String toString() {
        return "Incident{" +
                "incidentNumber=" + incidentNumber +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", reportedBy='" + reportedBy + '\'' +
                ", filedBy='" + filedBy + '\'' +
                '}';
    }
}
