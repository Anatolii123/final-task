package org.lanit.internship.finaltask.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("person")
public class PersonController {
    public List<Map<String,String>> persons = new ArrayList<Map<String,String>>() {{
        add(new HashMap<String, String>() {{put("id","1"); put("name", "Ivan"); put("birthdate","2000-05-25");}});
        add(new HashMap<String, String>() {{put("id","2"); put("name", "Pavel"); put("birthdate","1989-01-17");}});
        add(new HashMap<String, String>() {{put("id","3"); put("name", "Anton"); put("birthdate","1970-03-06");}});
    }};

    @GetMapping
    public List<Map<String,String>> list() {
        return persons;
    }
}
