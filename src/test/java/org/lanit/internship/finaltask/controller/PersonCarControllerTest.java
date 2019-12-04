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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

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

    private Set<Person> persons = new HashSet<>();
    private Set<Car> cars = new HashSet<>();
    private Person thePerson;
    private Car theCar;

    @BeforeEach
    public void prepareDataBase() throws Exception {
        personCarService.save(createPerson());
        persons.addAll(personCarService.findAllPersons());
        thePerson = persons.stream().findFirst().get();

        personCarService.save(createCar());
        cars.addAll(personCarService.findAllCars());
        theCar = cars.stream().findFirst().get();
    }

    @AfterEach
    public void clearDataBase() {
        personCarService.deleteAll();
        persons.clear();
        cars.clear();
        thePerson = null;
        theCar = null;
    }

    public Person createPerson() throws Exception {
        Person person = new Person();
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
        return person;
    }

    public Car createCar() throws Exception {
        Car car = new Car();
        car.setModel("Lada-Kalina");
        car.setHorsepower(380);
        car.setOwnerId(thePerson != null && thePerson.getId() != null ? thePerson.getId() : 1L);
        return car;
    }

    @Test
    void personsList() throws Exception {
        this.mockMvc.perform(get("/persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{" +
                        "\"id\":" + thePerson.getId() + "," +
                        "\"name\":\"Name\"," +
                        "\"birthDate\":\"2000-05-25\"}]"));
    }

    @Test
    void carsList() throws Exception {
        this.mockMvc.perform(get("/cars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{" +
                        "\"id\":" + theCar.getId() + "," +
                        "\"model\":\"Lada-Kalina\"," +
                        "\"horsepower\":380," +
                        "\"ownerId\":" + thePerson.getId() + "}]"));
    }

    @Test
    void savePerson() throws Exception {
        this.mockMvc.perform(post("/person")
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"2000-05-25\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void saveCar() throws Exception {
        this.mockMvc.perform(post("/car")
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getPerson() throws Exception {
        this.mockMvc.perform(get("/personwithcars?personid=" + personCarService.findAllPersons().get(0).getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\":" + thePerson.getId() + "," +
                        "\"name\":\"Name\"," +
                        "\"birthDate\":\"2000-05-25\"," +
                        "\"cars\":[{" +
                        "   \"id\":" + theCar.getId() + "," +
                        "   \"model\":\"Lada-Kalina\"," +
                        "   \"horsepower\":380," +
                        "   \"ownerId\":" + thePerson.getId() + "}]}"));
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

    /**
     *WrongExecutionTests
     **/

    @Test
    void savePersonFailedTest() throws Exception {
        this.mockMvc.perform(post("/person")
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"2020-05-25\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    void saveCarFailedTest() throws Exception {
        this.mockMvc.perform(post("/car")
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":-380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    void getPersonNotFound() throws Exception {
        this.mockMvc.perform(get("/personwithcars?personid=90"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    void getPersonBadRequest() throws Exception {
        this.mockMvc.perform(get("/personwithcars?personid=dasdasdasd"))
                .andDo(print())
                .andExpect(status().is(400));
    }

//    @Test
//    void getStatistics() throws Exception {
//        this.mockMvc.perform(get("/statistics"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"personcount\":1,\"carcount\":1,\"uniquevendorcount\":1}"));
//    }
}