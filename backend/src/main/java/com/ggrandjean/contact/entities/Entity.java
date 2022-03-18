package com.ggrandjean.contact.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
public abstract class Entity {
    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
}
