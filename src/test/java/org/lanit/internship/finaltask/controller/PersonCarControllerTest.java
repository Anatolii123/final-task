package org.lanit.internship.finaltask.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
                .andExpect(content().json("[]"));
    }

    @Test
    void carsList() throws Exception {
        this.mockMvc.perform(get("/cars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":2,\"model\":\"VAZ-Lada\",\"horsepower\":380,\"ownerId\":1,\"vendorModel\":\"VAZ\",\"modelModel\":\"Lada\"}]"));
    }

    @Test
    void savePerson() throws Exception {
        savePersonMethod();
        Optional<Person> person2 =  personRepo.findById(4L);
        Assert.assertEquals(Long.valueOf(4L),person2.get().getId());
        Assert.assertEquals("Name",person2.get().getName());
        Assert.assertEquals(Date.valueOf("2000-05-25"),person2.get().getBirthDate());
    }

    @Test
    void saveCar() throws Exception {
        saveCarMethod();
        Optional<Car> car2 =  carRepo.findById(5L);
        Assert.assertEquals("VAZ-Lada",car2.get().getModel());
        Assert.assertEquals(Integer.valueOf(380),car2.get().getHorsepower());
        Assert.assertEquals(Long.valueOf(1L),car2.get().getOwnerId());
    }

    @Test
    void getPerson() {

    }

    @Test
    void getStatistics() throws Exception {
        savePersonMethod();
        saveCarMethod();
        this.mockMvc.perform(get("/statistics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"personcount\":1,\"carcount\":1,\"uniquevendorcount\":1}"));
    }

    @Test
    void clearDB() throws Exception {
        savePersonMethod();
        saveCarMethod();
        clearDatabase();
    }
}