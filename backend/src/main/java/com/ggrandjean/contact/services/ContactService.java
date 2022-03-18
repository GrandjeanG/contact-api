package com.ggrandjean.contact.services;

import com.ggrandjean.contact.entities.ContactEntity;
import com.ggrandjean.contact.entities.SkillEntity;
import com.ggrandjean.contact.mappers.ContactMapper;
import com.ggrandjean.contact.model.Contact;
import com.ggrandjean.contact.persistence.ContactDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ContactService extends CRUDService<Contact, ContactEntity> {

    private final SkillService skillService;

    ContactService(SkillService skillService, ContactDao contactDao, ContactMapper contactMapper) {
        this.skillService = skillService;
        this.dao = contactDao;
        this.mapper = contactMapper;
    }

    @Transactional
    public SkillEntity addSkill(String contactId, String skillId) {
        var contact = getOne(contactId);
        var skill = skillService.getOne(skillId);
        contact.getSkills().add(skill);
        dao.save(contact);
        return skill;
    }

    @Transactional
    public void removeSkill(String contactId, String skillId) {
        var contact = getOne(contactId);
        var skill = skillService.getOne(skillId);
        contact.getSkills().remove(skill);
        dao.save(contact);
    }

    @Transactional(readOnly = true)
    public Set<SkillEntity> getSkills(String contactId) {
        var contact = getOne(contactId);
        return contact.getSkills();
    }
}
