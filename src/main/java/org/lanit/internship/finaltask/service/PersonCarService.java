package org.lanit.internship.finaltask.service;

import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.ParseException;
import java.util.List;

public interface PersonCarService {
    String getCarVendor(Car car) throws BadRequestException;
    String getCarModel(Car car) throws  BadRequestException;
    Car carIsValid(Car car) throws BadRequestException, ParseException;
    Person personIsValid(Person person) throws BadRequestException, NoSuchFieldException, IllegalAccessException, ParseException;
    PersonWithCars getPersonWithCars(Long personid) throws ParseException;
    Long getNewPersonId();
    Long getNewCarId();
    Statistics getStatisticsObject();
    void save(Person person) throws NoSuchFieldException, IllegalAccessException, ParseException;
    Person savePerson(Person person) throws NoSuchFieldException, IllegalAccessException, ParseException;
    void save(Car car) throws ParseException;
    Car saveCar(Car car) throws ParseException;
    List<Person> findAllPersons();
    List<Car> findAllCars();
    void deleteAll();
}
