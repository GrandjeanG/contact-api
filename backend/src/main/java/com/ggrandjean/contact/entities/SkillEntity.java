package com.ggrandjean.contact.entities;


import com.ggrandjean.contact.model.Skill;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@NoArgsConstructor
@Document(collection = "skills")
public class SkillEntity {
    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    private String name;

    private Skill.LevelEnum level;
}
