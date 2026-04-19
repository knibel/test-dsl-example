package com.example.testdslexample.petclinic;

public class PetClinicScenario {

    private final ClinicDataset dataset;
    private final PetClinicTestDriver driver;

    public PetClinicScenario(ClinicDataset dataset, PetClinicTestDriver driver) {
        this.dataset = dataset;
        this.driver = driver;
    }

    public PetClinicScenario givenWaitingPet(String ownerName, String petName) {
        dataset.addWaitingPet(ownerName, petName);
        return this;
    }

    public PetClinicScenario whenNextPetIsTreated() {
        dataset.treatNextWaitingPet();
        return this;
    }

    public PetClinicScenario thenWaitingListContains(int expectedCount) {
        driver.assertWaitingPets(dataset, expectedCount);
        return this;
    }

    public PetClinicScenario thenTreatedListContains(int expectedCount) {
        driver.assertTreatedPets(dataset, expectedCount);
        return this;
    }

    public PetClinicScenario thenLastTreatedPetIs(String ownerName, String petName) {
        driver.assertLastTreatedPet(dataset, new WaitingPet(ownerName, petName));
        return this;
    }

    public PetClinicScenario thenNoPetWasTreated() {
        driver.assertNoTreatedPet(dataset);
        return this;
    }
}
