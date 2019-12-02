package org.lanit.internship.finaltask.service;

import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.model.PersonRepo;

public interface PersonCarService {
    Car carIsValid(Car car, PersonRepo personRepo) throws BadRequestException;
    Person personIsValid(Person person) throws BadRequestException;
}
