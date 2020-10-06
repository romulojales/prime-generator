package com.romulojales.prime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class PrimeResource {

    private PrimeService primeService;

    @Inject
    public PrimeResource(PrimeService primeService) {
        this.primeService = primeService;
    }

    @ResponseBody
    @GetMapping("/primes/{number}")
    public ResponseEntity getPrimes(@PathVariable int upTo) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        primeService.getPrimes(outputStream, upTo);
        return new ResponseEntity(outputStream, HttpStatus.OK);
    }
}
