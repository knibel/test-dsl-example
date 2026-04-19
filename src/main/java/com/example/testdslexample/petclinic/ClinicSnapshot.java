package com.example.testdslexample.petclinic;

import java.util.List;

public record ClinicSnapshot(
        List<WaitingPet> waitingPets,
        List<WaitingPet> treatedPets,
        WaitingPet lastTreatedPet
) {
}
