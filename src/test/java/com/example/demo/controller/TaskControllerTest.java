package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class TaskControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    static void setObjectMapper() {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void shouldReturnStatus200_AfterAddingGettingAndRemovingTask() throws Exception {
        Task task = new Task();
        task.setContent("Make laundry");
        task.setDueDateOfTask(LocalDateTime.of(2020, 9, 13, 11, 45));
        task.setTaskStatus(TaskStatus.Doing);
        int id = task.getId();

        mockMvc.perform(
                post("/addTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("Make laundry")))
                .andExpect(jsonPath("$.taskStatus", is("Doing")));


        mockMvc.perform(
                get("/task/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult response = mockMvc.perform(
                delete("/deleteTaskById/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("Task removed", response.getResponse().getContentAsString());
    }
}