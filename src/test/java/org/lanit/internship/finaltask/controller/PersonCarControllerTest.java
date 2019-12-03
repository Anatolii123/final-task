package org.lanit.internship.finaltask.controller;

import org.junit.Assert;
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
                .andExpect(content().json("[]"));
    }

    @Test
    void savePerson() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
        personRepo.save(person);
        Optional<Person> person2 =  personRepo.findById(1L);
        Assert.assertEquals(person.getId(),person2.get().getId());
        Assert.assertEquals(person.getName(),person2.get().getName());
        Assert.assertEquals(person.getBirthDate(),person2.get().getBirthDate());
    }

    @Test
    void saveCar() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setModel("VAZ-Lada");
        car.setHorsepower(380);
        car.setOwnerId(1L);
        carRepo.save(car);
        Optional<Car> car2 =  carRepo.findById(2L);
        Assert.assertEquals(car.getModel(),car2.get().getModel());
        Assert.assertEquals(car.getHorsepower(),car2.get().getHorsepower());
        Assert.assertEquals(car.getOwnerId(),car2.get().getOwnerId());
    }

    @Test
    void getPerson() {

    }

    @Test
    void getStatistics() throws Exception {
        this.mockMvc.perform(get("/statistics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"personcount\":0,\"carcount\":0,\"uniquevendorcount\":0}"));
    }

    @Test
    void clearDB() {

    }
}