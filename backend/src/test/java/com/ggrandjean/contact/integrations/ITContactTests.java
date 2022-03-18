package com.ggrandjean.contact.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ggrandjean.contact.model.Contact;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ITContactTests {
    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String contactFirstName = "firstName";
    private static final String contactLastName = "lastName";
    private static final String  updatedEmail = "test@gmail.com";
    private static final String  updateFirstName = "Updated";

    private static Contact contact;

    @Test
    void test_0_createContact() throws Exception {

        ObjectNode body = mapper.createObjectNode()
                .put(Contact.Fields.firstname, contactFirstName)
                .put(Contact.Fields.lastname, contactLastName)
                .put(Contact.Fields.address, "1024 Ecublens")
                .put(Contact.Fields.mobile, "+41 76 822 44 51");

        String response = mockMvc.perform(
                        post("/contact")
                                .content(body.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$." + Contact.Fields.id).exists())
                .andExpect(jsonPath("$." + Contact.Fields.firstname, is(contactFirstName)))
                .andExpect(jsonPath("$." + Contact.Fields.lastname, is(contactLastName)))
                .andExpect(jsonPath("$." + Contact.Fields.fullname, is(contactFirstName + ' ' + contactLastName)))
                .andReturn().getResponse().getContentAsString();

        contact = mapper.readValue(response, Contact.class);
    }

    @Test
    void test_1_updateContact() throws Exception {
        contact.setEmail(updatedEmail);
        contact.setFirstname(updateFirstName);

        mockMvc.perform(put(String.format("/contact/%s", contact.getId()))
                        .content(mapper.writeValueAsString(contact))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + Contact.Fields.id, is(contact.getId())))
                .andExpect(jsonPath("$." + Contact.Fields.email, is(updatedEmail)))
                .andExpect(jsonPath("$." + Contact.Fields.fullname, is(updateFirstName + ' ' + contactLastName)));
    }


    @Test
    void test_2_getOneContact() throws Exception {
        mockMvc.perform(get(String.format("/contact/%s", contact.getId()))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + Contact.Fields.id, is(contact.getId())))
                .andExpect(jsonPath("$." + Contact.Fields.fullname, is( updateFirstName + ' ' + contactLastName)));
    }

    @Test
    void test_3_deleteContact() throws Exception {
        mockMvc.perform(delete(String.format("/contact/%s", contact.getId()))
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent());

        mockMvc.perform(get(String.format("/contact/%s", contact.getId()))
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound());
    }
}
