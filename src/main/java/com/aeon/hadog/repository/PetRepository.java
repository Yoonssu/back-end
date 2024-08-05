package com.aeon.hadog.repository;

import com.aeon.hadog.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByPetId(Long Id);

    List<Pet> findByUserId(String userId);
}
