package com.ggrandjean.contact.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ITContactTests {
    @Autowired
    MockMvc mockMvc;

    private static String contactId = "mockedId";

    @Test
    void test_0_getOneContact() throws Exception {
        mockMvc.perform(get(String.format("/contact/%s", contactId))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
