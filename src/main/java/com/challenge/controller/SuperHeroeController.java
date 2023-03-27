package com.challenge.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.aspect.TiempoEjecucion;
import com.challenge.dto.SuperHeroeDTO;
import com.challenge.service.SuperHeroeService;

import io.swagger.annotations.Api;

@Api(tags = "Challenge")
@RestController
@RequestMapping("/api/superheroes")
public class SuperHeroeController {

    @Autowired
    private SuperHeroeService superHeroeService;

    @GetMapping
    @TiempoEjecucion
    public List<SuperHeroeDTO> getAllSuperHeroes() {
        return superHeroeService.getAllSuperHeroes();
    }
    
    @GetMapping("/findByNameContaining")
    @TiempoEjecucion
    public List<SuperHeroeDTO> consultarSuperHeroes(@RequestParam String query) {
        return superHeroeService.findByNameContaining(query);
    }

    @GetMapping("/{id}")
    @TiempoEjecucion
    public SuperHeroeDTO getSuperHeroeById(@PathVariable Long id){
		return superHeroeService.getSuperHeroeById(id);
    }

    @PostMapping
    public SuperHeroeDTO createSuperHeroe(@RequestBody SuperHeroeDTO superHeroe) {
        return superHeroeService.createSuperHeroe(superHeroe);
    }

    @PutMapping("/{id}")
    public SuperHeroeDTO updateSuperHeroe(@PathVariable Long id, @RequestBody SuperHeroeDTO superHeroe) {
        return superHeroeService.updateSuperHeroe(id, superHeroe);
    }

    @DeleteMapping("/{id}")
    public void deleteSuperHeroe(@PathVariable Long id) {
        superHeroeService.deleteSuperHeroe(id);
    }
    
    @GetMapping("/flushCache")
    public boolean flushCache() {
        superHeroeService.flushCache();
        return true;
    }
}