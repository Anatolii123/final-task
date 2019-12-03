package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.service.PersonCarService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/2")
public class PersonCarController2 {

    @Autowired
    private PersonCarService2 personCarService2;

    @PostMapping(value = "/person")
    public Person savePerson(@RequestBody Person person) {
        return personCarService2.save(person);
    }

    @PostMapping(value = "/car")
    public Car saveCar2(@RequestBody Car car) {
        return personCarService2.save(car);
    }

}
