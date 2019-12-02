package org.lanit.internship.finaltask.service;

import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.model.PersonRepo;

public interface PersonCarService {
    boolean carIsValid(Car car, PersonRepo personRepo);
    boolean personIsValid(Person person);
}
