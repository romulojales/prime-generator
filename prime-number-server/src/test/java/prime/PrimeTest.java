package prime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class PrimeTest {

    @Test
    void isPrime_returns_true_for_two() {
        assertTrue(Prime.isPrime(2));
    }

    @Test
    void isPrime_returns_false_when_the_number_is_less_than_2() {
        assertFalse(Prime.isPrime(0));
        assertFalse(Prime.isPrime(1));
        assertFalse(Prime.isPrime(-1));
    }

    @Test
    void isPrime_returns_false_when_the_number_is_even_and_greater_than_2() {
        assertFalse(Prime.isPrime(4));
        assertFalse(Prime.isPrime(10));
        assertFalse(Prime.isPrime(1000));
    }

    @Test
    void isPrime_returns_false_when_the_number_is_odd_but_not_prime() {
        assertFalse(Prime.isPrime(15));
        assertFalse(Prime.isPrime(21));
        assertFalse(Prime.isPrime(87));
    }

    @Test
    void isPrime_returns_true_when_the_number_is_know_to_be_a_prime() {
        assertTrue(Prime.isPrime(5));
        assertTrue(Prime.isPrime(17));
        assertTrue(Prime.isPrime(23));
        assertTrue(Prime.isPrime(89));
        assertTrue(Prime.isPrime(101));
    }

    @Test
    void genPrimes_returns_a_stream_with_one_number_when_the_argument_is_2() {
        assertArrayEquals(new int[]{2}, Prime.generatePrimes(2).toArray());
    }

    @Test
    void genPrimes_returns_the_expected_values_when_the_argument_is_17() {
        assertArrayEquals(new int[]{2, 3, 5, 7, 11, 13, 17}, Prime.generatePrimes(17).toArray());
    }

    @Test
    void genPrimes_returns_empty_stream_if_argument_is_less_than_2() {
        assertArrayEquals(new int[]{}, Prime.generatePrimes(1).toArray());
        assertArrayEquals(new int[]{}, Prime.generatePrimes(0).toArray());
        assertArrayEquals(new int[]{}, Prime.generatePrimes(-1).toArray());
        assertArrayEquals(new int[]{}, Prime.generatePrimes(-17).toArray());
    }
}
