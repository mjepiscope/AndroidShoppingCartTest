package com.shoppingcarttest.mjepiscope.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KaeL on 27/10/17.
 */

public class ShoppingCartResponse implements Serializable {
    @SerializedName("shoppingCart")
    private ShoppingCart shoppingCart;
    public ShoppingCart getShoppingCart() { return this.shoppingCart; }

    @SerializedName("errorMessage")
    private String errorMessage;
    public String getErrorMessage() { return this.errorMessage; }
}
