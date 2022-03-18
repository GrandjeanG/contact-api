package com.ggrandjean.contact.mappers;

import com.ggrandjean.contact.entities.ContactEntity;
import com.ggrandjean.contact.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper extends EntityMapper<Contact, ContactEntity> {

    @Override
    @Mapping(target = "fullname", expression = "java(e.getFirstname() + ' ' + e.getLastname())")
    Contact toDto(ContactEntity e);
}
