package com.ggrandjean.contact.services;

import com.ggrandjean.contact.entities.SkillEntity;
import com.ggrandjean.contact.mappers.SkillMapper;
import com.ggrandjean.contact.model.Skill;
import com.ggrandjean.contact.persistence.SkillDao;
import org.springframework.stereotype.Service;

@Service
public class SkillService extends CRUDService<Skill, SkillEntity>  {

    SkillService(SkillMapper skillMapper, SkillDao dao) {
        this.dao = dao;
        this.mapper = skillMapper;
    }
}
