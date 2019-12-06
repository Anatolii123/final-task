package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.model.*;
import org.lanit.internship.finaltask.service.PersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private PersonCarService personCarService;

    @GetMapping("/persons")
    public List<Person> personsList() {
        return personCarService.findAllPersons();
    }

//    @GetMapping("/persons")
//    public List<PersonDTO> personsList() {
//        return personCarService.findAllPersons().stream().map(person -> (new PersonDTO()).fromPerson(person)).collect(Collectors.toList());
//    }

    @GetMapping("/cars")
    public List<Car> carsList() {
        return personCarService.findAllCars();
    }

//    @PostMapping(value = "/person")
//    public void savePerson(@RequestBody Person person) throws NoSuchFieldException, IllegalAccessException, ParseException {
//        personCarService.save(person);
//    }

    @PostMapping(value = "/person")
    public void savePerson(@RequestBody PersonDTO personDTO) throws NoSuchFieldException, IllegalAccessException, ParseException {
        try {
            Person person = personDTO.toPerson();
            personCarService.save(person);
        } catch (NullPointerException n) {
            throw new BadRequestException();
        } catch (BadRequestException b) {
            throw new BadRequestException();
        }
    }

//    @PostMapping(value = "/car")
//    public void saveCar(@RequestBody Car car) throws ParseException {
//        personCarService.save(car);
//    }

    @PostMapping(value = "/car")
    public void saveCar(@RequestBody CarDTO carDTO) throws ParseException {
        Car car = carDTO.toCar();
        personCarService.save(car);
    }

    @GetMapping(value = "/personwithcars")
    public PersonWithCars getPerson(@RequestParam Long personid) throws ParseException {
        return personCarService.getPersonWithCars(personid);
    }

    @GetMapping(value = "/statistics")
    public Statistics getStatistics() {
        return personCarService.getStatisticsObject();
    }

    @GetMapping(value = "/clear")
    public void clearDB() {
        personCarService.deleteAll();
    }

    @ExceptionHandler({com.fasterxml.jackson.databind.exc.InvalidFormatException.class,
            com.fasterxml.jackson.core.JsonParseException.class,
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class})
    public void handleException() {
        throw new BadRequestException();
    }
}
