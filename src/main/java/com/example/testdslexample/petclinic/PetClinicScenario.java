package com.example.testdslexample.petclinic;

public class PetClinicScenario {

    private final PetClinicService petClinicService;
    private final PetClinicTestDriver driver;

    public PetClinicScenario(PetClinicService petClinicService, PetClinicTestDriver driver) {
        this.petClinicService = petClinicService;
        this.driver = driver;
    }

    public PetClinicScenario givenWaitingPet(String ownerName, String petName) {
        petClinicService.checkInPet(ownerName, petName);
        return this;
    }

    public PetClinicScenario whenNextPetIsTreated() {
        petClinicService.treatNextPet();
        return this;
    }

    public PetClinicScenario thenWaitingListContains(int expectedCount) {
        driver.assertWaitingPets(petClinicService.currentSnapshot(), expectedCount);
        return this;
    }

    public PetClinicScenario thenTreatedListContains(int expectedCount) {
        driver.assertTreatedPets(petClinicService.currentSnapshot(), expectedCount);
        return this;
    }

    public PetClinicScenario thenLastTreatedPetIs(String ownerName, String petName) {
        driver.assertLastTreatedPet(petClinicService.currentSnapshot(), new WaitingPet(ownerName, petName));
        return this;
    }

    public PetClinicScenario thenNoPetWasTreated() {
        driver.assertNoTreatedPet(petClinicService.currentSnapshot());
        return this;
    }
}
