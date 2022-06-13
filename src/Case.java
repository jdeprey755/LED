public class Case {
    private int caseNumber;
    private String perpetrator;
    private String victim;
    private String date;
    private String description;

    public Case(int caseNumber, String perpetrator, String victim, String date, String description) {
        this.caseNumber = caseNumber;
        this.perpetrator = perpetrator;
        this.victim = victim;
        this.date = date;
        this.description = description;
    }

    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getPerpetrator() {
        return perpetrator;
    }

    public void setPerpetrator(String perpetrator) {
        this.perpetrator = perpetrator;
    }

    public String getVictim() {
        return victim;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
