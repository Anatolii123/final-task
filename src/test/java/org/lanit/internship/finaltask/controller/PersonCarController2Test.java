package org.lanit.internship.finaltask.controller;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lanit.internship.finaltask.service.PersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PersonCarController2Test {

    @Autowired
    private MockMvc mockMvc;

    @Resource
    private PersonCarService personCarService;

    @AfterEach
    void clearDB() throws Exception {
        this.mockMvc.perform(get("/clear"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public ResultActions personSave() throws Exception {
         return this.mockMvc.perform(post("/2/person")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthdate\":\"25.05.2000\"\n" +
                        "}"));
    }

    public ResultActions carSave() throws Exception {
        return this.mockMvc.perform(post("/2/car")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":1\n" +
                        "}"));
    }

    public ResultActions nissanXTrailSave() throws Exception {
        return this.mockMvc.perform(post("/2/car")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Nissan-X-Trail\",\n" +
                        "    \"horsepower\":163,\n" +
                        "    \"ownerId\":1\n" +
                        "}"));
    }

    public ResultActions mercedesBenzSave() throws Exception {
        return this.mockMvc.perform(post("/2/car")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Mercedes-Benz-A Sedan\",\n" +
                        "    \"horsepower\":163,\n" +
                        "    \"ownerId\":1\n" +
                        "}"));
    }

    @Test
    void savePerson() throws Exception {
        personSave()
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthdate\":\"25.05.2000\"\n" +
                        "}"));
    }

    @Test
    void saveCar() throws Exception {
        personSave();
        carSave()
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":1\n" +
                        "}"));
    }

    @Test
    void getNewPersonId() throws Exception {
        personSave();
        this.mockMvc.perform(get("/2/newPersonId"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void getNewCarId() throws Exception {
        personSave();
        carSave();
        this.mockMvc.perform(get("/2/newCarId"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    /**
     *WrongExecutionTests
     **/

    @Test
    void savePersonBadRequest() throws Exception {
        this.mockMvc.perform(post("/2/person")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthdate\":\"25.05.2020\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void saveCarBadRequest() throws Exception {
        personSave();
        this.mockMvc.perform(post("/2/car")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":-380,\n" +
                        "    \"ownerId\":1\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    /**
     * Nissan - X-Trail - Car
     *
     */

    @Test
    void saveCarDifficultModel() throws Exception {
        personSave();
        nissanXTrailSave()
                .andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals("Nissan",personCarService.getCarVendor(personCarService.findAllCars().get(0)));
        Assert.assertEquals("X-Trail",personCarService.getCarModel(personCarService.findAllCars().get(0)));
    }

    /**
     * Mercedes-Benz - vendor
     *
     */

    @Test
    void saveCarWrongVendor() throws Exception {
        personSave();
        mercedesBenzSave()
                .andDo(print())
                .andExpect(status().isOk());
        Assert.assertNotEquals("Mercedes-Benz",personCarService.getCarVendor(personCarService.findAllCars().get(0)));
        Assert.assertEquals("Mercedes",personCarService.getCarVendor(personCarService.findAllCars().get(0)));
        Assert.assertNotEquals("A Sedan",personCarService.getCarModel(personCarService.findAllCars().get(0)));
        Assert.assertEquals("Benz-A Sedan",personCarService.getCarModel(personCarService.findAllCars().get(0)));
    }

}