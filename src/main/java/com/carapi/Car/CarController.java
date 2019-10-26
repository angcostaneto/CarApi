package com.carapi.Car;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CarController {
    private final FluxCarService fluxCarService;

    CarController(FluxCarService fluxCarService) {
        this.fluxCarService = fluxCarService;
    }

    @GetMapping("/cars")
    public Flux<CarModel> all() {
        return fluxCarService.all();
    }

    @GetMapping("/cars/{carId}")
    public Mono<CarModel> byId(@PathVariable String carId) {
        return fluxCarService.byId(carId);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/cars/{carId}/events")
    public Flux<CarEvents> eventsOfStreams(@PathVariable String carId) {
        return fluxCarService.streams(carId);
    }

}
