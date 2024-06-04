import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        Random rand = new Random(System.currentTimeMillis()); // ГСПЧ

        BufferedReader reader = new BufferedReader(new FileReader("C://Users//MrBrick//Desktop//message.txt"));
        String msg = reader.readLine();

        int msg_len = msg.length(); // Узнаем длину сообщения

        try {
            File file = new File("C://Users//MrBrick//Desktop//key.txt"); // Создаем файл для ключа
            if (file.createNewFile()) {
                System.out.println("Файл создан");
            } else {
                System.out.println("Файл уже существует");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла");
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter("C://Users//MrBrick//Desktop//key.txt"); // Записываем случайные символы. Длина равна длине файла
            for (int i = 0 ; i < msg_len ; i++) {
                int rnd = rand.nextInt(26) + 97; //[97;122]
                char letter = (char) rnd;
                writer.write(letter);
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
            e.printStackTrace();
        }

        BufferedReader reader_key = new BufferedReader(new FileReader("C://Users//MrBrick//Desktop//key.txt"));
        String key = reader_key.readLine();
        System.out.println();
        System.out.println("Message: " + msg);
        System.out.println("Key:     " + key + "\n");


        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < msg.length(); i++)
            sb.append((char)(msg.charAt(i) ^ key.charAt(i % key.length())));
        String result = sb.toString();


        System.out.println("Result of XOR of two Strings:  " + result);

        StringBuilder sb2 = new StringBuilder();
        for(int i = 0; i < result.length(); i++)
            sb2.append((char)(result.charAt(i) ^ key.charAt(i)));
        String result2 = sb2.toString();

        System.out.println("Result of XOR of two Strings:  " + result2);


        // Поточный шифр RC4
        System.out.println("\nRC4 encrypt/decrypt\n");

        String rc4_msg = "Cъешь ещё этих мягких французских булок, да выпей чаю";

        byte[] msg_bytes = rc4_msg.getBytes();

        System.out.println("Message: " + rc4_msg);

        KeyGenerator rc4KeyGenerator = KeyGenerator.getInstance("RC4");
        SecretKey rc4_key = rc4KeyGenerator.generateKey(); // Генерирование ключа

        System.out.println("RC4 Key: " + rc4_key.toString().toCharArray());

        // Создание экземпляра Cipher и его инициализация в режиме шифрования.
        Cipher cipher = Cipher.getInstance("RC4");  // Преобразование алгоритма
        cipher.init(Cipher.ENCRYPT_MODE, rc4_key);
        byte[] cipherBytes = cipher.doFinal(msg_bytes);
        System.out.print("Encrypted Data (char format): ");

        for (int i = 0; i < cipherBytes.length ; i ++){
            System.out.print((char) cipherBytes[i]);
        }
        System.out.print("\nEncrypted Data (byte format): " + cipherBytes);

        // Повторная инициализация шифра в режим дешифрования.
        cipher.init(Cipher.DECRYPT_MODE,rc4_key, cipher.getParameters());
        byte[] msg_Decrypted = cipher.doFinal(cipherBytes);

        System.out.println("\nDecrypted Data : "+new String(msg_Decrypted));
        String rc4_result = new String(msg_Decrypted);







    }
}