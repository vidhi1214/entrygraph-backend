package com.entrygraph.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createDestination_shouldReturn201() throws Exception {

        String requestBody = """
                {
                    "name": "Test Cafe",
                    "address": "Mumbai",
                    "latitude": 19.10,
                    "longitude": 72.88,
                    "category": "CAFE",
                    "description": "Test destination"
                }
                """;

        mockMvc.perform(post("/api/v1/destinations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
}