package org.lanit.internship.finaltask.service;

import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.exceptions.NotFoundException;
import org.lanit.internship.finaltask.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

@Service
public class PersonCarServiceImpl implements PersonCarService {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private CarRepo carRepo;

    @Override
    public String getCarVendor(Car car) throws BadRequestException {
        String vendor = car.getModel().substring(0, car.getModel().indexOf("-"));
        return vendor;
    }

    @Override
    public String getCarModel(Car car) throws BadRequestException {
        String model = car.getModel().substring(car.getModel().indexOf("-") + 1);
        return model;
    }

    @Override
    public Person personIsValid(Person person) throws BadRequestException{
        if (person == null || personRepo.findById(person.getId()).isPresent()) {
            throw new BadRequestException();
        }
        return person;
    }

    @Override
    public Car carIsValid(Car car) throws BadRequestException {
        if (car == null || car.getHorsepower() <= 0 || carRepo.findById(car.getId()).isPresent() ||
                (new java.util.Date().getYear() - personRepo.findById(car.getOwnerId())
                        .orElseThrow(BadRequestException::new).getBirthdate().getYear() < 18)) {
            throw new BadRequestException();
        }
        return car;
    }

    @Override
    public PersonWithCars getPersonWithCars(Long personid) throws ParseException {
        PersonWithCars personWithCars = new PersonWithCars();
        if (personid == null) {
            throw new BadRequestException();
        }
        if (!personRepo.findById(personid).isPresent()) {
            throw new NotFoundException();
        }

        Optional<Person> personById = personRepo.findById(personid);
        personWithCars.setId(personById.get().getId());
        personWithCars.setName(personById.get().getName());
        personWithCars.setBirthdate(personById.get().getBirthdate());
        List<Optional<Car>> carsByOwnerId = carRepo.findByOwnerId(personid);
        personWithCars.setCars(carsByOwnerId);
        return personWithCars;
    }

    @Override
    public Long getNewPersonId() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < personRepo.findAll().size(); i++) {
            ids.add(Long.valueOf(personRepo.findAll().get(i).getId()));
        }

        return Collections.max(ids) + 1;
    }

    @Override
    public Long getNewCarId() {
        List<Long> ids = new ArrayList<Long>();
        for (int i = 0; i < carRepo.findAll().size(); i++) {
            ids.add(carRepo.findAll().get(i).getId());
        }

        return Collections.max(ids) + 1;
    }

    @Override
    public Statistics getStatisticsObject() {
        Statistics statistics = new Statistics();
        statistics.setPersoncount(personRepo.count());
        statistics.setCarcount(carRepo.count());
        HashSet<String> vendors = new HashSet<String>();
        for (Car car : carRepo.findAll()) {
            vendors.add(getCarVendor(car).toLowerCase());
        }
        statistics.setUniquevendorcount((long) vendors.size());
        return statistics;
    }

    @Override
    public void save(Person person) throws NoSuchFieldException, IllegalAccessException, ParseException {
        personRepo.save(personIsValid(person));
    }

    @Override
    public Person savePerson(Person person) throws NoSuchFieldException, IllegalAccessException, ParseException {
        return personRepo.save(personIsValid(person));
    }

    @Override
    public void save(Car car) {
        carRepo.save(carIsValid(car));
    }

    @Override
    public Car saveCar(Car car) {
        return carRepo.save(carIsValid(car));
    }

    @Override
    public List<Person> findAllPersons() {
        return personRepo.findAll(Sort.by("id"));
    }

    @Override
    public List<Car> findAllCars() {
        return carRepo.findAll(Sort.by("id"));
    }

    @Override
    public void deleteAll() {
        carRepo.deleteAll();
        personRepo.deleteAll();
    }
}
