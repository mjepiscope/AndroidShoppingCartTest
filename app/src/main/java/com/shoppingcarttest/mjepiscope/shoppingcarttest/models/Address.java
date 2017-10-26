package com.shoppingcarttest.mjepiscope.shoppingcarttest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by KaeL on 26/10/17.
 */

public class Address implements Serializable {
    @SerializedName("id")
    private int id;
    public int getId() { return this.id; }

    @SerializedName("shippingDetailsId")
    private int shippingDetailsId;
    public int getShippingDetailsId() { return this.shippingDetailsId; }

    @SerializedName("streetAddress1")
    private String streetAddress1;
    public String getStreetAddress1() { return this.streetAddress1; }
    public void setStreetAddress1(String streetAddress1) { this.streetAddress1 = streetAddress1; }

    @SerializedName("streetAddress2")
    private String streetAddress2;
    public String getStreetAddress2() { return this.streetAddress2; }
    public void setStreetAddress2(String streetAddress2) { this.streetAddress2 = streetAddress2; }

    @SerializedName("streetAddress3")
    private String streetAddress3;
    public String getStreetAddress3() { return this.streetAddress3; }
    public void setStreetAddress3(String streetAddress3) { this.streetAddress3 = streetAddress3; }

    @SerializedName("zip")
    private String zip;
    public String getZip() { return this.zip; }
    public void setZip(String zip) { this.zip = zip; }

    @SerializedName("city")
    private String city;
    public String getCity() { return this.city; }
    public void setCity(String city) { this.city = city; }

    @SerializedName("country")
    private String country;
    public String getCountry() { return this.country; }
    public void setCountry(String country) { this.country = country; }

    public String getDisplayAddress() {
        return this.streetAddress1 + " " + this.city + " " + this.country + " " + this.zip;
    }
}
