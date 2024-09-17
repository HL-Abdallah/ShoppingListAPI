package com.example.shoppingapi.controllers.Brand;

import com.example.shoppingapi.ViewModels.CreateBrandRequestViewModel;
import com.example.shoppingapi.ViewModels.ReplaceBrandRequestViewModel;
import com.example.shoppingapi.models.Brand;
import com.example.shoppingapi.repositories.BrandRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("API/v1/Brand")
@RestController
public class BrandController {

    private final BrandRepository repository;

    BrandController(BrandRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/all")
    public List<Brand> all() {
        return repository.findAll();
    }

    @PostMapping("/create")
    public Brand create(@RequestBody CreateBrandRequestViewModel model){

        var newBrand = new Brand(model.name);
        return repository.save(newBrand);
    }

    @GetMapping("/{id}")
    Brand one(@PathVariable Long id) {

        var exceptionMessage = String.format(""" 
                Brand with id:%s not found
                """, id);
        return repository.findById(id).orElseThrow(() -> new RuntimeException(exceptionMessage));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    Brand replace(@RequestBody ReplaceBrandRequestViewModel newBrand, @PathVariable Long id) {

        var exceptionMessage = String.format(""" 
                Brand with id:%s not found
                """, id);

        var dbBrand = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(exceptionMessage));

        dbBrand.setName(newBrand.name);
        return repository.save(dbBrand);
    }
}
