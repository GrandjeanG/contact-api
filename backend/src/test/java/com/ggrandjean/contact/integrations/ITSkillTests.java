package com.ggrandjean.contact.integrations;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ggrandjean.contact.model.Skill;
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
public class ITSkillTests {
    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String skillName = "skillName";

    private static Skill skill;

    @Test
    void test_0_createSkill() throws Exception {

        ObjectNode body = mapper.createObjectNode()
                .put(Skill.Fields.name, skillName);

        String response = mockMvc.perform(
                        post("/skill")
                                .content(body.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$." + Skill.Fields.id).exists())
                .andExpect(jsonPath("$." + Skill.Fields.name, is(skillName)))
                .andReturn().getResponse().getContentAsString();

        skill = mapper.readValue(response, Skill.class);
    }

    @Test
    void test_1_updateSkill() throws Exception {
        skill.setLevel(Skill.LevelEnum.HIGH);

        mockMvc.perform(put(String.format("/skill/%s", skill.getId()))
                        .content(mapper.writeValueAsString(skill))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + Skill.Fields.id, is(skill.getId())))
                .andExpect(jsonPath("$." + Skill.Fields.level, is(skill.getLevel().name())));
    }


    @Test
    void test_2_getOneSkill() throws Exception {
        mockMvc.perform(get(String.format("/skill/%s", skill.getId()))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + Skill.Fields.id, is(skill.getId())))
                .andExpect(jsonPath("$." + Skill.Fields.name, is(skill.getName())));
    }

    @Test
    void test_3_deleteSkill() throws Exception {
        mockMvc.perform(delete(String.format("/skill/%s", skill.getId()))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        mockMvc.perform(get(String.format("/skill/%s", skill.getId()))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
