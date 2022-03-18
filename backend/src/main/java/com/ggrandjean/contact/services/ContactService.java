package com.ggrandjean.contact.services;

import com.ggrandjean.contact.entities.ContactEntity;
import com.ggrandjean.contact.exceptions.ResourceNotFoundException;
import com.ggrandjean.contact.mappers.ContactMapper;
import com.ggrandjean.contact.persistence.ContactDao;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ContactService {
    private final ContactDao dao;
    private final ContactMapper mapper;

    @Transactional(readOnly = true)
    public ContactEntity getOneContact(String contactId) {
        return dao.findById(contactId).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public ContactEntity createContact(ContactEntity contact) {
        return dao.insert(createContactEntity(contact));
    }

    @Transactional
    public ContactEntity updateContact(String contactId, ContactEntity source) {
        var entityToUpdate = getOneContact(contactId);
        mapper.updateEntity(source, entityToUpdate);
        entityToUpdate.setId(contactId);
        return dao.save(entityToUpdate);
    }

    @Transactional
    public void deleteContact(String contactId) {
        var entityToDelete = getOneContact(contactId);
        dao.deleteById(entityToDelete.getId());
    }

    private ContactEntity createContactEntity(ContactEntity contact) {
        var newEntity = new ContactEntity();
        mapper.updateEntity(contact, newEntity);
        newEntity.setId(new ObjectId().toHexString());
        return newEntity;
    }
}
