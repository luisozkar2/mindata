package com.challenge.service;

import java.util.List;

import com.challenge.dto.SuperHeroeDTO;
import com.challenge.exceptions.ResourceNotFoundException;

public interface SuperHeroeService {
	List<SuperHeroeDTO> getAllSuperHeroes();

	SuperHeroeDTO getSuperHeroeById(Long id) throws ResourceNotFoundException;

	SuperHeroeDTO createSuperHeroe(SuperHeroeDTO superHeroe);

	SuperHeroeDTO updateSuperHeroe(Long id, SuperHeroeDTO superHeroe) throws ResourceNotFoundException;

	void deleteSuperHeroe(Long id) throws ResourceNotFoundException;

	List<SuperHeroeDTO> findByNameContaining(String query);
	
	void flushCache();
}