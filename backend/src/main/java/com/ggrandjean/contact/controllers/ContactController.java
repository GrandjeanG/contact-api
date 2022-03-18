package com.ggrandjean.contact.controllers;

import com.ggrandjean.contact.ContactApi;
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

    @Override
    public ResponseEntity<Contact> getOneContact(String contactId) {
        return new ResponseEntity<>(
                service.getOneContact(contactId),
                HttpStatus.OK
        );
    }
}
