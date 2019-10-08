package com.bigfishgames.gaeskeleton.sample;

import com.bigfishgames.gaeskeleton.sample.messages.MemcacheSetRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SampleResourceTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SampleService mockSampleService;

    @Test
    public void sampleGetSuccess() throws Exception {
        mvc.perform(
            get("/v1/sample/get/9999"))
            .andExpect(status().isOk())
            .andExpect(content().string("{\"message\":\"sampleGet: 9999\"}"));
    }

    @Test
    public void sampleGetBadRequest() throws Exception {
        mvc.perform(
            get("/v1/sample/get/not-a-number"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getMemcacheValueSuccess() throws Exception {
        given(this.mockSampleService.getValue("foo")).willReturn("bar");
        mvc.perform(
            get("/v1/sample/getmemcachevalue/foo"))
            .andExpect(status().isOk())
            .andExpect(content().string("{\"key\":\"foo\",\"value\":\"bar\"}"));
    }

    @Test
    public void setMemcacheValueSuccess() throws Exception {
        MemcacheSetRequest request = new MemcacheSetRequest();
        request.setKey("foo");
        request.setValue("bar");
        String requestString = objectMapper.writeValueAsString(request);
        mvc.perform(
            post("/v1/sample/setmemcachevalue")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(requestString))
            .andExpect(status().isOk());
        verify(mockSampleService).setValue("foo","bar");
    }
}
