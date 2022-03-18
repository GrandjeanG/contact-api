package com.ggrandjean.contact.services;

import com.ggrandjean.contact.entities.Entity;
import com.ggrandjean.contact.exceptions.ResourceNotFoundException;
import com.ggrandjean.contact.mappers.EntityMapper;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class CRUDService<D, E extends Entity> {

    protected EntityMapper<D,E> mapper;
    protected MongoRepository<E, String> dao;

    @Transactional(readOnly = true)
    public List<E> getAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public E getOne(String id) {
        return dao.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public E create(E entity) {
        return dao.insert(createEntity(entity));
    }

    @Transactional
    public E update(String id, E source) {
        var entityToUpdate = getOne(id);
        mapper.updateEntity(source, entityToUpdate);
        entityToUpdate.setId(id);
        return dao.save(entityToUpdate);
    }

    @Transactional
    public void delete(String id) {
        var entityToDelete = getOne(id);
        dao.deleteById(entityToDelete.getId());
    }

    @SneakyThrows
    private E createEntity(E entity) {
        E newEntity = (E) entity.getClass().getConstructor().newInstance();
        mapper.updateEntity(entity, newEntity);
        newEntity.setId(new ObjectId().toHexString());
        return newEntity;
    }



}
