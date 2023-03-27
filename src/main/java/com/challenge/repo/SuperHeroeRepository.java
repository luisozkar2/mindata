package com.challenge.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.model.SuperHeroe;

@Repository
public interface SuperHeroeRepository extends JpaRepository<SuperHeroe, Long> {
	
	@Query("SELECT s FROM SuperHeroe s WHERE LOWER(s.name) LIKE %:name%")
	List<SuperHeroe> findByNameContainingIgnoreCase(@Param("name") String name);
	
}