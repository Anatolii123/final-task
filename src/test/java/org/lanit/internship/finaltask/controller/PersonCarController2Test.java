package org.lanit.internship.finaltask.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void savePerson() throws Exception {
        this.mockMvc.perform(post("/2/person")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"25.05.2000\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Name\",\n" +
                        "    \"birthDate\":\"25.05.2000\"\n" +
                        "}"));
    }

    @Test
    void saveCar() throws Exception {
        this.mockMvc.perform(post("/2/car")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":1\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":380,\n" +
                        "    \"ownerId\":1\n" +
                        "}"));
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
                        "    \"birthDate\":\"25.05.2020\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    void saveCarBadRequest() throws Exception {
        this.mockMvc.perform(post("/2/car")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":2,\n" +
                        "    \"model\":\"Lada-Kalina\",\n" +
                        "    \"horsepower\":-380,\n" +
                        "    \"ownerId\":1\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().is(400));
    }

}