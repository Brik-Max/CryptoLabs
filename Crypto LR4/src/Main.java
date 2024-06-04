import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Генерация ключа для шифрования
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // Указываем размер ключа
        SecretKey secretKey = keyGenerator.generateKey();

        // Создание экземпляра Cipher для алгоритма AES
        Cipher cipher = Cipher.getInstance("AES");

        // Инициализация Cipher в режиме шифрования с нашим ключом
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        String secretMessage = "Cъешь ещё этих мягких французских булок, да выпей чаю";
        byte[] encryptedMessage = cipher.doFinal(secretMessage.getBytes());

        // Преобразование зашифрованного сообщения в строку для отправки
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessage);
        System.out.println("\nЗашифрованное сообщение: " + encodedMessage);

        // Инициализация Cipher в режиме дешифрования
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encodedMessage));
        String decryptedMessage = new String(decryptedBytes);
        System.out.println("Расшифрованное сообщение: " + decryptedMessage);




    }
}