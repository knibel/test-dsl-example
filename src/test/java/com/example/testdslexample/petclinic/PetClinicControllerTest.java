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
        ResponseEntity<Void> checkInResponse = restTemplate.postForEntity(
                url("/api/pet-clinic/waiting-pets"),
                new CheckInPetRequest("Alice", "Fido"),
                Void.class
        );

        ResponseEntity<WaitingPet> treatResponse = restTemplate.postForEntity(
                url("/api/pet-clinic/treat-next"),
                null,
                WaitingPet.class
        );

        ResponseEntity<ClinicSnapshot> snapshotResponse = restTemplate.getForEntity(
                url("/api/pet-clinic"),
                ClinicSnapshot.class
        );

        assertEquals(HttpStatus.ACCEPTED, checkInResponse.getStatusCode());
        assertEquals(HttpStatus.OK, treatResponse.getStatusCode());
        assertEquals(new WaitingPet("Alice", "Fido"), treatResponse.getBody());
        assertEquals(HttpStatus.OK, snapshotResponse.getStatusCode());
        assertEquals(0, snapshotResponse.getBody().waitingPets().size());
        assertEquals(1, snapshotResponse.getBody().treatedPets().size());
        assertEquals(new WaitingPet("Alice", "Fido"), snapshotResponse.getBody().lastTreatedPet());
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}
