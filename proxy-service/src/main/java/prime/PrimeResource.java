package prime;

@RestController
public class PrimeResource {

    private PrimeService primeService;
    public PrimeResource(PrimeService primeService) {
        this.primeService = primeService;
    }

    @GetMapping("/primes/<number>")
}
