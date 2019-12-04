package org.lanit.internship.finaltask.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lanit.internship.finaltask.model.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
class PersonCarServiceImplTest {

    @Resource
    private PersonCarService personCarService;

    @BeforeEach
    public void fillInDataBase() throws Exception {
        personCarService.save(createPerson());
        personCarService.save(createCar());
    }

    @AfterEach
    public void clearDataBase() throws Exception {
        personCarService.findAllPersons().clear();
        personCarService.findAllCars().clear();
    }

    public Person createPerson() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
        return person;
    }

    public Car createCar() {
        Car car = new Car();
        car.setId(2L);
        car.setModel("Lada-Kalina");
        car.setHorsepower(380);
        car.setOwnerId(1L);
        return car;
    }

    @Test
    void carIsValid() {
        Car car = personCarService.carIsValid(createCar());
        Assert.assertEquals(createCar().getId(),car.getId());
        Assert.assertEquals(createCar().getModel(),car.getModel());
        Assert.assertEquals(createCar().getHorsepower(),car.getHorsepower());
        Assert.assertEquals(createCar().getOwnerId(),car.getOwnerId());
    }

    @Test
    void personIsValid() throws Exception {
        Person person = personCarService.personIsValid(createPerson());
        Assert.assertEquals(createPerson(),person);
    }

    @Test
    void getPersonWithCars() throws Exception {
        PersonWithCars personWithCars = personCarService.getPersonWithCars(createPerson().getId());
        Assert.assertEquals(createPerson().getId(),personWithCars.getId());
        Assert.assertEquals(createPerson().getName(),personWithCars.getName());
        Assert.assertEquals(createPerson().getBirthDate(),personWithCars.getBirthDate());
    }

    @Test
    void getStatisticsObject() {
    }

    @Test
    void save() {
    }

    @Test
    void savePerson() {
    }

    @Test
    void testSave() {
    }

    @Test
    void saveCar() {
    }

    @Test
    void findAllPersons() {
    }

    @Test
    void findAllCars() {
    }

    @Test
    void deleteAll() {
    }
}