package com.shoppingcarttest.mjepiscope.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KaeL on 26/10/17.
 */

public class ContactDetails implements Serializable {
    @SerializedName("id")
    private int id;
    public int getId() { return this.id; }

    @SerializedName("shoppingCartId")
    private int shoppingCartId;
    public int getShoppingCartId() { return this.shoppingCartId; }

    @SerializedName("email")
    private String email;
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    @SerializedName("landline")
    private String landline;
    public String getLandline() { return this.landline; }
    public void setLandline(String landline) { this.landline = landline; }

    @SerializedName("handphone")
    private String handphone;
    public String getHandphone() { return this.handphone; }
    public void setHandphone(String handphone) { this.handphone = handphone; }

    public String getDisplayContactDetails() {
        if (this.email != null) return this.email;
        if (this.landline != null) return this.landline;
        if (this.handphone != null) return this.handphone;
        return null;
    }
}
