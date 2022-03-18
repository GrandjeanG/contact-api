package com.ggrandjean.contact.persistence;

import com.ggrandjean.contact.entities.ContactEntity;
import com.ggrandjean.contact.entities.SkillEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkillDao extends MongoRepository<SkillEntity, String> {
}
