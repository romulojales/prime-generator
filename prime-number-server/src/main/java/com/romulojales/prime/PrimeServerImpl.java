package com.romulojales.prime;

import com.romulojales.protobuf.PrimeRequest;
import com.romulojales.protobuf.PrimeResponse;
import com.romulojales.protobuf.PrimeServerGrpc;
import io.grpc.stub.StreamObserver;

import java.util.stream.IntStream;

public class PrimeServerImpl extends PrimeServerGrpc.PrimeServerImplBase {

    @Override
    public void getPrimes(PrimeRequest request, StreamObserver<PrimeResponse> responseObserver) {
        final int number = request.getArgument();
        IntStream primes = Prime.generatePrimes(number);

        primes.forEach(prime -> {
            PrimeResponse response = PrimeResponse.newBuilder().setPrimeNumber(prime).build();
            responseObserver.onNext(response);
        });

        responseObserver.onCompleted();
    }

}
