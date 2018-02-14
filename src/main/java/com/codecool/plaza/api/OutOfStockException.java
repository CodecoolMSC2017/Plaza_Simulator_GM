package com.codecool.plaza.api;

public class OutOfStockException extends ShopException {

    public OutOfStockException(String message) {
        super(message);
    }
}
