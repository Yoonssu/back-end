package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.pet.PetDTO;
import com.aeon.hadog.base.exception.DuplicateIdException;
import com.aeon.hadog.base.exception.PetIdNotFoundException;
import com.aeon.hadog.domain.Pet;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.PetRepository;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public Long registerPet(String userId, PetDTO petDTO) {

        Pet pet = Pet.builder()
                .name(petDTO.getName())
                .breed(petDTO.getBreed())
                .sex(petDTO.getSex())
                .neuter(petDTO.getNeuter())
                .image(petDTO.getImage())
                .age(petDTO.getAge())
                .feature(petDTO.getFeature())
                .user(userRepository.findById(userId).get())
                .build();

        petRepository.save(pet);

        return pet.getPetId();
    }

    public Long updatePet(Long petId, PetDTO petDTO) {

        Optional<Pet> optionalPet = petRepository.findById(petId);

        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();

            pet.setName(petDTO.getName());
            pet.setBreed(petDTO.getBreed());
            pet.setSex(petDTO.getSex());
            pet.setNeuter(petDTO.getNeuter());
            pet.setImage(petDTO.getImage());
            pet.setAge(petDTO.getAge());
            pet.setFeature(petDTO.getFeature());

            petRepository.save(pet);

            return pet.getPetId();

        } else {
            throw new PetIdNotFoundException(ErrorCode.PET_NOT_FOUND);
        }

    }

    public PetDTO viewPet(Long petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);

        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            PetDTO petDTO = PetDTO.builder()
                    .name(pet.getName())
                    .breed(pet.getBreed())
                    .sex(pet.getSex())
                    .neuter(pet.getNeuter())
                    .image(pet.getImage())
                    .age(pet.getAge())
                    .feature(pet.getFeature())
                    .build();

            return petDTO;
        } else {
            throw new PetIdNotFoundException(ErrorCode.PET_NOT_FOUND);
        }

    }
}
