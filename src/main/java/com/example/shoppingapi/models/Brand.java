package com.example.shoppingapi.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String name;
}
