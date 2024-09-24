package com.example.shoppingapi.controllers.Brand;

import com.example.shoppingapi.DTOs.CreateBrandRequestRecord;
import com.example.shoppingapi.DTOs.ReplaceBrandRequestRecord;
import com.example.shoppingapi.models.Brand;
import com.example.shoppingapi.models.Item;
import com.example.shoppingapi.repositories.BrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/brand")
@RestController
public class BrandController {

    private final BrandRepository repository;

    BrandController(BrandRepository repository) {
        this.repository = repository;
    }


    @GetMapping()
    ResponseEntity<List<Brand>> all() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/page")
    ResponseEntity<Page<Brand>> findPaginated(@RequestParam("page") int page , @RequestParam("size") int size) {
        return ResponseEntity.ok(repository.findAll(PageRequest.of(page,size)));
    }

    //replace class with record
    @PostMapping()
    ResponseEntity<Brand> create(@RequestBody CreateBrandRequestRecord model){

        var newBrand = new Brand();
        newBrand.name = model.name();
        var savedBrand = repository.save(newBrand);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBrand.id)
                .toUri();

        return ResponseEntity.created(location).body(savedBrand);
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Brand>> one(@PathVariable Long id) {
        // remove exception
        // voire controller advice
        var brand = repository.findById(id);

        if (brand.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Brand> existingBrand = repository.findById(id);
        if(existingBrand.isPresent()){
            repository.delete(existingBrand.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Brand> replace(@RequestBody ReplaceBrandRequestRecord newBrand, @PathVariable Long id) {
        //see about (map struct, model mapper)
        try {
            var dbBrand = repository.findById(id).orElseThrow();
            dbBrand.name = newBrand.name();
            return ResponseEntity.ok(repository.save(dbBrand));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
