import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class test {
    private Timer timerLB;
    //privatimerLB = new Timer(1000, new test.LbBlink(warning));
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        /*Connector con = new Connector();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        String[] columnNames = {"#", "Date", "Title"};*/



        //Timer timerLB = new Timer(1000, new test.LbBlink(warning));

        /*final String secretKey = "ThisIsAVerySecretKey";

        String originalString = "YourBluffing";
        String encryptedString = AES.encrypt(originalString, secretKey) ;
        String decryptedString = AES.decrypt(encryptedString, secretKey) ;

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);

        System.out.println("pVt4L6gS+dKyNm/C5nTxgA==".equals(encryptedString));

         */
    }
    class LbBlink implements ActionListener {
        private javax.swing.JLabel label;
        private java.awt.Color cor1 = java.awt.Color.red;
        private java.awt.Color cor2 = java.awt.Color.gray;
        private int count;

        public LbBlink(javax.swing.JLabel label){
            this.label = label;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(count % 2 == 0)
                label.setForeground(cor1);
            else
                label.setForeground(cor2);
            count++;
        }
    }

}

//pVt4L6gS+dKyNm/C5nTxgA== ThisIsAVerySecretKey
//whz3PCgxqu8X2Uh46ftVIw== ThisIsAVerySecretKey1