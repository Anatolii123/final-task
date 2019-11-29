package org.lanit.internship.finaltask.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PersonWithCars extends Person {
    private List<Car> cars;

    public List<Car> getCars() {
        return this.cars;
    }

    public void setCars(List<Car> cars, Long ownerId) {
        this.cars = cars.stream()
                .filter(car -> car.getOwnerId() == ownerId).collect(Collectors.toList());
    }
}
