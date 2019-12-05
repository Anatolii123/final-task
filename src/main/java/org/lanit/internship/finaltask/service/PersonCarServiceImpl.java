package org.lanit.internship.finaltask.service;

import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.exceptions.NotFoundException;
import org.lanit.internship.finaltask.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PersonCarServiceImpl implements PersonCarService {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private CarRepo carRepo;

    @Override
    public String getCarVendor(Car car) throws BadRequestException {
        String vendor = car.getModel().substring(0,car.getModel().indexOf("-"));
        return vendor;
    }

    @Override
    public String getCarModel(Car car) throws BadRequestException {
        String model = car.getModel().substring(car.getModel().indexOf("-") + 1);
        return model;
    }

    @Override
    public Person personIsValid(Person person) throws BadRequestException {
        if (person.getId() == null || personRepo.findById(person.getId()).isPresent() ||
                !(person.getName() instanceof String) || person.getName() == null ||
                !(person.getBirthDate() instanceof Date) || person.getBirthDate() == null ||
                !person.getBirthDate().before(new Date())) {
            throw new BadRequestException();
        }
        return person;
    }

    @Override
    public Car carIsValid(Car car) throws BadRequestException {
        if (!(car.getModel() instanceof String) || car.getModel() == null ||
                carRepo.findById(car.getId()).isPresent() ||
                getCarVendor(car).equals("") || getCarModel(car).equals("") ||
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
    public PersonWithCars getPersonWithCars(Long personid) {
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
        personWithCars.setBirthDate(personById.get().getBirthDate());
        List<Optional<Car>> carsByOwnerId = carRepo.findByOwnerId(personid);
        personWithCars.setCars(carsByOwnerId);
        return personWithCars;
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
    public void save(Person person) {
        personRepo.save(personIsValid(person));
    }

    @Override
    public Person savePerson(Person person) {
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
