package com.example.db_key_value.contreller;

import com.example.db_key_value.dto.request.EvidenceRequestDto;
import com.example.db_key_value.exception.EntityNotFoundException;
import com.example.db_key_value.exception.RequiredFieldIsEmptyException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class EvidenceManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createEvidence() throws Exception{
        EvidenceRequestDto requestDto = EvidenceRequestDto.builder()
                .key(1L)
                .value("test")
                .ttl(Timestamp.valueOf(LocalDateTime.of(2021,10,30,22,11)))
                .build();

        this.mockMvc.perform(post("/api/evidence/management")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(mvcResult -> Assert.assertEquals("true",
                        mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void createEvidenceEmptyKeyField() throws Exception{
        EvidenceRequestDto requestDto = EvidenceRequestDto.builder()
                .value("test")
                .ttl(Timestamp.valueOf(LocalDateTime.of(2021,10,30,22,11)))
                .build();

        this.mockMvc.perform(post("/api/evidence/management")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        Assert.assertTrue(mvcResult.getResolvedException() instanceof RequiredFieldIsEmptyException))
                .andExpect(mvcResult ->
                        Assert.assertEquals("Field 'key' cant be empty",
                                mvcResult.getResolvedException().getMessage()));
    }

    @Test
    void createEvidenceEmptyValueField() throws Exception{
        EvidenceRequestDto requestDto = EvidenceRequestDto.builder()
                .key(1L)
                .ttl(Timestamp.valueOf(LocalDateTime.of(2021,10,30,22,11)))
                .build();

        this.mockMvc.perform(post("/api/evidence/management")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        Assert.assertTrue(mvcResult.getResolvedException() instanceof RequiredFieldIsEmptyException))
                .andExpect(mvcResult ->
                        Assert.assertEquals("Field 'value' cant be empty",
                                mvcResult.getResolvedException().getMessage()));
    }

    @Test
    @Sql(value = {"/create-evidence-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-evidence-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteEvidence() throws Exception{
        this.mockMvc.perform(delete("/api/evidence/management/{key}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("test"));
    }

    @Test
    @Sql(value = {"/create-evidence-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-evidence-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteEvidenceFail() throws Exception{
        this.mockMvc.perform(delete("/api/evidence/management/{key}", 3L)
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