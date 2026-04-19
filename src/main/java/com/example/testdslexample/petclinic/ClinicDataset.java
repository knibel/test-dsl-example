package com.example.testdslexample.petclinic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class ClinicDataset {

    private final Deque<WaitingPet> waitingPets = new ArrayDeque<>();
    private final List<WaitingPet> treatedPets = new ArrayList<>();

    public void addWaitingPet(String ownerName, String petName) {
        waitingPets.addLast(new WaitingPet(ownerName, petName));
    }

    public Optional<WaitingPet> treatNextWaitingPet() {
        if (waitingPets.isEmpty()) {
            return Optional.empty();
        }

        WaitingPet treatedPet = waitingPets.removeFirst();
        treatedPets.add(treatedPet);
        return Optional.of(treatedPet);
    }

    public int waitingPetsCount() {
        return waitingPets.size();
    }

    public int treatedPetsCount() {
        return treatedPets.size();
    }

    public Optional<WaitingPet> lastTreatedPet() {
        if (treatedPets.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(treatedPets.get(treatedPets.size() - 1));
    }
}
