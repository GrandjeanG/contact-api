package com.ggrandjean.contact.entities;

import com.ggrandjean.contact.model.Level;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "skills")
public class SkillEntity extends Entity {

    private String name;

    private Level level;
}
