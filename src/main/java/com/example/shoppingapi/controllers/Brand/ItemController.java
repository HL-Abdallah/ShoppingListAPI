package com.example.shoppingapi.controllers.Brand;

import com.example.shoppingapi.ViewModels.CreateBrandRequestViewModel;
import com.example.shoppingapi.ViewModels.CreateItemRequestViewModel;
import com.example.shoppingapi.ViewModels.ReplaceBrandRequestViewModel;
import com.example.shoppingapi.ViewModels.ReplaceItemRequestViewModel;
import com.example.shoppingapi.models.Brand;
import com.example.shoppingapi.models.Item;
import com.example.shoppingapi.repositories.BrandRepository;
import com.example.shoppingapi.repositories.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("API/v1/Item")
@RestController
public class ItemController {

    private final ItemRepository repository;
    private final BrandRepository brandRepository;

    ItemController(ItemRepository repository, BrandRepository brandRepository) {
        this.repository = repository;
        this.brandRepository = brandRepository;
    }


    @GetMapping("/all")
    public List<Item> all() {
        return repository.findAll();
    }

    @PostMapping("/create")
    public Item create(@RequestBody CreateItemRequestViewModel model){

        var exceptionMessage = String.format(""" 
                Brand with id:%s not found
                """, model.brandId);

        var brand = brandRepository.findById(model.brandId)
                .orElseThrow(() -> new RuntimeException(exceptionMessage));

        var newItem = new Item(model.name, model.price, model.calories, brand);
        return repository.save(newItem);
    }

    @GetMapping("/{id}")
    Item one(@PathVariable Long id) {

        var exceptionMessage = String.format(""" 
                Item with id:%s not found
                """, id);
        return repository.findById(id).orElseThrow(() -> new RuntimeException(exceptionMessage));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    Item replace(@RequestBody ReplaceItemRequestViewModel newItem, @PathVariable Long id) {

        var exceptionMessage = String.format(""" 
                Item with id:%s not found
                """, id);
        var brandExceptionMessage = String.format(""" 
                Brand with id:%s not found
                """, newItem.brandId);

        var brand = brandRepository.findById(newItem.brandId)
                .orElseThrow(() -> new RuntimeException(brandExceptionMessage));

        var dbItem = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(exceptionMessage));

        dbItem.setName(newItem.name);
        dbItem.setBrand(brand);
        dbItem.setPrice(newItem.price);
        dbItem.setCalories(newItem.calories);

        return repository.save(dbItem);
    }
}
