package com.example.testdslexample.petclinic;

public interface PetClinicTestDriver {

    void assertWaitingPets(ClinicSnapshot clinicSnapshot, int expectedCount);

    void assertTreatedPets(ClinicSnapshot clinicSnapshot, int expectedCount);

    void assertLastTreatedPet(ClinicSnapshot clinicSnapshot, WaitingPet expectedPet);

    void assertNoTreatedPet(ClinicSnapshot clinicSnapshot);
}
