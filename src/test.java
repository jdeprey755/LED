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

    createSearch();

    }
    public static JPanel createSearch() {
        JPanel searchPanel = new JPanel(null);

        return new JPanel();
    }
}

//pVt4L6gS+dKyNm/C5nTxgA== ThisIsAVerySecretKey
//whz3PCgxqu8X2Uh46ftVIw== ThisIsAVerySecretKey1