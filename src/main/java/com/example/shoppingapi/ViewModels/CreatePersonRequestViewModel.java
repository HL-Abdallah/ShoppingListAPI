package com.example.shoppingapi.ViewModels;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class CreatePersonRequestViewModel {
    public String firstName;
    public String lastName;
    public LocalDate dateOfBirth;
    public String email;
}
