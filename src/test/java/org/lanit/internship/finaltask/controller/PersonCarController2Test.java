package org.lanit.internship.finaltask.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.service.PersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PersonCarController2Test {

    @Autowired
    private MockMvc mockMvc;

    @Resource
    private PersonCarService personCarService;

    @AfterEach
    public void clearDataBase() {
        personCarService.findAllPersons().clear();
        personCarService.findAllCars().clear();
    }

    @BeforeEach
    public void prepareDataBase() throws Exception {
        savePersonMethod();
        saveCarMethod();
    }

    public Person createPerson() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
        return person;
    }

    public Car createCar() throws Exception {
        Car car = new Car();
        car.setId(2L);
        car.setModel("Lada-Kalina");
        car.setHorsepower(380);
        car.setOwnerId(personCarService.findAllPersons().get(0).getId());
        return car;
    }

    public void savePersonMethod() throws Exception {
        personCarService.save(createPerson());
    }

    public void saveCarMethod() throws Exception {
        personCarService.save(createCar());
    }

    @Test
    void savePerson() throws Exception {
        this.mockMvc.perform(post("/2/person")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"2000-05-25\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"2000-05-25\"\n" +
                        "}"));
    }

    @Test
    void saveCar() throws Exception {
        this.mockMvc.perform(post("/2/car")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + personCarService.findAllPersons().get(0).getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + personCarService.findAllPersons().get(0).getId() + "\n" +
                        "}"));
    }
}