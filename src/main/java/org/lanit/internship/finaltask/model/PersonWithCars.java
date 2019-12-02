package org.lanit.internship.finaltask.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonWithCars extends Person {
    private List<Optional<Car>> cars;

    public List<Optional<Car>> getCars() {
        return this.cars;
    }

    public void setCars(List<Optional<Car>> cars) {
        this.cars = cars;
    }
}
