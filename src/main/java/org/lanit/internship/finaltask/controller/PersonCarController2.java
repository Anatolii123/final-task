package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.service.PersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/2")
public class PersonCarController2 {

    @Autowired
    private PersonCarService personCarService;

    @PostMapping(value = "/person")
    public Person savePerson(@RequestBody Person person) throws NoSuchFieldException, IllegalAccessException, ParseException {
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

    @ExceptionHandler({com.fasterxml.jackson.databind.exc.InvalidFormatException.class, com.fasterxml.jackson.core.JsonParseException.class})
    public void handleException() {
        throw new BadRequestException();
    }

}
