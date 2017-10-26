package com.shoppingcarttest.mjepiscope.shoppingcarttest.rest;

import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.Item;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCart;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCartResponse;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCartsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by KaeL on 25/10/17.
 */

public interface IShoppingCartService {

    @GET("ShoppingCart/TestConnect")
    Call<String> testConnection();

    @GET("ShoppingCart/Index")
    Call<ShoppingCartsResponse> getShoppingCartsAsync();

    @POST("ShoppingCart/Create")
    Call<Boolean> createShoppingCart(@Body ShoppingCart shoppingCart);

    @GET("ShoppingCart/GetById/{id}")
    Call<ShoppingCartResponse> getShoppingCartAsync(@Path("id") int id);

    @POST("ShoppingCart/Edit")
    Call<Boolean> editShoppingCart(@Body ShoppingCart shoppingCart, @Body Item[] deletedItems);
}
