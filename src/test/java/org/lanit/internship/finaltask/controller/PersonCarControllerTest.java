package org.lanit.internship.finaltask.controller;

import org.junit.Assert;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PersonCarControllerTest {

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
    void personsList() throws Exception {
        savePersonMethod();
        this.mockMvc.perform(get("/persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{" +
                        "\"id\":1," +
                        "\"name\":\"Name\"," +
                        "\"birthDate\":\"2000-05-25\"}]"));
    }

    @Test
    void carsList() throws Exception {
        savePersonMethod();
        saveCarMethod();
        this.mockMvc.perform(get("/cars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{" +
                        "\"id\":2," +
                        "\"model\":\"Lada-Kalina\"," +
                        "\"horsepower\":380," +
                        "\"ownerId\":1," +
                        "\"vendorModel\":\"Lada\"," +
                        "\"modelModel\":\"Kalina\"}]"));
    }

    @Test
    void savePerson() throws Exception {
        this.mockMvc.perform(post("/person")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"2000-05-25\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
        Person person2 = personCarService.findAllPersons().get(0);
        Person person = createPerson();
        person.setId(personCarService.findAllPersons().get(0).getId());
        Assert.assertEquals(person, person2);
    }

    @Test
    void saveCar() throws Exception {
        this.mockMvc.perform(post("/car")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + personCarService.findAllPersons().get(0).getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
        Car car2 = personCarService.findAllCars().get(0);
        Assert.assertEquals(personCarService.findAllCars().get(0).getId(), car2.getId());
        Assert.assertEquals(createCar().getModel(), car2.getModel());
        Assert.assertEquals(createCar().getHorsepower(), car2.getHorsepower());
        Assert.assertEquals(personCarService.findAllPersons().get(0).getId(), car2.getOwnerId());
    }

    @Test
    void getPerson() throws Exception {
        this.mockMvc.perform(get("/personwithcars?personid=" + personCarService.findAllPersons().get(0).getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\":1," +
                        "\"name\":\"Name\"," +
                        "\"birthDate\":\"2000-05-25\"," +
                        "\"cars\":[{" +
                        "   \"id\":2," +
                        "   \"model\":\"Lada-Kalina\"," +
                        "   \"horsepower\":380," +
                        "   \"ownerId\":1," +
                        "   \"modelModel\":\"Kalina\"," +
                        "   \"vendorModel\":\"Lada\"}]}"));
    }

    @Test
    void getStatistics() throws Exception {
        this.mockMvc.perform(get("/statistics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"personcount\":1,\"carcount\":1,\"uniquevendorcount\":1}"));
    }

    @Test
    void clearDB() throws Exception {
         this.mockMvc.perform(get("/clear"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}