package prime;

public class Prime {

    public static boolean isPrime(int number) {
        if (number == 2) {
            return true;
        }

        if (number < 2 || number % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(number); i = i + 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
