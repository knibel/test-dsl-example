package com.example.testdslexample.petclinic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pet-clinic")
public class PetClinicController {

    private final PetClinicService petClinicService;

    public PetClinicController(PetClinicService petClinicService) {
        this.petClinicService = petClinicService;
    }

    @PostMapping("/waiting-pets")
    public ResponseEntity<Void> checkInPet(@RequestBody CheckInPetRequest request) {
        petClinicService.checkInPet(request.ownerName(), request.petName());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/treat-next")
    public ResponseEntity<WaitingPet> treatNextPet() {
        return petClinicService.treatNextPet()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public ClinicSnapshot clinicSnapshot() {
        return petClinicService.currentSnapshot();
    }
}
