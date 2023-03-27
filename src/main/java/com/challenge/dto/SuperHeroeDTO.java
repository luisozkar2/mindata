package com.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperHeroeDTO {
    private Long id;
    private String name;
    private String powers;
    private String alias;
}