package com.shoppingcarttest.mjepiscope.shoppingcarttest.services;

import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.Item;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCart;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCartResponse;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCartUpdateRequest;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCartsResponse;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.rest.IShoppingCartService;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.rest.ServiceFactory;

import java.io.IOException;

import retrofit2.Callback;

/**
 * Created by KaeL on 25/10/17.
 */

public class ShoppingCartService {

    final IShoppingCartService service;

    public ShoppingCartService() {
        service = ServiceFactory.getServiceFactory().getShoppingCartService();
    }

    public void testConnectionAsync(Callback<String> callback) throws IOException {
        service.testConnection().enqueue(callback);
    }

    public void getShoppingCartsAsync(Callback<ShoppingCartsResponse> callback) {
        service.getShoppingCartsAsync().enqueue(callback);
    }

    public void getShoppingCartAsync(int id, Callback<ShoppingCartResponse> callback) {
        service.getShoppingCartAsync(id).enqueue(callback);
    }

    public void createShoppingCartAsync(ShoppingCart shoppingCart, Callback<Boolean> callback) {
        service.createShoppingCart(shoppingCart).enqueue(callback);
    }

    public void editShoppingCartAsync(ShoppingCartUpdateRequest request, Callback<Boolean> callback) {
        service.editShoppingCart(request).enqueue(callback);
    }

    public void deleteShoppingCartAsync(int id, Callback<Boolean> callback) {
        service.deleteShoppingCart(id).enqueue(callback);
    }
}
