from dataclasses import dataclass

from petclinic.domain import ClinicDataset, WaitingPet


class PetClinicTestDriver:
    def assert_waiting_pets(self, dataset: ClinicDataset, expected_count: int) -> None:
        raise NotImplementedError

    def assert_treated_pets(self, dataset: ClinicDataset, expected_count: int) -> None:
        raise NotImplementedError

    def assert_last_treated_pet(self, dataset: ClinicDataset, expected: WaitingPet | None) -> None:
        raise NotImplementedError


@dataclass
class PetClinicScenario:
    dataset: ClinicDataset
    driver: PetClinicTestDriver

    def given_a_waiting_pet(self, owner_name: str, pet_name: str) -> "PetClinicScenario":
        self.dataset.add_waiting_pet(owner_name=owner_name, pet_name=pet_name)
        return self

    def when_the_next_pet_is_treated(self) -> "PetClinicScenario":
        self.dataset.treat_next_waiting_pet()
        return self

    def then_waiting_list_contains(self, expected_count: int) -> "PetClinicScenario":
        self.driver.assert_waiting_pets(self.dataset, expected_count=expected_count)
        return self

    def then_treated_list_contains(self, expected_count: int) -> "PetClinicScenario":
        self.driver.assert_treated_pets(self.dataset, expected_count=expected_count)
        return self

    def then_last_treated_pet_is(self, owner_name: str, pet_name: str) -> "PetClinicScenario":
        self.driver.assert_last_treated_pet(
            self.dataset,
            expected=WaitingPet(owner_name=owner_name, pet_name=pet_name),
        )
        return self

    def then_no_pet_was_treated(self) -> "PetClinicScenario":
        self.driver.assert_last_treated_pet(self.dataset, expected=None)
        return self

