package com.example.carsharingservice.mapper;

public interface DtoMapper<D, R, M> {
    M mapToModel(D dto);

    R mapToDto(M model);
}
