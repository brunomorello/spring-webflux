package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class ReactorSink {
    public static void main(String[] args) {
        // Replay
        Sinks.Many<Integer> replaySinks = Sinks.many().replay().all();

        replaySinks.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySinks.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> sinksFlux = replaySinks.asFlux();

        sinksFlux.subscribe(integer -> System.out.println("Replay Subscriber 1: " + integer));
        sinksFlux.subscribe(integer -> System.out.println("Replay Subscriber 2: " + integer));
        sinksFlux.subscribe(integer -> System.out.println("Replay Subscriber 3: " + integer));

        replaySinks.tryEmitNext(3);

        // Multicast
        Sinks.Many<Integer> multicastSinks = Sinks.many().multicast().onBackpressureBuffer();

        multicastSinks.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        multicastSinks.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> multicastSinksFlux = multicastSinks.asFlux();

        multicastSinksFlux.subscribe(integer -> System.out.println("Multicast Subscriber 1: " + integer));
        multicastSinksFlux.subscribe(integer -> System.out.println("Multicast Subscriber 2: " + integer));
        multicastSinksFlux.subscribe(integer -> System.out.println("Multicast Subscriber 3: " + integer));

        multicastSinks.tryEmitNext(3);

        // Unicast
        Sinks.Many<Integer> unicastSinks = Sinks.many().unicast().onBackpressureBuffer();

        unicastSinks.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        unicastSinks.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<Integer> unicastSinksFlux = unicastSinks.asFlux();

        unicastSinksFlux.subscribe(integer -> System.out.println("Unicast Subscriber 1: " + integer));
        unicastSinksFlux.subscribe(integer -> System.out.println("Unicast Subscriber 2: " + integer));
        unicastSinksFlux.subscribe(integer -> System.out.println("Unicast Subscriber 3: " + integer));

        unicastSinks.tryEmitNext(3);
    }
}
