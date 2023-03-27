package com.challenge.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.challenge.dto.SuperHeroeDTO;
import com.challenge.exceptions.ResourceNotFoundException;
import com.challenge.mapper.SuperHeroeMapper;
import com.challenge.model.SuperHeroe;
import com.challenge.repo.SuperHeroeRepository;
import com.challenge.service.SuperHeroeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SuperHeroeServiceImpl implements SuperHeroeService {

    private SuperHeroeRepository superHeroeRepository;
    private SuperHeroeMapper mapper;
    private static final String SUPER_HEROE="SuperHeroe";
   
    @Autowired
    public SuperHeroeServiceImpl(SuperHeroeRepository superHeroeRepository, SuperHeroeMapper mapper) {
		super();
		this.superHeroeRepository = superHeroeRepository;
		this.mapper = mapper;
	}

	@Override
    @Cacheable("superheroes")
    public List<SuperHeroeDTO> getAllSuperHeroes() {
    	return Optional.of(superHeroeRepository.findAll())
            .orElse(new ArrayList<>())
            .stream()
            .map(this.mapper::toDto)
            .toList(); 
    }

    @Override
    public SuperHeroeDTO getSuperHeroeById(Long id) throws ResourceNotFoundException {
    	return mapper.toDto(superHeroeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SUPER_HEROE, "id", id)));
    }

    @Override
    @CacheEvict(cacheNames="superheroes", allEntries=true)
    public SuperHeroeDTO createSuperHeroe(SuperHeroeDTO superHeroe) {
        return mapper.toDto(superHeroeRepository.save(mapper.toEntity(superHeroe)));
    }

    @Override
    @CacheEvict(cacheNames="superheroes", allEntries=true)
    public SuperHeroeDTO updateSuperHeroe(Long id, SuperHeroeDTO superHeroe) throws ResourceNotFoundException {
        SuperHeroe existingSuperHeroe = superHeroeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SUPER_HEROE, "id", id));
        existingSuperHeroe.setName(superHeroe.getName());
        existingSuperHeroe.setPowers(superHeroe.getPowers());
        existingSuperHeroe.setAlias(superHeroe.getAlias());
        return mapper.toDto(superHeroeRepository.save(existingSuperHeroe));
    }

    @Override
    @CacheEvict(cacheNames="superheroes", allEntries=true)
    public void deleteSuperHeroe(Long id) throws ResourceNotFoundException {
        SuperHeroe superHeroe = superHeroeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SUPER_HEROE, "id", id));
        superHeroeRepository.delete(superHeroe);
    }

	@Override
	public List<SuperHeroeDTO> findByNameContaining(String query) {
		log.info("findByNameContaining {}", query);
		return Optional.of(superHeroeRepository.findByNameContainingIgnoreCase(query.toLowerCase()))
	            .orElse(new ArrayList<>())
	            .stream()
	            .map(this.mapper::toDto)
	            .toList();
	}
	
	@CacheEvict(cacheNames="superheroes", allEntries=true)
	public void flushCache() {  
		log.info("flushCache");
	}
}
