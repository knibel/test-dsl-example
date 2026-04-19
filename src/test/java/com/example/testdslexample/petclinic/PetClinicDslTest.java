package com.example.testdslexample.petclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.testdslexample.TestDslExampleApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestDslExampleApplication.class)
class PetClinicDslTest {

    @Autowired
    private PetClinicService petClinicService;

    @BeforeEach
    void resetClinic() {
        petClinicService.reset();
    }

    @Test
    void givenOneWaitingPetWhenTreatedThenDatasetIsUpdated() {
        PetClinicScenario scenario = new PetClinicScenario(petClinicService, new JunitPetClinicDriver());

        scenario.givenWaitingPet("Alice", "Fido")
                .whenNextPetIsTreated()
                .thenWaitingListContains(0)
                .thenTreatedListContains(1)
                .thenLastTreatedPetIs("Alice", "Fido");
    }

    @Test
    void givenEmptyWaitingListWhenTreatedThenNoDatasetChange() {
        PetClinicScenario scenario = new PetClinicScenario(petClinicService, new JunitPetClinicDriver());

        scenario.whenNextPetIsTreated()
                .thenWaitingListContains(0)
                .thenTreatedListContains(0)
                .thenNoPetWasTreated();
    }

    private static final class JunitPetClinicDriver implements PetClinicTestDriver {

        @Override
        public void assertWaitingPets(ClinicSnapshot clinicSnapshot, int expectedCount) {
            assertEquals(expectedCount, clinicSnapshot.waitingPets().size());
        }

        @Override
        public void assertTreatedPets(ClinicSnapshot clinicSnapshot, int expectedCount) {
            assertEquals(expectedCount, clinicSnapshot.treatedPets().size());
        }

        @Override
        public void assertLastTreatedPet(ClinicSnapshot clinicSnapshot, WaitingPet expectedPet) {
            assertEquals(expectedPet, clinicSnapshot.lastTreatedPet());
        }

        @Override
        public void assertNoTreatedPet(ClinicSnapshot clinicSnapshot) {
            assertEquals(0, clinicSnapshot.treatedPets().size());
            assertNull(clinicSnapshot.lastTreatedPet());
        }
    }
}
