package com.example.shoppingapi.DTOs;

public record ReplaceItemRequestRecord(String name,
                                       Long price,
                                       Long calories,
                                       Long brandId){}
