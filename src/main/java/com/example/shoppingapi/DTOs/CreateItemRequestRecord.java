package com.example.shoppingapi.DTOs;

public record CreateItemRequestRecord(String name,
                                      Long price,
                                      Long calories,
                                      Long brandId){}
