package com.example.db_key_value.contreller;

import com.example.db_key_value.exception.EntityNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-evidence-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-evidence-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EvidenceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getValueByKey() throws Exception {
        this.mockMvc.perform(get("/api/evidence/{key}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("test"));
    }

    @Test
    void getValueByKeyFailed() throws Exception {
        this.mockMvc.perform(get("/api/evidence/{key}", 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        Assert.assertTrue(mvcResult.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(mvcResult ->
                        Assert.assertEquals("Entity with id 3 not found",
                                mvcResult.getResolvedException().getMessage()));
    }
}