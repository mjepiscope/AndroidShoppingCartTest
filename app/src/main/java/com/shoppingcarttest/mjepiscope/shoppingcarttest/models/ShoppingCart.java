package com.shoppingcarttest.mjepiscope.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KaeL on 25/10/17.
 */

public class ShoppingCart implements Serializable {

    @SerializedName("id")
    private int id;
    public int getId() { return this.id; }

    @SerializedName("cartDetails")
    private CartDetails cartDetails;
    public CartDetails getCartDetails() { return this.cartDetails; }
    public void setCartDetails(CartDetails cartDetails) { this.cartDetails = cartDetails; }

    @SerializedName("shippingDetails")
    private ShippingDetails shippingDetails;
    public ShippingDetails getShippingDetails() { return this.shippingDetails; }
    public void  setShippingDetails(ShippingDetails shippingDetails) { this.shippingDetails = shippingDetails; }

    @SerializedName("contactDetails")
    private ContactDetails contactDetails;
    public ContactDetails getContactDetails() { return this.contactDetails; }
    public void setContactDetails(ContactDetails contactDetails) { this.contactDetails = contactDetails; }
}
