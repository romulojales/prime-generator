# prime-generator

A very simple way of generate a list of prime numbers up to a given number. 

# Discussion

I've decided to keep all the code simple, but tested. 
So I can iterate it by the time.

First I have a dedicated component to create the prime numbers, it contains the
a grpc server to provide the number to client, running in the port 9999. 

Then a proxy which connects with the prime number server and expose a rest interface to the web running in the 
port 8080.

Both, in localhost.

All complexity and validation regarding creating and dealing with bad integers is on prime-number. 

The prime number generator is not **optimized**, and can cause problem of OoM or long wait time.
Because I decided to keep it simple and delivery the value on the first iteration. Then, improve performance.

Another opportunity of improvement is improve the resilience between the proxy and the prime-server. In a
event of disconnection of the part, we need to restart both servers manually.


## The web interface is:

`/primes/<a given number>`

Then the web servicer will return:

`2,... to the last prime up to the number.`

Examples:

`/primes/17` will return `2,3,5,7,11,13,17.`

`/primes/21` returning  `2,3,5,7,11,13,17,19.`

`/primes/3` getting `2,3.`

## Errors

`/primes/a`

Will return 400, with a message saying that it failed to convert a to integer 

`/primes`

will return 404

`/primes/1`

will return 400, and the message saying that 1 is not valid. That is the same message for any number lower than 2.

Any 500 or other error, can be related to connection between the proxy and the server, or another service 
running on the same port. I'll be more than happy on helping debug any strange case. 


## Requirements

* Java version 11
* Maven 3.6

### before you run any command

`mvn clean install`

That is very important to make sure the java code of the protobuf is created. 

## running all unit tests

`mvn test`

## running the integration test

First start the servers (refers to the next section).

then 

`mvn -Prun-integration-tests clean verify` 

## Starting the servers

### First you need to start the prime-number-server

`cd prime-number-server`

`mvn  exec:java -Dexec.mainClass="com.romulojales.prime.PrimeServer" `

If you get this message, that means you are good:

```
[com.romulojales.prime.PrimeServer.main()] INFO com.romulojales.prime.PrimeServer - Starting the GRPC server
[com.romulojales.prime.PrimeServer.main()] INFO com.romulojales.prime.PrimeServer - The GRPC server has been started
```

### Then the proxy

open another terminal, go back to the root folder of the project, then 

`cd proxy-server`

`mvn spring-boot:run`

then you should see the spring's logo

## Folders

* integration-tests, contains the tests that requires both server running
* prime-number-server, the grpc server that generates the prime numbers
* protobuf, the grpc specification
* proxy-service, the spring boot component that exposes the rest endpoint and talks to the prime-number-server
