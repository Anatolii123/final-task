package org.lanit.internship.finaltask.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class PersonCarController {
    @GetMapping
    public String list() {
        return "index";
    }

}
