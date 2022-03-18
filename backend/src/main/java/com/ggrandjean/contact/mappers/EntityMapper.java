package com.ggrandjean.contact.mappers;

import com.ggrandjean.contact.entities.Entity;
import org.mapstruct.MappingTarget;

public interface EntityMapper<D,E extends Entity> {
    E toEntity(D d);
    D toDto(E e);
    void updateEntity(E source, @MappingTarget E target);
}
