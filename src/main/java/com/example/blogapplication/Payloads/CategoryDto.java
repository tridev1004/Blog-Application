package com.example.blogapplication.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    @NotBlank
    @Size(min = 4 ,message = "min size for title is 4")
    private String categoryTitle;
    @NotBlank
    @Size(message = "minimum size for Description is 10",min = 10)
    private String categoryDescription;
}
