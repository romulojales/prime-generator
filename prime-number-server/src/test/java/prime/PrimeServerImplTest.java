package prime;

import com.romulojales.protobuf.PrimeRequest;
import com.romulojales.protobuf.PrimeResponse;
import com.romulojales.protobuf.PrimeServerGrpc;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

class PrimeServerImplTest {

    private CountDownLatch latch ;
    private PrimeServerImpl server;

    @BeforeEach
    void setUp() {
        latch = new CountDownLatch(1);
        server = new PrimeServerImpl();
    }

    @Test
    void getPrimes_should_return_the_proper_list_of_primes() throws InterruptedException {
        final List<Integer> result = new ArrayList<>();
        PrimeRequest request = PrimeRequest.newBuilder().setArgument(17).build();
        StreamObserver<PrimeResponse> responseObserver = createObserver(result);

        server.getPrimes(request, responseObserver);

        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17), result);
    }

    @Test
    void getPrimes_should_return_an_empty_list_when_the_number_is_not_valid() {
        final List<Integer> result = new ArrayList<>();
        PrimeRequest request = PrimeRequest.newBuilder().setArgument(-1).build();
        StreamObserver<PrimeResponse> responseObserver = createObserver(result);
        server.getPrimes(request, responseObserver);
        assertEquals(List.of(), result);
    }

    @Test
    void getPrimes_should_return_an_empty_list_when_the_number_is_null() {
        final List<Integer> result = new ArrayList<>();

        PrimeRequest request = PrimeRequest.newBuilder().build();
        StreamObserver<PrimeResponse> responseObserver = createObserver(result);

        server.getPrimes(request, responseObserver);

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
                // making lint happy
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };
    }
}

