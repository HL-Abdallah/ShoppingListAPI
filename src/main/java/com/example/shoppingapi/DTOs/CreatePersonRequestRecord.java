package com.example.shoppingapi.DTOs;

import java.time.LocalDate;

public record CreatePersonRequestRecord(String firstName, String lastName, LocalDate dateOfBirth, String email){ }
