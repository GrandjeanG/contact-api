package com.ggrandjean.contact.controllers;

import com.ggrandjean.contact.ContactApi;
import com.ggrandjean.contact.entities.ContactEntity;
import com.ggrandjean.contact.mappers.ContactMapper;
import com.ggrandjean.contact.model.Contact;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.ggrandjean.contact.services.ContactService;

@AllArgsConstructor
@Controller
public class ContactController implements ContactApi {

    private final ContactService service;
    private final ContactMapper mapper;

    @Override
    public ResponseEntity<Contact> createContact(Contact contact) {
        var createdContact = service.createContact(mapper.toEntity(contact));
        return prepareResponse(createdContact, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Contact> getOneContact(String contactId) {
        var contact = service.getOneContact(contactId);
        return prepareResponse(contact, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Contact> updateContact(String contactId, Contact contact) {
        var updatedContact = service.updateContact(contactId, mapper.toEntity(contact));
        return prepareResponse(updatedContact, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteContact(String contactId) {
        service.deleteContact(contactId);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    private ResponseEntity<Contact> prepareResponse(ContactEntity entity, HttpStatus status) {
        return new ResponseEntity<>(
                mapper.toDto(entity),
                status
        );
    }
}
