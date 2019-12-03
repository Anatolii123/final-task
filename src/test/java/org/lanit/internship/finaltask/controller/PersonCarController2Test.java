package org.lanit.internship.finaltask.controller;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.CarRepo;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.model.PersonRepo;
import org.lanit.internship.finaltask.service.PersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PersonCarController2Test {

    @Autowired
    private MockMvc mockMvc;

    @Resource
    private PersonCarService personCarService;

    @AfterEach
    public void clearDataB() throws Exception {
        personCarService.deleteAll();
        personCarService.deleteAll();
    }

    public void savePersonMethod() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
    }

    public void saveCarMethod() {
        Car car = new Car();
        car.setId(1L);
        car.setModel("VAZ-Lada");
        car.setHorsepower(380);
        car.setOwnerId(1L);
    }

    @Test
    void savePerson() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
        savePersonMethod();
        assertEquals(person,personCarService.savePerson(person));
    }

    @Test
    void saveCar2() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
        personCarService.save(person);
        Car car = new Car();
        car.setId(2L);
        car.setModel("VAZ-Lada");
        car.setHorsepower(380);
        car.setOwnerId(1L);
        saveCarMethod();
        assertEquals(car,personCarService.saveCar(car));
    }
}