package org.lanit.internship.finaltask.service;

import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.model.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

    @Override
    public PersonWithCars getPersonWithCars(Long personid, PersonRepo personRepo, CarRepo carRepo) throws ParseException {
        PersonWithCars personWithCars = new PersonWithCars();
        Optional<Person> personById = personRepo.findById(personid);
        personWithCars.setId(personById.get().getId());
        personWithCars.setName(personById.get().getName());
        personWithCars.setBirthDate(personById.get().getBirthDate());
        List<Optional<Car>> carsByOwnerId = carRepo.findByOwnerId(personid);
        personWithCars.setCars(carsByOwnerId);
        return personWithCars;
    }

    @Override
    public Statistics getStatisticsObject(PersonRepo personRepo, CarRepo carRepo) {
        Statistics statistics = new Statistics();
        statistics.setPersoncount(personRepo.count());
        statistics.setCarcount(carRepo.count());
        HashSet<String> vendors = new HashSet<String>();
        for (Car car : carRepo.findAll()) {
            vendors.add(car.getVendorModel());
        }
        statistics.setUniquevendorcount((long) vendors.size());
        return statistics;
    }
}
