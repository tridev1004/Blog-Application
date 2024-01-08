package com.example.blogapplication.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resouceName;
    String filedName;

    public ResourceNotFoundException(String resouceName, String filedName, long fieldValue) {
        super(String.format("%s not found with %s : %s",resouceName,filedName,fieldValue));
        this.resouceName = resouceName;
        this.filedName = filedName;
        this.fieldValue = fieldValue;
    }

    long fieldValue;
}
