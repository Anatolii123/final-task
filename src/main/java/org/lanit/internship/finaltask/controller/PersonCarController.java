package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.entities.Person;
import org.lanit.internship.finaltask.entities.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class PersonCarController {
    private final PersonRepository personRepository;

    @Autowired
    public PersonCarController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> list() {
        return personRepository.findAll();
    }

    @GetMapping("{id}")
    public Person getOne(@PathVariable("id") Person person) {
        return person;
    }


    @PostMapping
    public Person create(@RequestBody Person person) {
       return personRepository.save(person);
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Person person) {
        personRepository.delete(person);
    }
}
