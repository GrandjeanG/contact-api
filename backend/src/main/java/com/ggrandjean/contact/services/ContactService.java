package com.ggrandjean.contact.services;

import com.ggrandjean.contact.entities.ContactEntity;
import com.ggrandjean.contact.entities.SkillEntity;
import com.ggrandjean.contact.exceptions.ResourceNotFoundException;
import com.ggrandjean.contact.mappers.ContactMapper;
import com.ggrandjean.contact.persistence.ContactDao;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AllArgsConstructor
@Service
public class ContactService {
    private final ContactDao dao;
    private final ContactMapper mapper;
    private final SkillService skillService;

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

    @Transactional
    public SkillEntity addSkill(String contactId, String skillId) {
        var contact = getOneContact(contactId);
        var skill = skillService.getOneSkill(skillId);
        contact.getSkills().add(skill);
        dao.save(contact);
        return skill;
    }

    @Transactional
    public void removeSkill(String contactId, String skillId) {
        var contact = getOneContact(contactId);
        var skill = skillService.getOneSkill(skillId);
        contact.getSkills().remove(skill);
        dao.save(contact);
    }

    @Transactional(readOnly = true)
    public Set<SkillEntity> getSkills(String contactId) {
        var contact = getOneContact(contactId);
        return contact.getSkills();
    }

    private ContactEntity createContactEntity(ContactEntity contact) {
        var newEntity = new ContactEntity();
        mapper.updateEntity(contact, newEntity);
        newEntity.setId(new ObjectId().toHexString());
        return newEntity;
    }
}
