public class Employee {
    private int id;
    private String userName;
    private String password;
    private String userAccess;

    public Employee() {
    }

    public Employee(String userName, String userAccess) {
        this.userName = userName;
        this.userAccess = userAccess;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(String userAccess) {
        this.userAccess = userAccess;
    }
}
