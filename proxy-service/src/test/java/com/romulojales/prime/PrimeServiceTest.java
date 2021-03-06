package com.romulojales.prime;

import com.romulojales.protobuf.PrimeResponse;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PrimeServiceTest {

    @Mock
    private PrimeClient primeClient;

    private PrimeService primeService;

    @BeforeEach
    void setUp() {
        initMocks(this);

        primeService = new PrimeService(primeClient);
    }

    @Test
    void getPrimes_should_return_the_list_of_primes_on_the_happy_path_and_write_it_on_the_outstream() throws IOException {

        PrimeResponse response1 = PrimeResponse.newBuilder().setPrimeNumber(2).build();
        PrimeResponse response2 = PrimeResponse.newBuilder().setPrimeNumber(3).build();
        PrimeResponse response3 = PrimeResponse.newBuilder().setPrimeNumber(5).build();

        List<PrimeResponse> list = List.of(response1, response2, response3);
        when(primeClient.getPrimes(eq(5))).thenReturn(list.listIterator());

        StringBuilder data = new StringBuilder();

        primeService.getPrimes(getOutputStream(data), 5);

        assertEquals("2,3,5.", data.toString());
    }

    @Test
    void getPrimes_should_return_the_proper_exception_when_the_prime_server_returns_an_exception() throws IOException {
        when(primeClient.getPrimes(eq(-1))).thenThrow(StatusRuntimeException.class);

        assertThrows(InvalidArgumentException.class, () -> {primeService.getPrimes(getOutputStream(new StringBuilder()), -1);});
    }

    private OutputStream getOutputStream(StringBuilder data) {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // make the lint happy
            }

            @Override
            public void write(byte[] b) throws IOException {
                data.append(new String(b));
            }
        };
    }
}
