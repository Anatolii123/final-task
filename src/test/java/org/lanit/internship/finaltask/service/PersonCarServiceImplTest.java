package org.lanit.internship.finaltask.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lanit.internship.finaltask.model.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class PersonCarServiceImplTest {

    @Resource
    private PersonCarService personCarService;
    private Set<Person> persons = new HashSet<>();
    private Set<Car> cars = new HashSet<>();
    private Person thePerson;
    private Car theCar;

    @BeforeEach
    public void fillInDataBase() throws Exception {
        personCarService.save(createPerson());
        persons.addAll(personCarService.findAllPersons());
        thePerson = persons.stream().findFirst().get(); // получили конкретную сущность-человека из БД

        personCarService.save(createCar());
        cars.addAll(personCarService.findAllCars());
        theCar = cars.stream().findFirst().get(); // а теперь и конкретную сущность-машину
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
        //person.setId(1L);
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
        return person;
    }

    public Car createCar() {
        Car car = new Car();
        //car.setId(2L);
        car.setModel("Lada-Kalina");
        car.setHorsepower(380);
        car.setOwnerId(thePerson != null && thePerson.getId() != null ? thePerson.getId() : 1L);
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
        Person person = personCarService.personIsValid(thePerson);
        Assert.assertEquals(thePerson, person);
    }

    @Test
    void getPersonWithCars() throws Exception {
        PersonWithCars personWithCars = personCarService.getPersonWithCars(thePerson.getId());
        Assert.assertEquals(thePerson.getId(),personWithCars.getId());
        Assert.assertEquals(thePerson.getName(),personWithCars.getName());
        Assert.assertEquals(thePerson.getBirthDate(),personWithCars.getBirthDate());
        Assert.assertEquals(1,personWithCars.getCars().size());
        Assert.assertEquals(theCar.getId(),personWithCars.getCars().get(0).get().getId());
        Assert.assertEquals(theCar.getModel(),personWithCars.getCars().get(0).get().getModel());
        Assert.assertEquals(theCar.getHorsepower(),personWithCars.getCars().get(0).get().getHorsepower());
        Assert.assertEquals(theCar.getOwnerId(),personWithCars.getCars().get(0).get().getOwnerId());
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
        Assert.assertEquals(1, personCarService.findAllPersons().size());
        //Assert.assertEquals(createPerson(),personCarService.findAllPersons().get(0));
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
        int size = personCarService.findAllPersons().size();
        Assert.assertEquals(1, size);

        personCarService.save(createPerson());
        Assert.assertTrue(personCarService.findAllPersons().size() > size);
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
        //Person person = createPerson();
        // к этому моменту в БД уэже что-то есть
        Assert.assertEquals(persons.size(), personCarService.findAllPersons().size());

        //Проверим, что thePerson есть среди того, что есть в БД и ровно один
        Assert.assertEquals(1, personCarService.findAllPersons().stream().filter(person -> thePerson.getId().equals(person.getId())).count());
    }

    @Test
    void findAllCars() {
        Assert.assertEquals(1, personCarService.findAllCars().size());
        Assert.assertEquals(theCar.getId(), personCarService.findAllCars().get(0).getId());
        Assert.assertEquals(theCar.getModel(), personCarService.findAllCars().get(0).getModel());
        Assert.assertEquals(theCar.getHorsepower(), personCarService.findAllCars().get(0).getHorsepower());
        Assert.assertEquals(theCar.getOwnerId(), personCarService.findAllCars().get(0).getOwnerId());
    }

    @Test
    void deleteAll() {
        personCarService.deleteAll();
        Assert.assertEquals(0,personCarService.findAllCars().size());
        Assert.assertEquals(0,personCarService.findAllPersons().size());
    }
}