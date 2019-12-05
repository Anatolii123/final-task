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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.Calendar;
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
    public void prepareDataBase() {
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

    public Person createPerson() {
        Person person = new Person();
        person.setId(1L);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2000);
        calendar.set(Calendar.MONTH, Calendar.MAY);
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        person.setName("Name");
        person.setBirthDate(calendar.getTime());
        return person;
    }

    public Person createChild() {
        Person person = new Person();
        person.setId(2L);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.MONTH, Calendar.MAY);
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        person.setName("Name");
        person.setBirthDate(calendar.getTime());
        return person;
    }

    public Car createCar() {
        Car car = new Car();
        car.setId(1L);
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
                        "\"birthDate\":\"25.05.2000\"}]"));
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
                        "    \"id\":2,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"25.05.2000\"\n" +
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
    void getPersonWithCars() throws Exception {
        this.mockMvc.perform(get("/personwithcars?personid=" + thePerson.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\":" + thePerson.getId() + "," +
                        "\"name\":\"Name\"," +
                        "\"birthDate\":\"25.05.2000\"," +
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
    void savePersonBadRequests() throws Exception {
        this.mockMvc.perform(post("/person") // id == null
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"25.05.2000\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/person") // !(id instanceof Long)
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":xasxasdasd,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"25.05.2000\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/person") // name == null
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"name\":,\n" +
                        "    \"birthDate\":\"25.05.2000\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/person") // birthDate == null
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/person") // !(birthDate instanceof Date)
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":dsaxsdasxasxaxs\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/person") // birthDate.format != "dd.MM.yyyy"
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"2000-05-25\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/person") // Дата рождения в будущем
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"25.05.2020\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/person") // Ранее передавался валидный объект с таким id
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"25.05.2000\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void saveCarBadRequests() throws Exception {
        this.mockMvc.perform(post("/car") // id == null
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // !(id instanceof Long)
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":asdasxasdasxas,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // model == null
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":,\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // model.format != "vendor-model"
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"BMW\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // vendor пустой
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"-X5\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // vendor содержит "-"
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"BMW-BMW-X5\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // model пустой
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"BMW-\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // horsepower == null
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // !(horsepower instanceof Integer)
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380L,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // ownerId == null
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // !(ownerId instanceof Long)
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":dasdasxasdasd\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // horsepower <= 0
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":-380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // Ранее передавался валидный объект с таким id
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":1,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":" + thePerson.getId() + "\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(post("/car") // Person с Id=ownerId не существует
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":90\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.personCarService.save(createChild());

        this.mockMvc.perform(post("/car") // Person с Id=ownerId младше 18 лет
                .header("Content-Type", "application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":2\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void getPersonWithCarsNotFound() throws Exception {
        this.mockMvc.perform(get("/personwithcars?personid=90"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void getPersonWithCarsBadRequest() throws Exception {
        this.mockMvc.perform(get("/personwithcars?personid=")) // personId == null
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        this.mockMvc.perform(get("/personwithcars?personid=asxasdasdasasxa")) // !(personId instanceof Long)
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
}