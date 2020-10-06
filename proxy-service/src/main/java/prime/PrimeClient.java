package prime;

import com.romulojales.protobuf.PrimeRequest;
import com.romulojales.protobuf.PrimeResponse;
import com.romulojales.protobuf.PrimeServerGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class PrimeClient {

    final private PrimeServerGrpc.PrimeServerBlockingStub primeServerStub;

    public PrimeClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9999)
                .usePlaintext()
                .build();
        this.primeServerStub = PrimeServerGrpc.newBlockingStub(channel);
    }

    public Iterator<PrimeResponse> getPrimes(int upTo) {
        PrimeRequest request = PrimeRequest.newBuilder().setArgument(upTo).build();
        return primeServerStub.getPrimes(request);
    }
}
