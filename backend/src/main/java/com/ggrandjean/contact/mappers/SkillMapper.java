package com.ggrandjean.contact.mappers;

import com.ggrandjean.contact.entities.SkillEntity;
import com.ggrandjean.contact.model.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper extends EntityMapper<Skill, SkillEntity> {
}
