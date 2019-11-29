package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.exceptions.NotFoundException;
import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/persons")
    public List<Person> personsList() {
        return persons;
    }

    @GetMapping("/cars")
    public List<Car> carsList() {
        return cars;
    }

//    @GetMapping("{id}")
//    public Map<String,String> getOne(@PathVariable String id) {
//        return persons.stream()
//                .filter(person -> person.getId().equals(id))
//                .findFirst()
//                .orElseThrow(NotFoundException::new);
//    }

//    @PutMapping("{id}")
//    public Map<String,String> update(@PathVariable String id, @RequestBody Map<String,String> person) {
//        Map<String,String> personFromDb = getOne(id);
//
//        personFromDb.putAll(person);
//        personFromDb.put("id",id);
//
//        return personFromDb;
//    }

//    @DeleteMapping("{id}")
//    public void delete(@PathVariable String id) {
//        Map<String, String> person = getOne(id);
//
//        persons.remove(person);
//    }

    @PostMapping(value = "/person")
    public void savePerson(@RequestBody Person person) {
        person.setId((long)personCounter++);
        persons.add(person);
    }

    @PostMapping(value = "/car")
    public void saveCar(@RequestBody Car car) {
        car.setId((long)carCounter++);
        cars.add(car);
    }

//    @PutMapping("{id}")
//    public void getPerson(@RequestBody Map<String,String> person) {
//
//    }

    @GetMapping(value = "/statistics")
    public void getStatistics() {

    }

    @GetMapping(value = "/clear")
    public void clearDB() {

    }

}
