package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class FluxAndMonoGeneratorService {
    public Flux<String> namesFlux() {
        return Flux.fromIterable(Arrays.asList("Luna", "Izzy", "Keira"));
    }

    public Mono<String> namesMono() {
        return Mono.just("Mel");
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> System.out.println(String.format("[FLUX] Cat name %s", name)));

        fluxAndMonoGeneratorService.namesMono()
                .subscribe(name -> System.out.println(String.format("[MONO] Cat name %s", name)));
    }
}
