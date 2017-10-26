package com.shoppingcarttest.mjepiscope.shoppingcarttest;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCartResponse;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.services.ShoppingCartService;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.ICartItemDialog;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.IListViewUpdate;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.ListViewHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditShoppingCartActivity extends BaseShoppingCartCreateUpdateActivity
    implements ICartItemDialog, IListViewUpdate {

    public final static String PARAM_ID = "id";

    private int id;
    private ShoppingCart shoppingCart;
    private List<Item> deletedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        id = intent.getIntExtra(PARAM_ID, -1);

        initControls();

        Callback<ShoppingCartResponse> callback = new Callback<ShoppingCartResponse>() {
            @Override
            public void onResponse(Call<ShoppingCartResponse> call, Response<ShoppingCartResponse> response) {
                shoppingCart = response.body().getShoppingCart();

                if (shoppingCart != null) {
                    initControls();
                    setShoppingCart(shoppingCart);
                }

                progressBar.setVisibility(View.INVISIBLE);

                if (response.body().getErrorMessage().length() > 0) {
                    View view = findViewById(R.id.clEditShoppingCart);
                    Snackbar.make(view, response.body().getErrorMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<ShoppingCartResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                View view = findViewById(R.id.clEditShoppingCart);
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };

        progressBar.setVisibility(View.VISIBLE);
        service.getShoppingCartAsync(id, callback);
    }

    @Override
    protected void btnSaveCart_Click(View view) {
        final View v = view;

        setCartDetailsForUpdate();
        setShippingDetailsForUpdate();
        setContactDetailsForUpdate();

        Callback<Boolean> callback = new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(EditShoppingCartActivity.this, ShoppingCartActivity.class));
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(v, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };

        Item[] deletedItemArray = deletedItems.toArray(new Item[deletedItems.size()]);

        service.editShoppingCartAsync(shoppingCart, deletedItemArray, callback);
    }

    @Override
    public void UpdateListView(Item item) {
        deletedItems.add(item);
        adapter.remove(item);
        adapter.notifyDataSetChanged();
        txtItemCount.setText("Total items: " + String.valueOf(items.size()));
        ListViewHelper.setListViewHeightBasedOnChildren(lvItems);
    }

    private void setShoppingCart(ShoppingCart shoppingCart) {
        setCartDetails(shoppingCart.getCartDetails());
        setShippingDetails(shoppingCart.getShippingDetails());
        setContactDetails(shoppingCart.getContactDetails());
    }

    private void setCartDetails(CartDetails cartDetails) {
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList(Arrays.asList(cartDetails.getItems()));
        adapter = new ItemAdapter(this, items);
        lvItems.setAdapter(adapter);
        lvItems.addHeaderView(lvHeader);

        // for some reason, we need to execute this
        // else the items are not displayed properly
        adapter.notifyDataSetChanged();
        txtItemCount.setText("Total items: " + String.valueOf(items.size()));
        ListViewHelper.setListViewHeightBasedOnChildren(lvItems);
    }

    private void setShippingDetails(ShippingDetails shippingDetails) {
        setHomeAddress(shippingDetails.getHomeAddress());
        setOfficeAddress(shippingDetails.getOfficeAddress());
    }

    private void setHomeAddress(Address address) {
        EditText txtHomeStreetAddress1 = (EditText) findViewById(R.id.txtHomeStreetAddress1);
        EditText txtHomeStreetAddress2 = (EditText) findViewById(R.id.txtHomeStreetAddress2);
        EditText txtHomeStreetAddress3 = (EditText) findViewById(R.id.txtHomeStreetAddress3);
        EditText txtHomeZip = (EditText) findViewById(R.id.txtHomeZip);
        EditText txtHomeCity = (EditText) findViewById(R.id.txtHomeCity);
        EditText txtHomeCountry = (EditText) findViewById(R.id.txtHomeCountry);

        txtHomeStreetAddress1.setText(address.getStreetAddress1());
        txtHomeStreetAddress2.setText(address.getStreetAddress2());
        txtHomeStreetAddress3.setText(address.getStreetAddress3());
        txtHomeZip.setText(address.getZip());
        txtHomeCity.setText(address.getCity());
        txtHomeCountry.setText(address.getCountry());
    }

    private void setContactDetails(ContactDetails contactDetails) {
        EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        EditText txtLandline = (EditText) findViewById(R.id.txtLandline);
        EditText txtHandphone = (EditText) findViewById(R.id.txtHandphone);

        txtEmail.setText(contactDetails.getEmail());
        txtLandline.setText(contactDetails.getLandline());
        txtHandphone.setText(contactDetails.getHandphone());
    }

    private void setCartDetailsForUpdate() {
        CartDetails cartDetails = getCartDetails();
        shoppingCart.getCartDetails().setItems(cartDetails.getItems());
    }

    private void setShippingDetailsForUpdate() {
        ShippingDetails shippingDetails = getShippingDetails();
        setHomeAddressForUpdate(shippingDetails.getHomeAddress());
        setOfficeAddressForUpdate(shippingDetails.getOfficeAddress());
    }

    private void setHomeAddressForUpdate(Address address) {
        shoppingCart.getShippingDetails().getHomeAddress().setStreetAddress1(address.getStreetAddress1());
        shoppingCart.getShippingDetails().getHomeAddress().setStreetAddress2(address.getStreetAddress2());
        shoppingCart.getShippingDetails().getHomeAddress().setStreetAddress3(address.getStreetAddress3());
        shoppingCart.getShippingDetails().getHomeAddress().setZip(address.getZip());
        shoppingCart.getShippingDetails().getHomeAddress().setCity(address.getCity());
        shoppingCart.getShippingDetails().getHomeAddress().setCountry(address.getCountry());
    }

    private void setOfficeAddressForUpdate(Address address) {
        shoppingCart.getShippingDetails().getOfficeAddress().setStreetAddress1(address.getStreetAddress1());
        shoppingCart.getShippingDetails().getOfficeAddress().setStreetAddress2(address.getStreetAddress2());
        shoppingCart.getShippingDetails().getOfficeAddress().setStreetAddress3(address.getStreetAddress3());
        shoppingCart.getShippingDetails().getOfficeAddress().setZip(address.getZip());
        shoppingCart.getShippingDetails().getOfficeAddress().setCity(address.getCity());
        shoppingCart.getShippingDetails().getOfficeAddress().setCountry(address.getCountry());
    }

    private void setContactDetailsForUpdate() {
        ContactDetails contactDetails = getContactDetails();

        shoppingCart.getContactDetails().setEmail(contactDetails.getEmail());
        shoppingCart.getContactDetails().setLandline(contactDetails.getLandline());
        shoppingCart.getContactDetails().setHandphone(contactDetails.getHandphone());
    }
}
