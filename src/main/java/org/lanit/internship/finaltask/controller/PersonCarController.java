package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("person")
public class PersonCarController {
    private int counter = 4;
    private List<Map<String,String>> persons = new ArrayList<Map<String,String>>() {{
        add(new HashMap<String, String>() {{put("id","1"); put("name", "Ivan"); put("birthdate","2006-05-25");}});
        add(new HashMap<String, String>() {{put("id","2"); put("name", "Pavel"); put("birthdate","1989-01-17");}});
        add(new HashMap<String, String>() {{put("id","3"); put("name", "Anton"); put("birthdate","1970-03-06");}});
    }};
    private List<Map<String,String>> cars = new ArrayList<Map<String,String>>() {{
        add(new HashMap<String, String>() {{put("id","1"); put("model", "BMW-X5"); put("horsepower","381"); put("ownerId","3");}});
        add(new HashMap<String, String>() {{put("id","2"); put("model", "Audi-R8"); put("horsepower","540"); put("ownerId","2");}});
        add(new HashMap<String, String>() {{put("id","3"); put("model", "Ford-GT"); put("horsepower","700"); put("ownerId","3");}});
    }};

    @GetMapping
    public List<Map<String,String>> list() {
        return persons;
    }

    @GetMapping("{id}")
    public Map<String,String> getOne(@PathVariable String id) {
        return persons.stream()
                .filter(person -> person.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String,String> create(@RequestBody Map<String,String> person) {
        person.put("id", String.valueOf(counter++));
        persons.add(person);

        return person;
    }

    @PutMapping("{id}")
    public Map<String,String> update(@PathVariable String id, @RequestBody Map<String,String> person) {
        Map<String,String> personFromDb = getOne(id);

        personFromDb.putAll(person);
        personFromDb.put("id",id);

        return personFromDb;
    }

    @PostMapping(value = "/person")
    public void savePerson(@RequestBody Map<String,String> person) {

    }

    @PostMapping(value = "/car")
    public void saveCar(@RequestBody Map<String,String> person) {

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
