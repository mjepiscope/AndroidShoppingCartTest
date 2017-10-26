package com.shoppingcarttest.mjepiscope.shoppingcarttest.rest;

import retrofit2.Retrofit;

/**
 * Created by KaeL on 25/10/17.
 */

public class ServiceFactory {
    private static ServiceFactory factory;
    private IShoppingCartService shoppingCartService;

    public static ServiceFactory getServiceFactory()
    {
        if (factory != null) {
            return factory;
        }

        initFactory();
        return factory;
    }

    public IShoppingCartService getShoppingCartService() {
        return this.shoppingCartService;
    }

    private static void initFactory() {
        Retrofit adapter = ServiceAdapter.getAdapter();

        factory = new ServiceFactory();
        factory.shoppingCartService = adapter.create(IShoppingCartService.class);
    }
}
