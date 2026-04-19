package com.example.testdslexample.petclinic;

public interface PetClinicTestDriver {

    void checkInPet(String ownerName, String petName);

    void treatNextPet();

    void assertWaitingPets(int expectedCount);

    void assertTreatedPets(int expectedCount);

    void assertLastTreatedPet(WaitingPet expectedPet);

    void assertNoTreatedPet();
}
