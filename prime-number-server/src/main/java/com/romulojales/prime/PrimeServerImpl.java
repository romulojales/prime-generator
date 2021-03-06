package com.romulojales.prime;

import com.romulojales.protobuf.PrimeRequest;
import com.romulojales.protobuf.PrimeResponse;
import com.romulojales.protobuf.PrimeServerGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class PrimeServerImpl extends PrimeServerGrpc.PrimeServerImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrimeServer.class);

    @Override
    public void getPrimes(PrimeRequest request, StreamObserver<PrimeResponse> responseObserver) {
        final int number = request.getArgument();
        IntStream primes = Prime.generatePrimes(number);

        if (number < 2) {
            LOGGER.info("Received the invalid number: " + number);
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("This RPC does not accept number lower than 2.")
                    .augmentDescription("Received number: " + number)
                    .asRuntimeException());
        }

        primes.forEach(prime -> {
            PrimeResponse response = PrimeResponse.newBuilder().setPrimeNumber(prime).build();
            responseObserver.onNext(response);
        });

        responseObserver.onCompleted();
    }

}
