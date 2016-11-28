package paygate.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.iveri.bouncycastle.util.encoders.Base64;

/**
 *
 * @author Joseph
 */
public class AES {

    private static byte[] Encrypt(String plainText, String key) throws
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        // Instantiate the cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

        return Base64.encode(encryptedTextBytes);
    }

    private static String Decrypt(String encryptedText, String key) throws
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException {

        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        // Instantiate the cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] encryptedTextBytes = new Base64().decode(encryptedText);
        byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);

        return new String(decryptedTextBytes);
    }
}
