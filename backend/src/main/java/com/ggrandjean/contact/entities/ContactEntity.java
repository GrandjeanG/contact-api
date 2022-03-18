package com.ggrandjean.contact.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "contacts")
public class ContactEntity extends Entity {

    private String firstname;

    private String lastname;

    private String address;

    private String email;

    private String mobile;

    @DBRef
    private Set<SkillEntity> skills = new HashSet<>();

}
