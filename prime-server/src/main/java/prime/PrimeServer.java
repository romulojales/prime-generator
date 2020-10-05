package prime;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class PrimeServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(9999)
                .addService(new PrimeServerImpl())
                .build();

        server.start();
        server.awaitTermination();
    }
}
