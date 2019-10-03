package com.bigfishgames.gaeskeleton.sample;

import com.bigfishgames.gaeskeleton.memcache.MemcacheClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SampleServiceTest {
    @Autowired
    private SampleService sampleService;

    @MockBean
    private MemcacheClient mockMemcacheClient;

    @Test
    public void setValueSuccess() {
        sampleService.setValue("foo", "bar");
        verify(mockMemcacheClient).put("foo","bar");
    }

    @Test
    public void getValueSuccess() {
        given(sampleService.getValue("foo")).willReturn("bar");
        String result = sampleService.getValue("foo");
        assertEquals("bar", result);
    }

    @Test
    public void getValueNull() {
        given(sampleService.getValue("foo")).willReturn(null);
        String result = sampleService.getValue("foo");
        assertEquals(null, result);
    }
}
