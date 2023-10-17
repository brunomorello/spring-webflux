package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    private FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
    @Test
    void when_flux_of_strings_validate_its_values_and_completion() {

        var namesFlux = fluxAndMonoGeneratorService.namesFlux();
        StepVerifier.create(namesFlux)
                .expectNext("Luna", "Izzy", "Keira")
                .verifyComplete();
    }

    @Test
    void namesMapFlux() {
        var namesUpper = fluxAndMonoGeneratorService.namesMapFlux();
        StepVerifier.create(namesUpper)
                .expectNext("LUNA", "IZZY", "KEIRA")
                .verifyComplete();
    }

    @Test
    void namesFilterAndFlatMapAsync() {
        var res = fluxAndMonoGeneratorService.namesFilterAndFlatMapAsync(3);
        StepVerifier.create(res)
//                .expectNext("L", "U", "N", "A", "I", "Z", "Z", "Y", "K", "E", "I", "R", "A")
                .expectNextCount(13)
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMap() {
        var res = fluxAndMonoGeneratorService.namesMonoFlatMap(3);
        StepVerifier.create(res)
                .expectNext(List.of("K", "E", "I", "R", "A"))
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMapMany() {
        var res = fluxAndMonoGeneratorService.namesMonoFlatMapMany(3);
        StepVerifier.create(res)
                .expectNext("K", "E", "I", "R", "A")
                .verifyComplete();
    }

    @Test
    void namesFilterAndFlatMapAsyncWithTransform() {
        var res = fluxAndMonoGeneratorService.namesFilterAndFlatMapAsyncWithTransform(3);
        StepVerifier.create(res)
                .expectNextCount(13)
                .verifyComplete();
    }

    @Test
    void namesFilterAndFlatMapAsyncWithTransformEmpty() {
        var res = fluxAndMonoGeneratorService.namesFilterAndFlatMapAsyncWithTransform(6);
        StepVerifier.create(res)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void namesFilterAndFlatMapAsyncWithTransformEmptySwitchIfEmpty() {
        var res = fluxAndMonoGeneratorService.namesFilterAndFlatMapAsyncWithTransformEmpty(6);
        StepVerifier.create(res)
                .expectNextCount(7)
                .verifyComplete();
    }

    @Test
    void testExploreConcat() {
        var res = fluxAndMonoGeneratorService.exploreConcat();
        StepVerifier.create(res)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }

    @Test
    void testExploreConcatWith() {
        var res = fluxAndMonoGeneratorService.exploreContactWith();
        StepVerifier.create(res)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }

    @Test
    void testExploreConcatWithMono() {
        var res = fluxAndMonoGeneratorService.exploreConcatWithMono();
        StepVerifier.create(res)
                .expectNext("A", "B")
                .verifyComplete();

    }

    @Test
    void testExploreMerge() {
        var res = fluxAndMonoGeneratorService.exploreMerge();
        StepVerifier.create(res)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();

    }

    @Test
    void testExploreMergeWith() {
        var res = fluxAndMonoGeneratorService.exploreMergeWith();
        StepVerifier.create(res)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();

    }

    @Test
    void testExploreMergeWithMono() {
        var res = fluxAndMonoGeneratorService.exploreMergeWithMono();
        StepVerifier.create(res)
                .expectNext("A", "B")
                .verifyComplete();

    }

    @Test
    void testExploreMergeSequential() {
        var res = fluxAndMonoGeneratorService.exploreMergeSequenial();
        StepVerifier.create(res)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }

    @Test
    void testExploreZip() {
        var res = fluxAndMonoGeneratorService.exploreZip();
        StepVerifier.create(res)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();

    }

    @Test
    void testExploreZipTupple() {
        var res = fluxAndMonoGeneratorService.exploreZipTupple();
        StepVerifier.create(res)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();

    }

    @Test
    void testExploreZipWith() {
        var res = fluxAndMonoGeneratorService.exploreZipWith();
        StepVerifier.create(res)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();

    }

    @Test
    void testExploreZipWithMono() {
        var res = fluxAndMonoGeneratorService.exploreZipWithMono();
        StepVerifier.create(res)
                .expectNext("AB")
                .verifyComplete();

    }
}