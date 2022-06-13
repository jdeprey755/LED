import java.io.*;
import java.util.Scanner;

public class FileFunction {
    public static void main(String[] args) throws IOException {
        writeFile();
    }
    public static void readFile() throws IOException {
        File file = new File("Resources/LED_insert_db.sql");
        Scanner reader = new Scanner(file);
        String check = "";
        while(!(check.equals("#Start"))) {
            check = reader.nextLine();
            if(check.equals("#Start")) {
                System.out.println("Found " + check);
                FileWriter writer = new FileWriter(file, true);
                writer.write("\ninsert into EMPLOYEE (EmployeeID, AccessLevel, UserLogin, PassLogin) values (4, \"High\", \"User\", \"1234\");");
                writer.close();
            }
        }
    }
    public static void writeFile() throws IOException {
        File file = new File("Resources/LED_insert_db.sql");
        FileWriter writer = new FileWriter(file, true);
        writer.write("\ninsert into EMPLOYEE (EmployeeID, AccessLevel, UserLogin, PassLogin) values (4, \"High\", \"User\", \"1234\");");
        System.out.println("File Written to");
        writer.close();
    }
}
