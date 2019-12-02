package org.lanit.internship.finaltask.service;

import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.model.*;

import java.text.ParseException;

public interface PersonCarService {
    Car carIsValid(Car car, PersonRepo personRepo) throws BadRequestException;
    Person personIsValid(Person person) throws BadRequestException;
    PersonWithCars getPersonWithCars(Long personid, PersonRepo personRepo, CarRepo carRepo) throws ParseException;
    Statistics getStatisticsObject(PersonRepo personRepo, CarRepo carRepo);
}
