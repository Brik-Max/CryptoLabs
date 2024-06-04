import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        double entropy = 0;

        ArrayList<Character> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("C://Users//MrBrick//Desktop//text.txt"));
        String line = reader.readLine();
        int count_char = line.length();

        for (char element : line.toCharArray()) {
            if (list.contains(element)){
                continue;
            }
            list.add(element);
            int count = line.length() - line.replace(String.valueOf(element), "").length();
            System.out.println(element + " " + count);

            entropy += ((double)count/(double)count_char) * Log(2,(double)count/(double)count_char);


            count = 0;
            System.out.print("");
        }
        entropy = entropy * (-1);
        System.out.println( "Alphabet: " + list.size() + " element(s)");
        System.out.println( "Entropy of file: " + entropy);

        reader.close();

    }

    public static double Log(int base, double x){
        return Math.log(x) /
                Math.log(base);

    }

}