package com.productmanagement.model;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private String description;
    private String farmer;
    private String image1;
    private String image2;
    private String image3;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor, getters, and setters
    // ...

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", farmer='" + farmer + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
