package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/2")
public class PersonCarController2 {
    private List<Car> cars = new ArrayList<Car>(){{
        Car car = new Car();
        car.setHorsepower(100500);
        car.setId(1L);
        car.setModel("Lada-El");
        car.setOwnerId(1L);
        add(car);
    }};
    private List<Person> persons = new ArrayList<Person>(){{


    }};

    @GetMapping
    public List<Car> getCars(){
        return cars;
    }
}
