import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Set<Integer> mineNumbers = new HashSet<>();

        while (mineNumbers.size() < 10) {
            mineNumbers.add(random.nextInt(81));
        }
        System.out.println("size of mineNumbers: " + mineNumbers.size());
        for (Integer mineNumber : mineNumbers) {
            System.out.println("mineNumber = " + mineNumber);
        }
    }
}