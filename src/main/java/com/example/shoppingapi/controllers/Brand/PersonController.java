package com.example.shoppingapi.controllers.Brand;

import com.example.shoppingapi.DTOs.CreatePersonRequestRecord;
import com.example.shoppingapi.DTOs.ReplacePersonRequestRecord;
import com.example.shoppingapi.models.Person;
import com.example.shoppingapi.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("API/v1/Person")
@RestController
public class PersonController {
    private final PersonRepository repository ;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<Person> all() {
        return repository.findAll();
    }

    @GetMapping("/page")
    public Page<Person> findPaginated(@RequestParam("page") int page , @RequestParam("size") int size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @PostMapping("")
    public ResponseEntity<Person> create(@RequestBody CreatePersonRequestRecord model) {

        var newPerson = new Person();

        newPerson.email = model.email();
        newPerson.dateOfBirth = model.dateOfBirth();
        newPerson.firstName = model.firstName();
        newPerson.lastName = model.lastName();

        var savedPerson = repository.save(newPerson);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPerson.id)
                .toUri();

        return ResponseEntity.created(location).body(savedPerson);
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Person>> one(@PathVariable Long id) {

        var person = repository.findById(id);

        if (person.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<Person> existingPerson = repository.findById(id);
        if(existingPerson.isPresent()){
            repository.delete(existingPerson.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Person> replace(@RequestBody ReplacePersonRequestRecord newPerson, @PathVariable Long id) {

        try {
            var dbPerson = repository.findById(id).orElseThrow();
            dbPerson.email = newPerson.email();
            dbPerson.dateOfBirth = newPerson.dateOfBirth();
            dbPerson.firstName = newPerson.firstName();
            dbPerson.lastName = newPerson.lastName();

            return ResponseEntity.ok(repository.save(dbPerson));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
