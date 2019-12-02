package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.model.*;
import org.lanit.internship.finaltask.service.PersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("")
public class PersonCarController {
//    private List<Person> persons = new ArrayList<Person>() {{
//        Person person1 = new Person();
//        person1.setId(1L);
//        person1.setName("Ivan");
//        person1.setBirthDate(Date.valueOf("2006-05-25"));
//        add(person1);
//        Person person2 = new Person();
//        person2.setId(2L);
//        person2.setName("Pavel");
//        person2.setBirthDate(Date.valueOf("1989-01-17"));
//        add(person2);
//        Person person3 = new Person();
//        person3.setId(3L);
//        person3.setName("Anton");
//        person3.setBirthDate(Date.valueOf("1970-03-06"));
//        add(person3);
//    }};
//    private List<Car> cars = new ArrayList<Car>() {{
//        Car car1 = new Car();
//        car1.setId(1L);
//        car1.setModel("BMW-X5");
//        car1.setHorsepower(381);
//        car1.setOwnerId(3L);
//        add(car1);
//        Car car2 = new Car();
//        car2.setId(2L);
//        car2.setModel("Audi-R8");
//        car2.setHorsepower(540);
//        car2.setOwnerId(2L);
//        add(car2);
//        Car car3 = new Car();
//        car3.setId(3L);
//        car3.setModel("Ford-GT");
//        car3.setHorsepower(700);
//        car3.setOwnerId(3L);
//        add(car3);
//    }};
//    static boolean carIsValid(Car car, PersonRepo personRepo) {
//        if (!(car.getId() instanceof Long) || car.getId() == null ||
//                        !(car.getModel() instanceof String) || car.getModel() == null ||
//                        car.getVendorModel().equals("") || car.getModelModel().equals("") ||
//                        !(car.getHorsepower() instanceof Integer) || car.getHorsepower() == null ||
//                        !(car.getOwnerId() instanceof Long) || car.getOwnerId() == null ||
//                        car.getHorsepower() <= 0 || !personRepo.findAll().stream()
//                        .filter(person -> person.getId() == car.getOwnerId()).findFirst().isPresent() ||
//                        (new java.util.Date().getYear() - personRepo.findAll().stream()
//                                .filter(person -> person.getId() == car.getOwnerId()).findFirst()
//                                .orElseThrow(BadRequestException::new).getBirthDate().getYear() < 18)) {
//            return false;
//        }
//        return true;
//    }
//
//    static boolean personIsValid(Person person) {
//        if (!(person.getId() instanceof Long) || person.getId() == null ||
//                        !(person.getName() instanceof String) || person.getName() == null ||
//                        !(person.getBirthDate() instanceof Date) || person.getBirthDate() == null ||
//                        !person.getBirthDate().before(new Date())) {
//            return false;
//        }
//        return true;
//    }

    private final PersonRepo personRepo;
    private final CarRepo carRepo;

    @Autowired
    private PersonCarService personCarService;

    @Autowired
    public PersonCarController(PersonRepo personRepo, CarRepo carRepo) {
        this.personRepo = personRepo;
        this.carRepo = carRepo;
    }

    @GetMapping("/persons")
    public List<Person> personsList() {
        return personRepo.findAll(Sort.by("id"));
    }

    @GetMapping("/cars")
    public List<Car> carsList() {
        return carRepo.findAll(Sort.by("id"));
    }

    @PostMapping(value = "/person")
    public void savePerson(@RequestBody Person person) {
        personRepo.save(personCarService.personIsValid(person));
    }

    @PostMapping(value = "/person2")
    public Person savePerson2(@RequestBody Person person) {
        return personRepo.save(personCarService.personIsValid(person));
    }

    @PostMapping(value = "/car")
    public void saveCar(@RequestBody Car car) {
        carRepo.save(personCarService.carIsValid(car, personRepo));
    }

    @PostMapping(value = "/car2")
    public Car saveCar2(@RequestBody Car car) {
        return carRepo.save(personCarService.carIsValid(car, personRepo));
    }

    @GetMapping(value = "/personwithcars")
    public PersonWithCars getPerson(@RequestParam Long personid) throws ParseException {
        return personCarService.getPersonWithCars(personid, personRepo, carRepo);
    }

    @GetMapping(value = "/statistics")
    public Statistics getStatistics() {
        return personCarService.getStatisticsObject(personRepo, carRepo);
    }

    @GetMapping(value = "/clear")
    public void clearDB() {
        carRepo.deleteAll();
        personRepo.deleteAll();
    }
}
