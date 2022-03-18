package com.ggrandjean.contact.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

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

}
