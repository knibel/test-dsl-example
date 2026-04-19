package com.example.testdslexample.petclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.testdslexample.TestDslExampleApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(
        classes = TestDslExampleApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PetClinicControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PetClinicService petClinicService;

    @BeforeEach
    void resetClinic() {
        petClinicService.reset();
    }

    @Test
    void checkInTreatAndInspectClinicOverHttp() {
        PetClinicScenario scenario = new PetClinicScenario(new HttpPetClinicDriver());

        scenario.givenWaitingPet("Alice", "Fido")
                .whenNextPetIsTreated()
                .thenWaitingListContains(0)
                .thenTreatedListContains(1)
                .thenLastTreatedPetIs("Alice", "Fido");
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private final class HttpPetClinicDriver implements PetClinicTestDriver {

        @Override
        public void checkInPet(String ownerName, String petName) {
            ResponseEntity<Void> response = restTemplate.postForEntity(
                    url("/api/pet-clinic/waiting-pets"),
                    new CheckInPetRequest(ownerName, petName),
                    Void.class
            );

            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        }

        @Override
        public void treatNextPet() {
            ResponseEntity<WaitingPet> response = restTemplate.postForEntity(
                    url("/api/pet-clinic/treat-next"),
                    null,
                    WaitingPet.class
            );

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Override
        public void assertWaitingPets(int expectedCount) {
            assertEquals(expectedCount, snapshot().waitingPets().size());
        }

        @Override
        public void assertTreatedPets(int expectedCount) {
            assertEquals(expectedCount, snapshot().treatedPets().size());
        }

        @Override
        public void assertLastTreatedPet(WaitingPet expectedPet) {
            assertEquals(expectedPet, snapshot().lastTreatedPet());
        }

        @Override
        public void assertNoTreatedPet() {
            assertEquals(0, snapshot().treatedPets().size());
        }

        private ClinicSnapshot snapshot() {
            ResponseEntity<ClinicSnapshot> response = restTemplate.getForEntity(
                    url("/api/pet-clinic"),
                    ClinicSnapshot.class
            );

            assertEquals(HttpStatus.OK, response.getStatusCode());
            return response.getBody();
        }
    }
}
