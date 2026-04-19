package com.example.testdslexample.petclinic;

public interface PetClinicTestDriver {

    void assertWaitingPets(ClinicDataset dataset, int expectedCount);

    void assertTreatedPets(ClinicDataset dataset, int expectedCount);

    void assertLastTreatedPet(ClinicDataset dataset, WaitingPet expectedPet);

    void assertNoTreatedPet(ClinicDataset dataset);
}
