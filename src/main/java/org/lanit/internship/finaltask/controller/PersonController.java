package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("person")
public class PersonController {
    private int counter = 4;
    private List<Map<String,String>> persons = new ArrayList<Map<String,String>>() {{
        add(new HashMap<String, String>() {{put("id","1"); put("name", "Ivan"); put("birthdate","2000-05-25");}});
        add(new HashMap<String, String>() {{put("id","2"); put("name", "Pavel"); put("birthdate","1989-01-17");}});
        add(new HashMap<String, String>() {{put("id","3"); put("name", "Anton"); put("birthdate","1970-03-06");}});
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

}
