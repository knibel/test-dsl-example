package com.example.testdslexample.petclinic;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PetClinicService {

    private final ClinicDataset dataset = new ClinicDataset();

    public synchronized void checkInPet(String ownerName, String petName) {
        dataset.addWaitingPet(ownerName, petName);
    }

    public synchronized Optional<WaitingPet> treatNextPet() {
        return dataset.treatNextWaitingPet();
    }

    public synchronized ClinicSnapshot currentSnapshot() {
        return new ClinicSnapshot(
                dataset.waitingPets(),
                dataset.treatedPets(),
                dataset.lastTreatedPet().orElse(null)
        );
    }

    public synchronized void reset() {
        dataset.clear();
    }
}
