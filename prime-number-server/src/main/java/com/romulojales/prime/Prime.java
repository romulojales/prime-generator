package com.romulojales.prime;

import java.util.stream.IntStream;

public class Prime {

    /**
     * Generates the list of primes up to the given number.
     * If the given number is less than 2, then an empty stream will be generated.
     * */
    static IntStream generatePrimes(final int number) {
        IntStream nums = IntStream.rangeClosed(2, number);
        return nums.filter(Prime::isPrime);
    }

    static boolean isPrime(int number) {
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
