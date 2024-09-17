package com.example.shoppingapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Item")
public class Item {
    public Item (String name, Long price, Long calories, Brand brand){
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.brand = brand;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    private Long calories;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Long getPrice() {
        return this.price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCalories() {
        return this.calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }


    public Long getId() {
        return this.id;
    }

    @ManyToOne
    Brand brand;

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}
