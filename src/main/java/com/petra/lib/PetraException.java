package com.petra.lib;

public class PetraException extends RuntimeException{

    public PetraException(String message, Exception supplier){
        super(message, supplier);
    }
}
