package org.lanit.internship.finaltask.service;

import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.CarRepo;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.model.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonCarService2Impl implements PersonCarService2 {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private PersonCarService personCarService;

    @Override
    public Person save(Person person) {
        return personRepo.save(personCarService.personIsValid(person));
    }

    @Override
    public Car save(Car car) {
        return carRepo.save(personCarService.carIsValid(car));
    }
}
