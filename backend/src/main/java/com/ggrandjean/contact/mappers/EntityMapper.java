package com.ggrandjean.contact.mappers;

import org.mapstruct.MappingTarget;

public interface EntityMapper<D,E> {
    E toEntity(D d);
    D toDto(E e);
    void updateEntity(E source, @MappingTarget E target);
}
