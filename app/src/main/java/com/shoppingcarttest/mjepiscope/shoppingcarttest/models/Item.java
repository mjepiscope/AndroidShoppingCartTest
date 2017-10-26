package com.shoppingcarttest.mjepiscope.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KaeL on 25/10/17.
 */

public class Item implements Serializable{

    @SerializedName("id")
    private int id;
    public int getId() { return this.id; }

    @SerializedName("itemId")
    private String itemId;
    public String getItemId() { return this.itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    @SerializedName("cartDetailsId")
    private int cartDetailsId;
    public int getCartDetailsId() { return this.cartDetailsId; }

    @SerializedName("qty")
    private int qty;
    public int getQty() { return this.qty; }
    public void setQty(int qty) { this.qty = qty; }
}
