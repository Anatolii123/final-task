package org.lanit.internship.finaltask.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void personsList() {
    }

    @Test
    void carsList() {
    }

    @Test
    void savePerson() throws Exception {
        this.mockMvc.perform(post("/person")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":4,\n" +
                        "    \"name\":\"Nads\",\n" +
                        "    \"birthDate\":\"2006-05-25\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void saveCar() throws Exception {
        this.mockMvc.perform(post("/car")
                .header("Content-Type","application/json").content("{\n" +
                        "    \"id\":4,\n" +
                        "    \"model\":\"VAZ-Lada Kalina\",\n" +
                        "    \"horsepower\":395,\n" +
                        "    \"ownerId\":1\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
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