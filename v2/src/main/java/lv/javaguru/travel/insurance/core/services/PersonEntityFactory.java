package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.entities.PersonEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.PersonEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PersonEntityFactory {

    @Autowired
    private PersonEntityRepository repository;

    PersonEntity createPersonEntity(PersonDTO person) {
        Optional<PersonEntity> personOpt = repository.findBy(
                person.getPersonFirstName(),
                person.getPersonLastName(),
                person.getPersonalCode());
        if (personOpt.isPresent()) {
            return personOpt.get();
        } else {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setFirstName(person.getPersonFirstName());
            personEntity.setLastName(person.getPersonLastName());
            personEntity.setBirthDate(person.getPersonBirthDate());
            personEntity.setPersonalCode(person.getPersonalCode());
            return repository.save(personEntity);
        }
    }
}
