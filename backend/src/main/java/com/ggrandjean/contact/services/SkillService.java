package com.ggrandjean.contact.services;

import com.ggrandjean.contact.entities.SkillEntity;
import com.ggrandjean.contact.exceptions.ResourceNotFoundException;
import com.ggrandjean.contact.mappers.SkillMapper;
import com.ggrandjean.contact.persistence.SkillDao;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class SkillService {

    private final SkillDao dao;
    private final SkillMapper mapper;

    @Transactional(readOnly = true)
    public SkillEntity getOneSkill(String skillId) {
        return dao.findById(skillId).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public SkillEntity createSkill(SkillEntity skill) {
        return dao.insert(createSkillEntity(skill));
    }

    @Transactional
    public SkillEntity updateSkill(String skillId, SkillEntity source) {
        var entityToUpdate = getOneSkill(skillId);
        mapper.updateEntity(source, entityToUpdate);
        entityToUpdate.setId(skillId);
        return dao.save(entityToUpdate);
    }

    @Transactional
    public void deleteSkill(String skillId) {
        var entityToDelete = getOneSkill(skillId);
        dao.deleteById(entityToDelete.getId());
    }

    private SkillEntity createSkillEntity(SkillEntity skill) {
        var newEntity = new SkillEntity();
        mapper.updateEntity(skill, newEntity);
        newEntity.setId(new ObjectId().toHexString());
        return newEntity;
    }

}
