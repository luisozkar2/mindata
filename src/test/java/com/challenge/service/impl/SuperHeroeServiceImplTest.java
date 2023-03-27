package com.challenge.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.challenge.dto.SuperHeroeDTO;
import com.challenge.exceptions.ResourceNotFoundException;
import com.challenge.mapper.SuperHeroeMapper;
import com.challenge.model.SuperHeroe;
import com.challenge.repo.SuperHeroeRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SuperHeroeServiceImplTest {

	private SuperHeroeServiceImpl superHeroeService;
	@Mock
	private SuperHeroeRepository superHeroeRepository;
	@Mock
	private SuperHeroeMapper superHeroeMapper;
	
    @BeforeAll
    void setUp() {
    	this.superHeroeRepository = Mockito.mock(SuperHeroeRepository.class);
    	this.superHeroeMapper = Mockito.mock(SuperHeroeMapper.class);
    	this.superHeroeService = new SuperHeroeServiceImpl(superHeroeRepository, superHeroeMapper);
    }

	@Test
	void testGetAllSuperHeroes() {
		List<SuperHeroe> superHeroes = Arrays.asList(new SuperHeroe(1L, "Superman", "Super fuerza", "Clark Kent"),
				new SuperHeroe(2L, "Batman", "Inteligencia y habilidades de detective", "Bruce Wayne"));
		List<SuperHeroeDTO> expected = Arrays.asList(new SuperHeroeDTO(1L, "Superman", "Super fuerza", "Clark Kent"),
				new SuperHeroeDTO(2L, "Batman", "Inteligencia y habilidades de detective", "Bruce Wayne"));

		when(superHeroeRepository.findAll()).thenReturn(superHeroes);
		when(superHeroeMapper.toDto(any(SuperHeroe.class))).thenAnswer(invocation -> {
			SuperHeroe superHeroe = invocation.getArgument(0);
			return new SuperHeroeDTO(superHeroe.getId(), superHeroe.getName(), superHeroe.getPowers(),
					superHeroe.getAlias());
		});
		List<SuperHeroeDTO> result = superHeroeService.getAllSuperHeroes();

		assertEquals(expected, result);
	}

	@Test
	void testGetSuperHeroeById() throws ResourceNotFoundException {
		Long id = 1L;
		SuperHeroe superHeroe = new SuperHeroe(id, "Superman", "Super fuerza", "Clark Kent");
		SuperHeroeDTO expected = new SuperHeroeDTO(id, "Superman", "Super fuerza", "Clark Kent");

		when(superHeroeRepository.findById(id)).thenReturn(Optional.of(superHeroe));
		when(superHeroeMapper.toDto(any(SuperHeroe.class))).thenReturn(expected);

		SuperHeroeDTO result = superHeroeService.getSuperHeroeById(id);

		assertEquals(expected, result);
	}

	@Test
	void testCreateSuperHeroe() {
		SuperHeroeDTO superHeroeDTO = new SuperHeroeDTO(null, "Iron Man", "Tecnología avanzada", "Tony Stark");
		SuperHeroe superHeroe = new SuperHeroe(1L, "Iron Man", "Tecnología avanzada", "Tony Stark");		

        when(superHeroeMapper.toEntity(superHeroeDTO)).thenReturn(superHeroe);
        when(superHeroeMapper.toDto(superHeroe)).thenReturn(superHeroeDTO);
        when(superHeroeRepository.save(superHeroe)).thenReturn(superHeroe);

        SuperHeroeDTO createdSuperHeroe = superHeroeService.createSuperHeroe(superHeroeDTO);

        assertEquals(superHeroeDTO.getName(), createdSuperHeroe.getName());
	}
	
    @Test
    void testFindByNameContaining() {
        String query = "man";
        SuperHeroe superHeroe1 = SuperHeroe.builder()
        		.id(1L)
        		.name("Superman")
        		.powers("Super fuerza")
        		.alias("Clark Kent")
        		.build();
        SuperHeroe superHeroe2 = SuperHeroe.builder()
        		.id(2L)
        		.name("Iron Man")
        		.powers("Energy repulsors")
        		.alias("Tony Stark")
        		.build();
        List<SuperHeroe> superHeroes = Arrays.asList(superHeroe1, superHeroe2);
        
        SuperHeroeDTO superHeroeDTO1 = SuperHeroeDTO.builder()
        		.id(1L)
        		.name("Superman")
        		.powers("Super fuerza")
        		.alias("Clark Kent")
        		.build();
        SuperHeroeDTO superHeroeDTO2 = SuperHeroeDTO.builder()
        		.id(2L)
        		.name("Iron Man")
        		.powers("Energy repulsors")
        		.alias("Tony Stark")
        		.build();
        List<SuperHeroeDTO> expectedSuperHeroes = Arrays.asList(superHeroeDTO1, superHeroeDTO2);


        when(superHeroeRepository.findByNameContainingIgnoreCase(query)).thenReturn(superHeroes);
        when(superHeroeMapper.toDto(superHeroe1)).thenReturn(superHeroeDTO1);
        when(superHeroeMapper.toDto(superHeroe2)).thenReturn(superHeroeDTO2);
        
        List<SuperHeroeDTO> result = superHeroeService.findByNameContaining(query);

        assertEquals(expectedSuperHeroes, result);
    }
}
