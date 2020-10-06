package com.romulojales.prime;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class PrimeServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrimeServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        LOGGER.info("Starting the GRPC server");
        Server server = ServerBuilder.forPort(9999)
                .addService(new PrimeServerImpl())
                .build();

        server.start();
        LOGGER.info("The GRPC server has been started");
        server.awaitTermination();
        LOGGER.info("The GRPC server has been terminated");
    }
}
