public class Evidence {
    private int evidenceNumber;
    private int caseNumber;
    private String itemDescription;

    public Evidence(int evidenceNumber, int caseNumber, String itemDescription) {
        this.evidenceNumber = evidenceNumber;
        this.caseNumber = caseNumber;
        this.itemDescription = itemDescription;
    }

    public int getEvidenceNumber() {
        return evidenceNumber;
    }

    public void setEvidenceNumber(int evidenceNumber) {
        this.evidenceNumber = evidenceNumber;
    }

    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
