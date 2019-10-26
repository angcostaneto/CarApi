package com.carapi.Car;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CarRepository extends ReactiveMongoRepository<CarModel, String> { }
