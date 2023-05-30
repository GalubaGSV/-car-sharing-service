package com.example.carsharingservice.service.mapper;

public interface DtoMapper<D, R, M> {
    M mapToModel(D dto);

    R mapToDto(M model);
}
