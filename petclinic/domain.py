from dataclasses import dataclass


@dataclass(frozen=True)
class WaitingPet:
    owner_name: str
    pet_name: str


class ClinicDataset:
    def __init__(self) -> None:
        self.waiting_pets: list[WaitingPet] = []
        self.treated_pets: list[WaitingPet] = []

    def add_waiting_pet(self, owner_name: str, pet_name: str) -> None:
        self.waiting_pets.append(WaitingPet(owner_name=owner_name, pet_name=pet_name))

    def treat_next_waiting_pet(self) -> WaitingPet | None:
        if not self.waiting_pets:
            return None
        treated_pet = self.waiting_pets.pop(0)
        self.treated_pets.append(treated_pet)
        return treated_pet

