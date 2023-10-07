package com.learnreactiveprogramming.service;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {
    public Flux<String> namesFlux() {
        return Flux.fromIterable(Arrays.asList("Luna", "Izzy", "Keira"));
    }

    public Mono<String> namesMono() {
        return Mono.just("Mel");
    }

    public Flux<String> namesMapFlux() {
        return Flux.fromIterable(Arrays.asList("luna", "izzy", "keira"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFilterAndFlatMapAsync(final int length) {
        return Flux.fromIterable(Arrays.asList("luna", "izzy", "keira", "mel"))
                .filter(name -> name.length() > length)
                .map(String::toUpperCase)
                // use .concatMap if order needs to be preserved
                .flatMap(name -> splitStrFlux(name))
                .log();
    }

    public Mono<List<String>> namesMonoFlatMap(final int length) {
        return Mono.just("Keira")
                .map(String::toUpperCase)
                .filter(str -> str.length() > length)
                .flatMap(this::splitStrMono)
                .log();
    }

    public Flux<String> namesMonoFlatMapMany(final int length) {
        return Mono.just("Keira")
                .map(String::toUpperCase)
                .filter(str -> str.length() > length)
                .flatMapMany(this::splitStr)
                .log();
    }

    private Flux<String> splitStr(final String str) {
        var charArr = str.split("");
        return Flux.fromArray(charArr);
    }

    private Mono<List<String>> splitStrMono(final String str) {
        var charArr = str.split("");
        return Mono.just(List.of(charArr));
    }

    private Flux<String> splitStrFlux(final String str) {
        var strSplited = str.split("");
        var delayRandom = new Random(1000);
        return Flux.fromArray(strSplited)
                .delayElements(Duration.of(delayRandom.nextInt(), ChronoUnit.MILLIS));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> System.out.println(String.format("[FLUX] Cat name %s", name)));

        fluxAndMonoGeneratorService.namesMono()
                .subscribe(name -> System.out.println(String.format("[MONO] Cat name %s", name)));
    }
}
