package com.carapi.Car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class CarApplication {
	@Component
	public static class RouteHandles {
		private final FluxCarService fluxCarService;

		public RouteHandles(FluxCarService fluxCarService) {
			this.fluxCarService = fluxCarService;
		}

		public Mono<ServerResponse> allCars(ServerRequest serverRequest) {
			return ServerResponse.ok()
					.body(fluxCarService.all(), CarModel.class)
					.doOnError(throwable -> new IllegalStateException("My godness NOOOOO!!"));
		}

		public Mono<ServerResponse> carById(ServerRequest serverRequest) {
			String carId = serverRequest.pathVariable("carId");
			return ServerResponse.ok()
					.body(fluxCarService.byId(carId), CarModel.class)
					.doOnError(throwable -> new IllegalStateException("oh boy... not againnn =(("));
		}

		public Mono<ServerResponse> events(ServerRequest serverRequest) {
			String carId = serverRequest.pathVariable("carId");
			return ServerResponse.ok()
					.contentType(MediaType.TEXT_EVENT_STREAM)
					.body(fluxCarService.streams(carId), CarEvents.class)
					.doOnError(throwable -> new IllegalStateException("I give up!! "));
		}
	}

	@Bean
	RouterFunction<?> routes(RouteHandles routeHandles) {
		return RouterFunctions.route(
				RequestPredicates.GET("/cars"), routeHandles::allCars)
				.andRoute(RequestPredicates.GET("/cars/{carId}"), routeHandles::carById)
				.andRoute(RequestPredicates.GET("/cars/{carId}/events"), routeHandles::events);
	}

	public static void main(String[] args) {
		SpringApplication.run(CarApplication.class, args);
	}
}