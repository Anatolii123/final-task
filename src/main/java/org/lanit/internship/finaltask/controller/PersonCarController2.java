package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.service.PersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/2")
public class PersonCarController2 {

    @Autowired
    private PersonCarService personCarService;

    @PostMapping(value = "/person")
    public Person savePerson(@RequestBody Person person) {
        return personCarService.savePerson(person);
    }

    @PostMapping(value = "/car")
    public Car saveCar(@RequestBody Car car) {
        return personCarService.saveCar(car);
    }

    @GetMapping(value = "/newPersonId")
    public Long getNewPersonId() {
        Long id = personCarService.getNewPersonId();
        return id;
    }

    @GetMapping(value = "/newCarId")
    public Long getNewCarId() {
        Long id = personCarService.getNewCarId();
        return id;
    }

}
