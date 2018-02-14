package com.codecool.plaza.api;

public class ShopException extends Exception {
    private String message;

    public ShopException(String message) {
        this.message = message;
    }

}
