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
    public void clearDataBase() {
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
        Assert.assertEquals(1,personWithCars.getCars().size());
        Assert.assertEquals(createCar().getId(),personWithCars.getCars().get(0).get().getId());
        Assert.assertEquals(createCar().getModel(),personWithCars.getCars().get(0).get().getModel());
        Assert.assertEquals(createCar().getHorsepower(),personWithCars.getCars().get(0).get().getHorsepower());
        Assert.assertEquals(createCar().getOwnerId(),personWithCars.getCars().get(0).get().getOwnerId());
    }

    @Test
    void getStatisticsObject() {
        Statistics statistics = new Statistics();
        statistics.setPersoncount(1L);
        statistics.setCarcount(1L);
        statistics.setUniquevendorcount(1L);
        Statistics statistics2 = personCarService.getStatisticsObject();
        Assert.assertEquals(statistics.getPersoncount(),statistics2.getPersoncount());
        Assert.assertEquals(statistics.getCarcount(),statistics2.getCarcount());
        Assert.assertEquals(statistics.getUniquevendorcount(),statistics2.getUniquevendorcount());
    }

    @Test
    void save() throws Exception {
        clearDataBase();
        personCarService.save(createPerson());
        Assert.assertEquals(1,personCarService.findAllPersons().size());
        Assert.assertEquals(createPerson(),personCarService.findAllPersons().get(0));
    }

    @Test
    void save2() throws Exception {
        clearDataBase();
        personCarService.save(createPerson());
        personCarService.save(createCar());
        Assert.assertEquals(1,personCarService.findAllCars().size());
        Assert.assertEquals(createCar().getId(),personCarService.findAllCars().get(0).getId());
        Assert.assertEquals(createCar().getModel(),personCarService.findAllCars().get(0).getModel());
        Assert.assertEquals(createCar().getHorsepower(),personCarService.findAllCars().get(0).getHorsepower());
        Assert.assertEquals(createCar().getOwnerId(),personCarService.findAllCars().get(0).getOwnerId());
    }

    @Test
    void savePerson() throws Exception {
        clearDataBase();
        Assert.assertEquals(createPerson(),personCarService.savePerson(createPerson()));
    }

    @Test
    void saveCar() {
        clearDataBase();
        Assert.assertEquals(createCar().getId(),personCarService.saveCar(createCar()).getId());
        Assert.assertEquals(createCar().getModel(),personCarService.saveCar(createCar()).getModel());
        Assert.assertEquals(createCar().getHorsepower(),personCarService.saveCar(createCar()).getHorsepower());
        Assert.assertEquals(createCar().getOwnerId(),personCarService.saveCar(createCar()).getOwnerId());
    }

    @Test
    void findAllPersons() throws Exception {
        Assert.assertEquals(1,personCarService.findAllPersons().size());
        Assert.assertEquals(createPerson(),personCarService.findAllPersons().get(0));
    }

    @Test
    void findAllCars() {
        Assert.assertEquals(1,personCarService.findAllCars().size());
        Assert.assertEquals(createCar().getId(),personCarService.findAllCars().get(0).getId());
        Assert.assertEquals(createCar().getModel(),personCarService.findAllCars().get(0).getModel());
        Assert.assertEquals(createCar().getHorsepower(),personCarService.findAllCars().get(0).getHorsepower());
        Assert.assertEquals(createCar().getOwnerId(),personCarService.findAllCars().get(0).getOwnerId());
    }

    @Test
    void deleteAll() {
    }
}