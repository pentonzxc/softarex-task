package com.nikolai.softarex.web.mapper;


public interface EntityMapper<E , D> {
    E convertDtoToEntity(D dto);
    D convertEntityToDto(E entity);
}
