package com.ggrandjean.contact.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "contacts")
public class ContactEntity {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    private String firstname;

    private String lastname;

    private String address;

    private String email;

    private String mobile;

    @DBRef
    private Set<SkillEntity> skills = new HashSet<>();

}
