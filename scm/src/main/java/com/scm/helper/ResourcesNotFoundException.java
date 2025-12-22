package com.scm.helper;

public class ResourcesNotFoundException extends RuntimeException{

    public ResourcesNotFoundException(String message){
        super(message);
    }
    public ResourcesNotFoundException(){
        super("Resource not found");
    }
}
