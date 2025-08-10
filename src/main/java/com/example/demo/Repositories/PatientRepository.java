package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.Patient;


@Repository
public interface PatientRepository extends JpaRepository<Patient , Integer> {
		boolean existsByEmail(String email);		// so email should be unique
		
		@EntityGraph(attributePaths = {"appointments"}) // appointments is the var name in Patient 
		Optional<Patient> findByEmailAndName(String email , String name); // return can be Patient withput Optional but this avoids null exceptions
		
		Optional<Patient> findByEmail(String email);
}
