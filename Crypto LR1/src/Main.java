import java.io.IOException;
import java.io.BufferedReader;
import java.io.StringReader;

public class Main {
    public static void main (String[] args) throws IOException {

        StringReader reader1 = new StringReader("This text cipher text for lab");
        String encrypt = encrypt(reader1,5);

        System.out.println(encrypt);
        System.out.println("Encrypt complete!");


        for(int i = 0 ; i >= -25; i--)
        {
            StringReader reader = new StringReader(encrypt);
            System.out.println(decrypt(reader, i));  //This text cipher text for lab
        }
        System.out.println("Decrypt complete!");
    }
    public static String decrypt(StringReader reader, int key) throws IOException {
        if (reader==null) return "";
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(reader);

        String string = bufferedReader.readLine();


        for (char c: string.toCharArray()
        ) {
            sb.append((char)(c+key));
        }
        sb.append(" ");
        sb.append(key);

        return sb.toString();
    }

    public static String encrypt (StringReader reader, int key) throws IOException{
        if (reader==null) return "";
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(reader);

        String string = bufferedReader.readLine();


        for (char c: string.toCharArray()
        ) {
            sb.append((char)(c+key));
        }

        return sb.toString();
    }
}