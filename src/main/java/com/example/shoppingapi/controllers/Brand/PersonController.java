package com.example.shoppingapi.controllers.Brand;

import com.example.shoppingapi.ViewModels.CreatePersonRequestViewModel;
import com.example.shoppingapi.ViewModels.ReplacePersonRequestViewModel;
import com.example.shoppingapi.models.Person;
import com.example.shoppingapi.repositories.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("API/v1/Person")
@RestController
public class PersonController {
    private final PersonRepository repository ;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<Person> all() {
        return repository.findAll();
    }

    @PostMapping("/create")
    public Person create(@RequestBody CreatePersonRequestViewModel model) {

        var newPerson = new Person(model.firstName, model.lastName, model.dateOfBirth, model.email);
        return repository.save(newPerson);
    }

    @GetMapping("/{id}")
    Person one(@PathVariable Long id) {

        var exceptionMessage = String.format(""" 
                Person with id:%s not found
                """, id);
        return repository.findById(id).orElseThrow(() -> new RuntimeException(exceptionMessage));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    Person replace(@RequestBody ReplacePersonRequestViewModel newPerson, @PathVariable Long id) {

        var exceptionMessage = String.format(""" 
                Person with id:%s not found
                """, id);

        var dbPerson = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(exceptionMessage));

        dbPerson.setEmail(newPerson.email);
        dbPerson.setDateOfBirth(newPerson.dateOfBirth);
        dbPerson.setFirstName(newPerson.firstName);
        dbPerson.setLastName(newPerson.lastName);

        return repository.save(dbPerson);
    }
}
