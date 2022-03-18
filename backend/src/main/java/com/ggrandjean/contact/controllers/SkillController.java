package com.ggrandjean.contact.controllers;


import com.ggrandjean.contact.SkillApi;
import com.ggrandjean.contact.entities.SkillEntity;
import com.ggrandjean.contact.mappers.SkillMapper;
import com.ggrandjean.contact.model.Skill;
import com.ggrandjean.contact.services.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class SkillController implements SkillApi {

    private final SkillService service;
    private final SkillMapper mapper;

    @Override
    public ResponseEntity<Skill> createSkill(Skill skill) {
        var createdSkill = service.create(mapper.toEntity(skill));
        return prepareResponse(createdSkill, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Skill> getOneSkill(String skillId) {
        var skill = service.getOne(skillId);
        return prepareResponse(skill, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Skill> updateSkill(String skillId, Skill skill) {
        var skillUpdated = service.update(skillId, mapper.toEntity(skill));
        return prepareResponse(skillUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteSkill(String skillId) {
        service.delete(skillId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<Skill> prepareResponse(SkillEntity entity, HttpStatus status) {
        return new ResponseEntity<>(
                mapper.toDto(entity),
                status
        );
    }
}
