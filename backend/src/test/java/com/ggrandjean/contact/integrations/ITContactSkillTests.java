package com.ggrandjean.contact.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ggrandjean.contact.model.Contact;
import com.ggrandjean.contact.model.Skill;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ITContactSkillTests {

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static Contact contact;
    private static Skill skill;

    @Test
    void test_0_createObjects() throws Exception {
        ObjectNode bodySkill = mapper.createObjectNode()
                .put(Skill.Fields.name, "skillName");

        String response = mockMvc.perform(
                        post("/skill")
                                .content(bodySkill.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        skill = mapper.readValue(response, Skill.class);

        ObjectNode bodyContact = mapper.createObjectNode()
                .put(Contact.Fields.firstname, "firstname")
                .put(Contact.Fields.lastname, "lastname");

        response = mockMvc.perform(
                        post("/contact")
                                .content(bodyContact.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        contact = mapper.readValue(response, Contact.class);
    }

    @Test
    void test_1_addSkill() throws Exception {
        mockMvc.perform(
                        put(String.format("/contact/%s/skill/%s", contact.getId(), skill.getId()))
                )
                .andExpect(status().isOk());
    }

    @Test
    void test_2_getSkills() throws Exception {
        mockMvc.perform(
                        get(String.format("/contact/%s/skill", contact.getId()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]." + Skill.Fields.id, is(skill.getId())));

    }

    @Test
    void test_2_removeSkill() throws Exception {
        mockMvc.perform(
                        delete(String.format("/contact/%s/skill/%s", contact.getId(), skill.getId()))
                )
                .andExpect(status().isNoContent());

        mockMvc.perform(
                        get(String.format("/contact/%s/skill", contact.getId()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", empty()));

    }

    @Test
    void test_3_deleteObjects() throws Exception {
        mockMvc.perform(
                        delete(String.format("/skill/%s", skill.getId()))
                )
                .andExpect(status().isNoContent());


        mockMvc.perform(
                        delete(String.format("/contact/%s", contact.getId()))
                )
                .andExpect(status().isNoContent());

    }


}
