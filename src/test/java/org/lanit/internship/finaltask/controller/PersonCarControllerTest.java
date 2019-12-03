package org.lanit.internship.finaltask.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void personsList() {
    }

    @Test
    void carsList() {
    }

    @Test
    void savePerson() {
    }

    @Test
    void saveCar() {
    }

    @Test
    void getPerson() {
    }

    @Test
    void getStatistics() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/statistics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"personcount\":4,\"carcount\":4,\"uniquevendorcount\":3}"));
    }

    @Test
    void clearDB() {
    }
}