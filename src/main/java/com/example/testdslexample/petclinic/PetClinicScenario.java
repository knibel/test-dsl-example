package com.example.testdslexample.petclinic;

public class PetClinicScenario {

    private final PetClinicTestDriver driver;

    public PetClinicScenario(PetClinicTestDriver driver) {
        this.driver = driver;
    }

    public PetClinicScenario givenWaitingPet(String ownerName, String petName) {
        driver.checkInPet(ownerName, petName);
        return this;
    }

    public PetClinicScenario whenNextPetIsTreated() {
        driver.treatNextPet();
        return this;
    }

    public PetClinicScenario thenWaitingListContains(int expectedCount) {
        driver.assertWaitingPets(expectedCount);
        return this;
    }

    public PetClinicScenario thenTreatedListContains(int expectedCount) {
        driver.assertTreatedPets(expectedCount);
        return this;
    }

    public PetClinicScenario thenLastTreatedPetIs(String ownerName, String petName) {
        driver.assertLastTreatedPet(new WaitingPet(ownerName, petName));
        return this;
    }

    public PetClinicScenario thenNoPetWasTreated() {
        driver.assertNoTreatedPet();
        return this;
    }
}
