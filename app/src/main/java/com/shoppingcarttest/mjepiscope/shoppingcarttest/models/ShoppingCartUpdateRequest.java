package com.shoppingcarttest.mjepiscope.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KaeL on 27/10/17.
 */

public class ShoppingCartUpdateRequest implements Serializable{
    @SerializedName("shoppingCart")
    private ShoppingCart shoppingCart;
    public void setShoppingCart(ShoppingCart shoppingCart) { this.shoppingCart = shoppingCart; }

    @SerializedName("deletedItems")
    private Item[] deletedItems;
    public void setDeletedItems(Item[] deletedItems) { this.deletedItems = deletedItems; }
}
