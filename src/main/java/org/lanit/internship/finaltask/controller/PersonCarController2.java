package org.lanit.internship.finaltask.controller;

import org.lanit.internship.finaltask.service.PersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/2")
public class PersonCarController2 {

    @Autowired
    private PersonCarService personCarService;


}
