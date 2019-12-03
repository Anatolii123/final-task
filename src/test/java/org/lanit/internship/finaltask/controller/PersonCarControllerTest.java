package org.lanit.internship.finaltask.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.CarRepo;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.model.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import javax.annotation.Resource;
import java.sql.Date;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private CarRepo carRepo;

    @Resource
    private PersonRepo personRepo;

    @AfterEach
    public void clearDataB() {
        carRepo.deleteAll();
        personRepo.deleteAll();
    }

    @BeforeEach
    public void prepareDB() throws Exception {
        savePersonMethod();
        saveCarMethod();
    }

    public void savePersonMethod() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
        personRepo.save(person);
    }

    public void saveCarMethod() {
        Car car = new Car();
        car.setId(1L);
        car.setModel("VAZ-Lada");
        car.setHorsepower(380);
        car.setOwnerId(1L);
        carRepo.save(car);
    }

    public void clearDatabase() throws Exception {
        this.mockMvc.perform(get("/clear"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void personsList() throws Exception {
        this.mockMvc.perform(get("/persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Name\",\"birthDate\":\"2000-05-25\"}]"));
    }

    @Test
    void carsList() throws Exception {
        this.mockMvc.perform(get("/cars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":" +
                        carRepo.findAll().get(0).getId().toString() +
                        ",\"model\":\"VAZ-Lada\",\"horsepower\":380,\"ownerId\":1,\"vendorModel\":\"VAZ\",\"modelModel\":\"Lada\"}]"));
    }

    @Test
    void savePerson() throws Exception {
        clearDataB();
        savePersonMethod();
        Person person2 =  personRepo.findAll().get(0);
        Assert.assertEquals("Name",person2.getName());
        Assert.assertEquals(Date.valueOf("2000-05-25"),person2.getBirthDate());
    }

    @Test
    void saveCar() throws Exception {
        clearDataB();
        saveCarMethod();
        Car car2 =  carRepo.findAll().get(0);
        Assert.assertEquals("VAZ-Lada",car2.getModel());
        Assert.assertEquals(Integer.valueOf(380),car2.getHorsepower());
        Assert.assertEquals(Long.valueOf(1L),car2.getOwnerId());
    }

    @Test
    void getPerson() throws Exception {
        this.mockMvc.perform(get("/personwithcars?personid=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Name\",\"birthDate\":\"2000-05-25\",\"cars\":" +
                                "[{\"id\":" +
                                carRepo.findAll().get(0).getId().toString() +
                                ",\"model\":\"VAZ-Lada\",\"horsepower\":380,\"ownerId\":1,\"vendorModel\":\"VAZ\",\"modelModel\":\"Lada\"}]" +
                        "}"));
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
        clearDatabase();
    }
}