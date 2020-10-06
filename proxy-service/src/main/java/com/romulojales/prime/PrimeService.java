package com.romulojales.prime;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


@Service
public class PrimeService {

    private PrimeClient primeClient;

    public PrimeService(PrimeClient primeClient) {
        this.primeClient = primeClient;
    }

    public void getPrimes(OutputStream outputStream, int upTo) throws IOException {

        primeClient.getPrimes(upTo).forEachRemaining(primeNumber ->
        {
            int number = primeNumber.getPrimeNumber();
            String data;
            if(number != 2) {
                data = "," + number;
            } else {
                data = "2";
            }
            try {
                outputStream.write(data.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            } catch(IOException e) { }
        });

        outputStream.write(".".getBytes());
        outputStream.flush();
    }
}
