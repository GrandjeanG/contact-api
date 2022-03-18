package com.ggrandjean.contact.controllers;

import com.ggrandjean.contact.ContactApi;
import com.ggrandjean.contact.entities.ContactEntity;
import com.ggrandjean.contact.mappers.ContactMapper;
import com.ggrandjean.contact.mappers.SkillMapper;
import com.ggrandjean.contact.model.Contact;
import com.ggrandjean.contact.model.Skill;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ggrandjean.contact.services.ContactService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class ContactController implements ContactApi {

    private final ContactService service;
    private final SkillMapper skillMapper;
    private final ContactMapper mapper;

    @Override
    public ResponseEntity<Contact> createContact(Contact contact) {
        var createdContact = service.create(mapper.toEntity(contact));
        return prepareResponse(createdContact, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Contact>> getAllContacts() {
        var contacts = service.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(
                contacts,
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Contact> getOneContact(String contactId) {
        var contact = service.getOne(contactId);
        return prepareResponse(contact, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Contact> updateContact(String contactId, Contact contact) {
        var updatedContact = service.update(contactId, mapper.toEntity(contact));
        return prepareResponse(updatedContact, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteContact(String contactId) {
        service.delete(contactId);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    @Override
    public ResponseEntity<Skill> addSkill(String contactId, String skillId) {
        var addedSkill = service.addSkill(contactId, skillId);
        return new ResponseEntity<>(
                skillMapper.toDto(addedSkill),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> removeSkill(String contactId, String skillId) {
        service.removeSkill(contactId, skillId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Set<Skill>> getAllSkillFromContact(String contactId) {
        var skills = service.getSkills(contactId).stream().map(skillMapper::toDto).collect(Collectors.toSet());
        return new ResponseEntity<>(
                skills,
                HttpStatus.OK
        );
    }


    protected ResponseEntity<Contact> prepareResponse(ContactEntity entity, HttpStatus status) {
        return new ResponseEntity<>(
                mapper.toDto(entity),
                status
        );
    }
}
