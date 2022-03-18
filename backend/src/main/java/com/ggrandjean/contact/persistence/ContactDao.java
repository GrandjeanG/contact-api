package com.ggrandjean.contact.persistence;

import com.ggrandjean.contact.entities.ContactEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactDao extends MongoRepository<ContactEntity, String> {
}
