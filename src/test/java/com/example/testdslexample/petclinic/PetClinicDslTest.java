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
        PetClinicScenario scenario = new PetClinicScenario(new JunitPetClinicDriver(petClinicService));

        scenario.givenWaitingPet("Alice", "Fido")
                .whenNextPetIsTreated()
                .thenWaitingListContains(0)
                .thenTreatedListContains(1)
                .thenLastTreatedPetIs("Alice", "Fido");
    }

    @Test
    void givenEmptyWaitingListWhenTreatedThenNoDatasetChange() {
        PetClinicScenario scenario = new PetClinicScenario(new JunitPetClinicDriver(petClinicService));

        scenario.whenNextPetIsTreated()
                .thenWaitingListContains(0)
                .thenTreatedListContains(0)
                .thenNoPetWasTreated();
    }

    private static final class JunitPetClinicDriver implements PetClinicTestDriver {

        private final PetClinicService petClinicService;

        private JunitPetClinicDriver(PetClinicService petClinicService) {
            this.petClinicService = petClinicService;
        }

        @Override
        public void checkInPet(String ownerName, String petName) {
            petClinicService.checkInPet(ownerName, petName);
        }

        @Override
        public void treatNextPet() {
            petClinicService.treatNextPet();
        }

        @Override
        public void assertWaitingPets(int expectedCount) {
            assertEquals(expectedCount, petClinicService.currentSnapshot().waitingPets().size());
        }

        @Override
        public void assertTreatedPets(int expectedCount) {
            assertEquals(expectedCount, petClinicService.currentSnapshot().treatedPets().size());
        }

        @Override
        public void assertLastTreatedPet(WaitingPet expectedPet) {
            assertEquals(expectedPet, petClinicService.currentSnapshot().lastTreatedPet());
        }

        @Override
        public void assertNoTreatedPet() {
            assertEquals(0, petClinicService.currentSnapshot().treatedPets().size());
            assertNull(petClinicService.currentSnapshot().lastTreatedPet());
        }
    }
}
