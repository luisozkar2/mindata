package com.challenge.mapper;

import org.springframework.stereotype.Component;

import com.challenge.dto.SuperHeroeDTO;
import com.challenge.model.SuperHeroe;

@Component
public class SuperHeroeMapper {
    
    public SuperHeroeDTO toDto(SuperHeroe superHeroe) {
        SuperHeroeDTO dto = new SuperHeroeDTO();
        dto.setId(superHeroe.getId());
        dto.setName(superHeroe.getName());
        dto.setPowers(superHeroe.getPowers());
        dto.setAlias(superHeroe.getAlias());
        return dto;
    }
    
    public SuperHeroe toEntity(SuperHeroeDTO dto) {
        SuperHeroe entity = new SuperHeroe();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPowers(dto.getPowers());
        entity.setAlias(dto.getAlias());
        return entity;
    }
}