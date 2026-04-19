package com.example.testdslexample.petclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.testdslexample.TestDslExampleApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestDslExampleApplication.class)
class PetClinicDslTest {

    @Test
    void givenOneWaitingPetWhenTreatedThenDatasetIsUpdated() {
        PetClinicScenario scenario = new PetClinicScenario(new ClinicDataset(), new JunitPetClinicDriver());

        scenario.givenWaitingPet("Alice", "Fido")
                .whenNextPetIsTreated()
                .thenWaitingListContains(0)
                .thenTreatedListContains(1)
                .thenLastTreatedPetIs("Alice", "Fido");
    }

    @Test
    void givenEmptyWaitingListWhenTreatedThenNoDatasetChange() {
        PetClinicScenario scenario = new PetClinicScenario(new ClinicDataset(), new JunitPetClinicDriver());

        scenario.whenNextPetIsTreated()
                .thenWaitingListContains(0)
                .thenTreatedListContains(0)
                .thenNoPetWasTreated();
    }

    private static final class JunitPetClinicDriver implements PetClinicTestDriver {

        @Override
        public void assertWaitingPets(ClinicDataset dataset, int expectedCount) {
            assertEquals(expectedCount, dataset.waitingPetsCount());
        }

        @Override
        public void assertTreatedPets(ClinicDataset dataset, int expectedCount) {
            assertEquals(expectedCount, dataset.treatedPetsCount());
        }

        @Override
        public void assertLastTreatedPet(ClinicDataset dataset, WaitingPet expectedPet) {
            assertEquals(expectedPet, dataset.lastTreatedPet().orElseThrow());
        }

        @Override
        public void assertNoTreatedPet(ClinicDataset dataset) {
            assertEquals(0, dataset.treatedPetsCount());
            assertEquals(java.util.Optional.empty(), dataset.lastTreatedPet());
        }
    }
}
