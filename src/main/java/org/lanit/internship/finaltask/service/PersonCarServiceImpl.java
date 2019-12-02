package org.lanit.internship.finaltask.service;

import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.model.PersonRepo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonCarServiceImpl implements PersonCarService {

    @Override
    public Car carIsValid(Car car, PersonRepo personRepo) throws BadRequestException {
        if (!(car.getId() instanceof Long) || car.getId() == null ||
                !(car.getModel() instanceof String) || car.getModel() == null ||
                car.getVendorModel().equals("") || car.getModelModel().equals("") ||
                !(car.getHorsepower() instanceof Integer) || car.getHorsepower() == null ||
                !(car.getOwnerId() instanceof Long) || car.getOwnerId() == null ||
                car.getHorsepower() <= 0 || !personRepo.findById(car.getOwnerId()).isPresent() ||
                (new java.util.Date().getYear() - personRepo.findById(car.getOwnerId())
                        .orElseThrow(BadRequestException::new).getBirthDate().getYear() < 18)) {
            throw new BadRequestException();
        }
        return car;
    }

    @Override
    public Person personIsValid(Person person) throws BadRequestException {
        if (!(person.getId() instanceof Long) || person.getId() == null ||
                !(person.getName() instanceof String) || person.getName() == null ||
                !(person.getBirthDate() instanceof Date) || person.getBirthDate() == null ||
                !person.getBirthDate().before(new Date())) {
            throw new BadRequestException();
        }
        return person;
    }

}
