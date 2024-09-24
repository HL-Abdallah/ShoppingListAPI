package com.example.shoppingapi.controllers.Brand;

import com.example.shoppingapi.DTOs.CreateItemRequestRecord;
import com.example.shoppingapi.DTOs.ReplaceItemRequestRecord;
import com.example.shoppingapi.models.Brand;
import com.example.shoppingapi.models.Item;
import com.example.shoppingapi.models.Person;
import com.example.shoppingapi.repositories.BrandRepository;
import com.example.shoppingapi.repositories.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("API/v1/Item")
@RestController
public class ItemController {

    private final ItemRepository repository;
    private final BrandRepository brandRepository;

    ItemController(ItemRepository repository, BrandRepository brandRepository) {
        this.repository = repository;
        this.brandRepository = brandRepository;
    }


    @GetMapping("")
    public List<Item> all() {
        return repository.findAll();
    }

    @GetMapping("/page")
    public Page<Item> findPaginated(@RequestParam("page") int page , @RequestParam("size") int size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @PostMapping("")
    public ResponseEntity<Item> create(@RequestBody CreateItemRequestRecord model){

        try {
            var brand = brandRepository.findById(model.brandId())
                    .orElseThrow();

            var newItem = new Item();

            newItem.name = model.name();
            newItem.brand = brand;
            newItem.price = model.price();
            newItem.calories = model.calories();

            var savedItem = repository.save(newItem);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedItem.id)
                    .toUri();

            return ResponseEntity.created(location).body(savedItem);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Item>> one(@PathVariable Long id) {

        var item = repository.findById(id);

        if (item.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Item> existingItem = repository.findById(id);
        if(existingItem.isPresent()){
            repository.delete(existingItem.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Item> replace(@RequestBody ReplaceItemRequestRecord newItem, @PathVariable Long id) {

        try {
            var brand = brandRepository.findById(newItem.brandId())
                    .orElseThrow();

            var dbItem = repository.findById(id).orElseThrow();
            dbItem.name = newItem.name();
            dbItem.brand = brand;
            dbItem.price = newItem.price();
            dbItem.calories = newItem.calories();

            return ResponseEntity.ok(repository.save(dbItem));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
