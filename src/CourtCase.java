public class CourtCase {
    private int docket;
    private String date;
    private String title;
    private String plaintiff;
    private String defendant;
    private String verdict;
    private String sentence;
    private int caseNumber;

    public CourtCase(int docket, String date, String title, String plaintiff, String defendant, String verdict, String sentence, int caseNumber) {
        this.docket = docket;
        this.date = date;
        this.title = title;
        this.plaintiff = plaintiff;
        this.defendant = defendant;
        this.verdict = verdict;
        this.sentence = sentence;
        this.caseNumber = caseNumber;
    }

    public int getDocket() {
        return docket;
    }

    public void setDocket(int docket) {
        this.docket = docket;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }
}
