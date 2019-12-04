package org.lanit.internship.finaltask.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lanit.internship.finaltask.exceptions.BadRequestException;
import org.lanit.internship.finaltask.exceptions.NotFoundException;
import org.lanit.internship.finaltask.model.Car;
import org.lanit.internship.finaltask.model.Person;
import org.lanit.internship.finaltask.model.PersonWithCars;
import org.lanit.internship.finaltask.model.Statistics;
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
        person.setName("Name");
        person.setBirthDate(Date.valueOf("2000-05-25"));
        return person;
    }

    public Car createCar() {
        Car car = new Car();
        car.setModel("Lada-Kalina");
        car.setHorsepower(380);
        car.setOwnerId(thePerson != null && thePerson.getId() != null ? thePerson.getId() : 1L);
        return car;
    }

    public Person createWrongPerson() throws Exception {
        Person person = new Person();
        person.setName("Ivan");
        person.setBirthDate(Date.valueOf("2027-01-06"));
        return person;
    }

    public Car createWrongCar() {
        Car car = new Car();
        car.setModel("Chevrolet-");
        car.setHorsepower(160);
        car.setOwnerId(thePerson != null && thePerson.getId() != null ? thePerson.getId() : 1L);
        return car;
    }

    @Test
    void personIsValid() {
        Person person = personCarService.personIsValid(thePerson);
        Assert.assertEquals(thePerson, person);
    }

    @Test
    void carIsValid() {
        Car car = personCarService.carIsValid(createCar());
        Assert.assertEquals(createCar().getId(), car.getId());
        Assert.assertEquals(createCar().getModel(), car.getModel());
        Assert.assertEquals(createCar().getHorsepower(), car.getHorsepower());
        Assert.assertEquals(createCar().getOwnerId(), car.getOwnerId());
    }

    @Test
    void getPersonWithCars() throws Exception {
        PersonWithCars personWithCars = personCarService.getPersonWithCars(thePerson.getId());
        Assert.assertEquals(thePerson.getId(), personWithCars.getId());
        Assert.assertEquals(thePerson.getName(), personWithCars.getName());
        Assert.assertEquals(thePerson.getBirthDate(), personWithCars.getBirthDate());
        Assert.assertEquals(1, personWithCars.getCars().size());
        Assert.assertEquals(theCar.getId(), personWithCars.getCars().get(0).get().getId());
        Assert.assertEquals(theCar.getModel(), personWithCars.getCars().get(0).get().getModel());
        Assert.assertEquals(theCar.getHorsepower(), personWithCars.getCars().get(0).get().getHorsepower());
        Assert.assertEquals(theCar.getOwnerId(), personWithCars.getCars().get(0).get().getOwnerId());
    }

    @Test
    void getStatisticsObject() {
        Statistics statistics = new Statistics();
        statistics.setPersoncount(1L);
        statistics.setCarcount(1L);
        statistics.setUniquevendorcount(1L);
        Statistics statistics2 = personCarService.getStatisticsObject();
        Assert.assertEquals(statistics.getPersoncount(), statistics2.getPersoncount());
        Assert.assertEquals(statistics.getCarcount(), statistics2.getCarcount());
        Assert.assertEquals(statistics.getUniquevendorcount(), statistics2.getUniquevendorcount());
    }

    @Test
    void save() throws Exception {
        int size = personCarService.findAllPersons().size();
        Assert.assertEquals(1, size);

        personCarService.save(createPerson());
        Assert.assertTrue(personCarService.findAllPersons().size() > size);
    }

    @Test
    void save2() {
        int size = personCarService.findAllCars().size();
        Assert.assertEquals(1, size);

        personCarService.save(createCar());
        Assert.assertTrue(personCarService.findAllCars().size() > size);
    }

    @Test
    void savePerson() throws Exception {
        int size = personCarService.findAllPersons().size();
        Assert.assertEquals(1, size);

        personCarService.savePerson(createPerson());
        Assert.assertTrue(personCarService.findAllPersons().size() > size);
    }

    @Test
    void saveCar() {
        int size = personCarService.findAllCars().size();
        Assert.assertEquals(1, size);

        personCarService.saveCar(createCar());
        Assert.assertTrue(personCarService.findAllCars().size() > size);
    }

    @Test
    void findAllPersons() {
        Assert.assertEquals(persons.size(), personCarService.findAllPersons().size());
        Assert.assertEquals(1, personCarService.findAllPersons().stream().filter(person -> thePerson.getId().equals(person.getId())).count());
    }

    @Test
    void findAllCars() {
        Assert.assertEquals(cars.size(), personCarService.findAllCars().size());
        Assert.assertEquals(1, personCarService.findAllCars().stream().filter(car -> theCar.getId().equals(car.getId())).count());
    }

    @Test
    void deleteAll() {
        personCarService.deleteAll();
        Assert.assertEquals(0, personCarService.findAllCars().size());
        Assert.assertEquals(0, personCarService.findAllPersons().size());
    }

    /**
     *WrongExecutionTests
     **/

    @Test
    void personIsInvalidBadRequest() throws Exception {
        try {
            Person person = personCarService.personIsValid(createWrongPerson());
        } catch (BadRequestException b) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

    @Test
    void carIsInvalidBadRequest() {
        try {
            personCarService.carIsValid(createWrongCar());
        } catch (BadRequestException b) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

    @Test
    void getPersonWithCarsNotFound() throws Exception {
        try {
            personCarService.getPersonWithCars(90L);
        } catch (NotFoundException n) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

    @Test
    void getPersonWithCarsBadRequest() {
        try {
            personCarService.getPersonWithCars(null);
        } catch (Exception b) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

    @Test
    void getStatisticsObjectFailedTest() {
        Statistics statistics = new Statistics();
        statistics.setPersoncount(2L);
        statistics.setCarcount(2L);
        statistics.setUniquevendorcount(2L);
        Statistics statistics2 = personCarService.getStatisticsObject();
        Assert.assertNotEquals(statistics.getPersoncount(), statistics2.getPersoncount());
        Assert.assertNotEquals(statistics.getCarcount(), statistics2.getCarcount());
        Assert.assertNotEquals(statistics.getUniquevendorcount(), statistics2.getUniquevendorcount());
    }

    @Test
    void saveFailedTest() throws Exception {
        try {
            personCarService.save(createWrongPerson());
        } catch (BadRequestException b) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

    @Test
    void save2FailedTest() {
        try {
            personCarService.save(createWrongCar());
        } catch (BadRequestException b) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

    @Test
    void savePersonFailedTest() throws Exception {
        try {
            personCarService.savePerson(createWrongPerson());
        } catch (BadRequestException b) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

    @Test
    void saveCarFailedTest() {
        try {
            personCarService.saveCar(createWrongCar());
        } catch (BadRequestException b) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

    @Test
    void findAllPersonsFailedTest() {
        Assert.assertNotEquals(persons.size() + 1, personCarService.findAllPersons().size());
        Assert.assertNotEquals(2, personCarService.findAllPersons().stream().filter(person -> thePerson.getId().equals(person.getId())).count());
    }

    @Test
    void findAllCarsFailedTest() {
        Assert.assertNotEquals(cars.size() + 1, personCarService.findAllCars().size());
        Assert.assertNotEquals(2, personCarService.findAllPersons().stream().filter(person -> thePerson.getId().equals(person.getId())).count());
    }

}