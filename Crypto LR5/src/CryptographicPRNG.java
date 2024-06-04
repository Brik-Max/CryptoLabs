import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptographicPRNG {

    private MessageDigest digest;
    private byte[] seed;
    private int counter;

    public CryptographicPRNG(String algorithm, byte[] seed) throws NoSuchAlgorithmException {
        this.digest = MessageDigest.getInstance(algorithm);
        this.seed = seed;
        this.counter = 0;
    }

    public int nextInt() {
        // Обновление состояния хэш-функции за счет объединения с текущим счетчиком
        digest.update(seed);
        digest.update(intToBytes(counter++));

        // Вычисление хэша в виде массива байт
        byte[] hash = digest.digest();

        // Преобразование хэша в целое число, используя 4 байта из середины (для большей случайности)
        return bytesToInt(hash, 4, 8);
    }

    private byte[] intToBytes(int value) {
        return new byte[] {
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
    }

    private int bytesToInt(byte[] bytes, int start, int end) {
        int value = 0;
        for (int i = start; i < end; i++) {
            value = (value << 8) | (bytes[i] & 0xFF);
        }
        return value;
    }

    public static void main(String[] args) {
        try {

            byte[] seed = new byte[16];
            long time = System.currentTimeMillis();
            int pid = (int) ProcessHandle.current().pid();
            int tid = (int) Thread.currentThread().getId();

            // Объединение источников энтропии в массив байт
            seed[0] = (byte) (time >>> 56);
            seed[1] = (byte) (time >>> 48);
            seed[2] = (byte) (time >>> 40);
            seed[3] = (byte) (time >>> 32);
            seed[4] = (byte) (time >>> 24);
            seed[5] = (byte) (time >>> 16);
            seed[6] = (byte) (time >>> 8);
            seed[7] = (byte) time;
            seed[8] = (byte) (pid >>> 24);
            seed[9] = (byte) (pid >>> 16);
            seed[10] = (byte) (pid >>> 8);
            seed[11] = (byte) pid;
            seed[12] = (byte) (tid >>> 24);
            seed[13] = (byte) (tid >>> 16);
            seed[14] = (byte) (tid >>> 8);
            seed[15] = (byte) tid;

            // Создание генератора PRNG с помощью алгоритма SHA-256 и указанного начального значения seed
            CryptographicPRNG prng = new CryptographicPRNG("SHA-256", seed);

            // Генерация и вывод 10 псевдослучайных чисел в диапазоне [0, Integer.MAX_VALUE]
            for (int i = 0; i < 10; i++) {
                System.out.println(prng.nextInt());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
