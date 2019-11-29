package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.exceptions.NotFoundException;
import org.lanit.internship.finaltask.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("")
public class PersonCarController {
    private int personCounter = 4;
    private int carCounter = 4;
    private List<Person> persons = new ArrayList<Person>() {{
        Person person1 = new Person();
        person1.setId(1L);
        person1.setName("Ivan");
        person1.setBirthDate(Date.valueOf("2006-05-25"));
        add(person1);
        Person person2 = new Person();
        person2.setId(2L);
        person2.setName("Pavel");
        person2.setBirthDate(Date.valueOf("1989-01-17"));
        add(person2);
        Person person3 = new Person();
        person3.setId(3L);
        person3.setName("Anton");
        person3.setBirthDate(Date.valueOf("1970-03-06"));
        add(person3);
    }};

    private List<Car> cars = new ArrayList<Car>(){{
        Car car1 = new Car();
        car1.setId(1L);
        car1.setModel("BMW-X5");
        car1.setHorsepower(381);
        car1.setOwnerId(3L);
        add(car1);
        Car car2 = new Car();
        car2.setId(2L);
        car2.setModel("Audi-R8");
        car2.setHorsepower(540);
        car2.setOwnerId(2L);
        add(car2);
        Car car3 = new Car();
        car3.setId(3L);
        car3.setModel("Ford-GT");
        car3.setHorsepower(700);
        car3.setOwnerId(3L);
        add(car3);
    }};

    private final PersonRepo personRepo;
    private final CarRepo carRepo;

    @Autowired
    public PersonCarController(PersonRepo personRepo, CarRepo carRepo) {
        this.personRepo = personRepo;
        this.carRepo = carRepo;
    }

    @GetMapping("/persons")
    public List<Person> personsList() {
        return personRepo.findAll();
    }

    @GetMapping("/cars")
    public List<Car> carsList() {
        return carRepo.findAll();
    }

    @PostMapping(value = "/person")
    public void savePerson(@RequestBody Person person) {
        person.setId((long)personCounter++);
        personRepo.save(person);
    }

    @PostMapping(value = "/car")
    public void saveCar(@RequestBody Car car) {
        car.setId((long)carCounter++);
        carRepo.save(car);
    }

    @GetMapping(value = "/personwithcars")
    public PersonWithCars getPerson(@RequestParam String personid) {
        PersonWithCars personWithCars = new PersonWithCars();
        Person person1 = personRepo.findAll().stream()
                .filter(person -> person.getId() == Long.parseLong(personid))
                .findFirst()
                .orElseThrow(NotFoundException::new);
        personWithCars.setId(person1.getId());
        personWithCars.setName(person1.getName());
        personWithCars.setBirthDate(person1.getBirthDate());
        personWithCars.setCars(carRepo.findAll(),Long.parseLong(personid));

        return personWithCars;
    }

    @GetMapping(value = "/statistics")
    public Statistics getStatistics() {
        Statistics statistics = new Statistics();
        statistics.setPersoncount((long)personRepo.findAll().size());
        statistics.setCarcount((long)carRepo.findAll().size());
        HashSet<String> vendors = new HashSet<String>();
        for (Car car:carRepo.findAll()) {
            vendors.add(car.getVendorModel());
        }
        statistics.setUniquevendorcount((long)vendors.size());
        return statistics;
    }

    @GetMapping(value = "/clear")
    public void clearDB() {
        persons.clear();
        cars.clear();
    }

}
