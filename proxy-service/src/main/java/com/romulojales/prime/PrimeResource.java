package com.romulojales.prime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import javax.ws.rs.Produces;

@RestController
public class PrimeResource {

    final private PrimeService primeService;

    public PrimeResource(PrimeService primeService) {
        this.primeService = primeService;
    }

    @ResponseBody
    @Produces("text/plain")
    @GetMapping("/primes/{number}")
    public void getPrimes(@PathVariable int number, HttpServletResponse response) throws IOException {
        primeService.getPrimes(response.getOutputStream(), number);
    }
}
