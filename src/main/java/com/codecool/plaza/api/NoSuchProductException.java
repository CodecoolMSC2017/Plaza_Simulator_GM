package com.codecool.plaza.api;

public class NoSuchProductException extends ShopException {

    public NoSuchProductException(String message) {
        super(message);
    }
}
