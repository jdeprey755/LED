public class CriminalRecord {
    private String firstName;
    private String description;

    public CriminalRecord(String firstName, String description) {
        this.firstName = firstName;
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

