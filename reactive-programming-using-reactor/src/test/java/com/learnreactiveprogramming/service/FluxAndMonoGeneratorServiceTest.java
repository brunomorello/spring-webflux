package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

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
}