package com.shoppingcarttest.mjepiscope.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KaeL on 26/10/17.
 */

public class ShippingDetails implements Serializable {
    @SerializedName("id")
    private int id;
    public int getId() { return this.id; }

    @SerializedName("shoppingCartId")
    private int shoppingCartId;
    public int getShoppingCartId() { return this.shoppingCartId; }

    @SerializedName("homeAddress")
    private Address homeAddress;
    public Address getHomeAddress() { return this.homeAddress; }
    public void setHomeAddress(Address homeAddress) { this.homeAddress = homeAddress; }

    @SerializedName("officeAddress")
    private Address officeAddress;
    public Address getOfficeAddress() { return this.officeAddress; }
    public void setOfficeAddress(Address officeAddress) { this.officeAddress = officeAddress; }

    public String getDisplayAddress() {
        if (this.homeAddress != null)
            return this.homeAddress.getDisplayAddress();
        if (this.officeAddress != null)
            return this.officeAddress.getDisplayAddress();

        return null;
    }
}
