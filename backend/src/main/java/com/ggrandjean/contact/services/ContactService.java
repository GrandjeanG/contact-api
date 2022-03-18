package com.ggrandjean.contact.services;

import com.ggrandjean.contact.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    public Contact getOneContact(String contactId) {
        return new Contact();
    }
}
