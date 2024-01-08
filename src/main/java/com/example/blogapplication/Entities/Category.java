package com.example.blogapplication.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer categoryId;

    @Column(name = "title",length = 100,nullable = false)
    public String categoryTitle;
    @Column(name = "Description")
    public String categoryDescription;
}
