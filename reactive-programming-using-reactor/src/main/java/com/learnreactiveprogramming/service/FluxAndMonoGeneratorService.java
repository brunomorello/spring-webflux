package com.learnreactiveprogramming.service;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

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

    public Flux<String> namesFilterAndFlatMapAsyncWithTransform(final int length) {

        Function<Flux<String>, Flux<String>> filterAndMapFunc = str -> str.filter(name -> name.length() > length)
                .map(String::toUpperCase)
                .flatMap(this::splitStrFlux);

        return Flux.fromIterable(Arrays.asList("luna", "izzy", "keira", "mel"))
                .transform(filterAndMapFunc)
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> namesFilterAndFlatMapAsyncWithTransformEmpty(final int length) {

        Function<Flux<String>, Flux<String>> filterAndMapFunc = str -> str.filter(name -> name.length() > length)
                .map(String::toUpperCase)
                .flatMap(this::splitStrFlux);

        var defaultEmpty = Flux.just("default").transform(filterAndMapFunc);

        return Flux.fromIterable(Arrays.asList("luna", "izzy", "keira", "mel"))
                .transform(filterAndMapFunc)
                .switchIfEmpty(defaultEmpty)
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

    public Flux<String> exploreConcat() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> exploreContactWith() {
        var abcFlux = Flux.just("A", "B", "C");
        return abcFlux.concatWith(Flux.just("D", "E", "F"));
    }

    public Flux<String> exploreConcatWithMono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.concatWith(bMono);
    }

    public Flux<String> exploreMerge() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(150));
        return Flux.merge(abcFlux, defFlux).log();
    }

    public Flux<String> exploreMergeWith() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(150));
        return abcFlux.mergeWith(defFlux).log();
    }

    public Flux<String> exploreMergeWithMono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.mergeWith(bMono).log();
    }

    public Flux<String> exploreMergeSequenial() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(150));
        return Flux.mergeSequential(abcFlux, defFlux).log();
    }

    public Flux<String> exploreZip() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(150));

        return Flux.zip(abcFlux, defFlux, (first, second) -> first+second).log();
    }

    public Flux<String> exploreZipTupple() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(150));
        var flux123 = Flux.just("1", "2", "3");
        var flux456 = Flux.just("4", "5", "6");

        return Flux.zip(abcFlux, defFlux, flux123, flux456)
                .map(t4 -> t4.getT1()+t4.getT2()+t4.getT3()+t4.getT4())
                .log();
    }

    public Flux<String> exploreZipWith() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(150));
        return abcFlux.zipWith(defFlux, (first, second) -> first+second).log();
    }

    public Mono<String> exploreZipWithMono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.zipWith(bMono)
                .map(t2 -> t2.getT1()+ t2.getT2()).log();
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
