package com.romulojales.prime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


@RestController
public class PrimeResource {

    final private PrimeService primeService;

    public PrimeResource(PrimeService primeService) {
        this.primeService = primeService;
    }

    @GetMapping("/primes/{number}")
    public void getPrimes(@PathVariable int number, HttpServletResponse response) throws IOException {
        primeService.getPrimes(response.getOutputStream(), number);

    }
}
