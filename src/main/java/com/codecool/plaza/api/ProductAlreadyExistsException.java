package com.codecool.plaza.api;

public class ProductAlreadyExistsException extends ShopException {

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
