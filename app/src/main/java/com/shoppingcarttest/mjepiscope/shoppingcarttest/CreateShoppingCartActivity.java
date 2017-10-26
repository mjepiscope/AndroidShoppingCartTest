package com.shoppingcarttest.mjepiscope.shoppingcarttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.Address;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.CartDetails;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ContactDetails;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.Item;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShippingDetails;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCart;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.services.ShoppingCartService;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.ICartItemDialog;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.IListViewUpdate;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.ListViewHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateShoppingCartActivity extends BaseShoppingCartCreateUpdateActivity
    implements ICartItemDialog, IListViewUpdate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initControls();

        items = new ArrayList<>();
        adapter = new ItemAdapter(this, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.addHeaderView(lvHeader);
        lvItems.setAdapter(adapter);
    }

    @Override
    protected void btnSaveCart_Click(View view) {
        final View v = view;
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setCartDetails(getCartDetails());
        shoppingCart.setShippingDetails(getShippingDetails());
        shoppingCart.setContactDetails(getContactDetails());

        Callback<Boolean> callback = new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(CreateShoppingCartActivity.this, ShoppingCartActivity.class));
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(v, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };

        service.createShoppingCartAsync(shoppingCart, callback);
    }

    @Override
    public void UpdateListView(Item item) {
        adapter.remove(item);
        adapter.notifyDataSetChanged();
        txtItemCount.setText("Total items: " + String.valueOf(items.size()));
        ListViewHelper.setListViewHeightBasedOnChildren(lvItems);
    }
}
