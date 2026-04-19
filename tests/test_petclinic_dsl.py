import unittest

from petclinic.domain import ClinicDataset, WaitingPet
from petclinic.testdsl import PetClinicScenario, PetClinicTestDriver


class UnittestPetClinicDriver(PetClinicTestDriver):
    def __init__(self, test_case: unittest.TestCase) -> None:
        self.test_case = test_case

    def assert_waiting_pets(self, dataset: ClinicDataset, expected_count: int) -> None:
        self.test_case.assertEqual(expected_count, len(dataset.waiting_pets))

    def assert_treated_pets(self, dataset: ClinicDataset, expected_count: int) -> None:
        self.test_case.assertEqual(expected_count, len(dataset.treated_pets))

    def assert_last_treated_pet(self, dataset: ClinicDataset, expected: WaitingPet | None) -> None:
        if expected is None:
            self.test_case.assertEqual([], dataset.treated_pets)
            return
        self.test_case.assertEqual(expected, dataset.treated_pets[-1])


class PetClinicDslTest(unittest.TestCase):
    def test_given_one_waiting_pet_when_treated_then_dataset_is_updated(self) -> None:
        PetClinicScenario(
            dataset=ClinicDataset(),
            driver=UnittestPetClinicDriver(self),
        ).given_a_waiting_pet(
            owner_name="Alice", pet_name="Fido"
        ).when_the_next_pet_is_treated().then_waiting_list_contains(
            expected_count=0
        ).then_treated_list_contains(
            expected_count=1
        ).then_last_treated_pet_is(
            owner_name="Alice", pet_name="Fido"
        )

    def test_given_empty_waiting_list_when_treated_then_no_dataset_change(self) -> None:
        PetClinicScenario(
            dataset=ClinicDataset(),
            driver=UnittestPetClinicDriver(self),
        ).when_the_next_pet_is_treated().then_waiting_list_contains(
            expected_count=0
        ).then_treated_list_contains(
            expected_count=0
        ).then_no_pet_was_treated()


if __name__ == "__main__":
    unittest.main()
