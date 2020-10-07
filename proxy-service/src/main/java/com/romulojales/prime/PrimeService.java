package com.romulojales.prime;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class PrimeService {

    final private PrimeClient primeClient;

    public PrimeService(PrimeClient primeClient) {
        this.primeClient = primeClient;
    }

    public void getPrimes(OutputStream outputStream, int upTo) throws IOException, InvalidArgumentException {
        try{
            primeClient.getPrimes(upTo).forEachRemaining(primeNumber ->
            {
                int number = primeNumber.getPrimeNumber();
                String data;
                if (number != 2) {
                    data = ","+number;
                }else {
                    data = "2";
                }
                try{
                    outputStream.write(data.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                }catch(IOException e){
                    log.error("Oh no! Something when really bad here.");
                    e.printStackTrace();
                }
            });

            outputStream.write(".".getBytes());
            outputStream.flush();
        }catch(StatusRuntimeException e){
            log.info("invalid argument received: " + upTo);
            throw new InvalidArgumentException(upTo);
        }
    }
}
