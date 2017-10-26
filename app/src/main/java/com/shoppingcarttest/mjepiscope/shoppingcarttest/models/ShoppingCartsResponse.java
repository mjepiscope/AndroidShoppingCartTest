package com.shoppingcarttest.mjepiscope.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KaeL on 25/10/17.
 */

public class ShoppingCartsResponse implements Serializable {
    @SerializedName("shoppingCarts")
    private ShoppingCart[] shoppingCarts;
    public ShoppingCart[] getShoppingCarts() { return this.shoppingCarts; }

    @SerializedName("errorMessage")
    private String errorMessage;
    public String getErrorMessage() { return this.errorMessage; }
}
