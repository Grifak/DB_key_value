package com.example.db_key_value.contreller;

import com.example.db_key_value.exception.DataBaseIsEmptyException;
import com.example.db_key_value.exception.FileEmptyException;
import com.example.db_key_value.exception.InvalidFileFormatException;
import org.aspectj.util.FileUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class ManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void dump() throws Exception{
        this.mockMvc.perform(get("/api/management/save")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void dumpFail() throws Exception{
        this.mockMvc.perform(get("/api/management/save")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        Assert.assertTrue(mvcResult.getResolvedException() instanceof DataBaseIsEmptyException))
                .andExpect(mvcResult ->
                        Assert.assertEquals("DataBase is empty",
                                mvcResult.getResolvedException().getMessage()));
    }

    @Test
    void upload() throws Exception{
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.csv",
                MediaType.TEXT_PLAIN_VALUE,
                "key,value,ttl\n 1,test,2022-12-30 23:59:00.0\n 2,task,2022-12-30 23:59:00.0"
                        .getBytes(StandardCharsets.UTF_8));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/api/management/upload").file(file)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].value").value("test"))
                .andExpect(jsonPath("$[1].value").value("task"));

    }

    @Test
    void uploadFileEmpty() throws Exception{
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.csv",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes(StandardCharsets.UTF_8));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/api/management/upload").file(file)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        Assert.assertTrue(mvcResult.getResolvedException() instanceof FileEmptyException))
                .andExpect(mvcResult ->
                        Assert.assertEquals("File 'testFile.csv' is empty",
                                mvcResult.getResolvedException().getMessage()));
    }

    @Test
    void uploadInvalidFileFormat() throws Exception{
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "testFile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes(StandardCharsets.UTF_8));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/api/management/upload").file(file)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        Assert.assertTrue(mvcResult.getResolvedException() instanceof InvalidFileFormatException))
                .andExpect(mvcResult ->
                        Assert.assertEquals("Invalid file format. Filename: testFile.txt",
                                mvcResult.getResolvedException().getMessage()));

    }
}