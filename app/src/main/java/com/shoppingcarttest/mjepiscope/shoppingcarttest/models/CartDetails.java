package com.shoppingcarttest.mjepiscope.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KaeL on 25/10/17.
 */

public class CartDetails implements Serializable {

    @SerializedName("id")
    private int id;
    public int getId() { return this.id; }

    @SerializedName("shoppingCartId")
    private int shoppingCartId;
    public int getShoppingCartId() { return this.shoppingCartId; }

    @SerializedName("items")
    private Item[] items;
    public Item[] getItems() { return this.items; }
    public void setItems(Item[] items) { this.items = items; }
}
