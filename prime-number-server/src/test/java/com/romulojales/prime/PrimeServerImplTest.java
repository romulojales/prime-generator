package com.romulojales.prime;

import com.romulojales.protobuf.PrimeRequest;
import com.romulojales.protobuf.PrimeResponse;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PrimeServerImplTest {

    private CountDownLatch latch;
    private PrimeServerImpl server;

    @BeforeEach
    void setUp() {
        latch = new CountDownLatch(1);
        server = new PrimeServerImpl();
    }

    @Test
    void getPrimes_should_return_the_proper_list_of_primes() {
        final List<Integer> result = new ArrayList<>();
        PrimeRequest request = PrimeRequest.newBuilder().setArgument(17).build();
        StreamObserver<PrimeResponse> responseObserver = createObserver(result);

        server.getPrimes(request, responseObserver);

        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17), result);
    }

    @Test
    void getPrimes_should_fail_when_the_number_is_not_valid() {
        final List<Integer> result = new ArrayList<>();
        PrimeRequest request = PrimeRequest.newBuilder().setArgument(-1).build();
        StreamObserver<PrimeResponse> responseObserver = createObserver(result);

        RuntimeException encupsulated = assertThrows(RuntimeException.class, () -> {server.getPrimes(request, responseObserver);});

        assertEquals(StatusRuntimeException.class, encupsulated.getCause().getClass());
        assertEquals("INVALID_ARGUMENT: This RPC does not accept number lower than 2.\nReceived number: -1",
                encupsulated.getCause().getMessage());

        assertEquals(List.of(), result);
    }

    @Test
    void getPrimes_should_return_an_empty_list_when_the_number_is_not_provided() {
        final List<Integer> result = new ArrayList<>();

        PrimeRequest request = PrimeRequest.newBuilder().build();
        StreamObserver<PrimeResponse> responseObserver = createObserver(result);

        RuntimeException encupsulated = assertThrows(RuntimeException.class, () -> {server.getPrimes(request, responseObserver);});

        assertEquals(StatusRuntimeException.class, encupsulated.getCause().getClass());
        assertEquals("INVALID_ARGUMENT: This RPC does not accept number lower than 2.\nReceived number: 0",
                encupsulated.getCause().getMessage());


        assertEquals(List.of(), result);
    }

    private StreamObserver<PrimeResponse> createObserver(List<Integer> values) {
        return new StreamObserver<>() {
            @Override
            public void onNext(PrimeResponse response) {
                values.add(response.getPrimeNumber());
            }

            @Override
            public void onError(Throwable throwable) {
                throw new RuntimeException(throwable);
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };
    }
}

