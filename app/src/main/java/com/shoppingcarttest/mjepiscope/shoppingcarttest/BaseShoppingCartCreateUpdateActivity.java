package com.shoppingcarttest.mjepiscope.shoppingcarttest;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.shoppingcarttest.mjepiscope.shoppingcarttest.services.ShoppingCartService;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.ICartItemDialog;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.ListViewHelper;

import java.util.List;

/**
 * Created by KaeL on 27/10/17.
 */

public class BaseShoppingCartCreateUpdateActivity extends AppCompatActivity
        implements ICartItemDialog {

    protected ProgressBar progressBar;
    protected TextView txtItemCount;
    protected ListView lvItems;
    protected View lvHeader;
    protected ItemAdapter adapter;
    protected List<Item> items;
    protected Button btnCancelCart, btnSaveCart;

    final ShoppingCartService service;

    protected BaseShoppingCartCreateUpdateActivity() {
        service = new ShoppingCartService();
    }

    protected void initControls() {
        lvHeader = getLayoutInflater().inflate(R.layout.shopping_cart_item_header, null);
        txtItemCount = (TextView) findViewById(R.id.lblItemCount);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        btnSaveCart = (Button) findViewById(R.id.btnSaveCart);
        btnSaveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btnSaveCart_Click(v);
            }
        });

        Button btnShowAddItemDialog = (Button) findViewById(R.id.btnShowAddItemDialog);
        btnShowAddItemDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });

        btnCancelCart = (Button) findViewById(R.id.btnCancelCart);
        btnCancelCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseShoppingCartCreateUpdateActivity.this, ShoppingCartActivity.class));
            }
        });

        CheckBox cbCopyHomeAddress = (CheckBox) findViewById(R.id.cbCopyHomeAddress);
        cbCopyHomeAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Address homeAddress = getHomeAddress();
                    setOfficeAddress(homeAddress);
                }
            }
        });
    }

    public void btnAddCartItem_Click(Item item) {
        items.add(item);
        adapter.notifyDataSetChanged();
        txtItemCount.setText("Total items: " + String.valueOf(adapter.getCount()));
        ListViewHelper.setListViewHeightBasedOnChildren(lvItems);
    }

    protected void btnSaveCart_Click(View view) {
        throw new UnsupportedOperationException("Must override this method");
    }

    protected void showAddItemDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment f = getSupportFragmentManager().findFragmentByTag("dialog");

        if (f != null) {
            ft.remove(f);
        }

        ft.addToBackStack(null);

        DialogFragment df = AddShoppingItemFragment.newInstance();
        df.show(ft, "dialog");
    }

    protected void setOfficeAddress(Address address) {
        EditText txtOfficeStreetAddress1 = (EditText) findViewById(R.id.txtOfficeStreetAddress1);
        EditText txtOfficeStreetAddress2 = (EditText) findViewById(R.id.txtOfficeStreetAddress2);
        EditText txtOfficeStreetAddress3 = (EditText) findViewById(R.id.txtOfficeStreetAddress3);
        EditText txtOfficeZip = (EditText) findViewById(R.id.txtOfficeZip);
        EditText txtOfficeCity = (EditText) findViewById(R.id.txtOfficeCity);
        EditText txtOfficeCountry = (EditText) findViewById(R.id.txtOfficeCountry);

        txtOfficeStreetAddress1.setText(address.getStreetAddress1());
        txtOfficeStreetAddress2.setText(address.getStreetAddress2());
        txtOfficeStreetAddress3.setText(address.getStreetAddress3());
        txtOfficeZip.setText(address.getZip());
        txtOfficeCity.setText(address.getCity());
        txtOfficeCountry.setText(address.getCountry());
    }

    protected CartDetails getCartDetails() {
        CartDetails cartDetails = new CartDetails();
        cartDetails.setItems(items.toArray(new Item[items.size()]));
        return cartDetails;
    }

    protected ShippingDetails getShippingDetails() {
        ShippingDetails shippingDetails = new ShippingDetails();

        shippingDetails.setHomeAddress(getHomeAddress());
        shippingDetails.setOfficeAddress(getOfficeAddress());

        return shippingDetails;
    }

    protected Address getHomeAddress() {
        EditText txtHomeStreetAddress1 = (EditText) findViewById(R.id.txtHomeStreetAddress1);
        EditText txtHomeStreetAddress2 = (EditText) findViewById(R.id.txtHomeStreetAddress2);
        EditText txtHomeStreetAddress3 = (EditText) findViewById(R.id.txtHomeStreetAddress3);
        EditText txtHomeZip = (EditText) findViewById(R.id.txtHomeZip);
        EditText txtHomeCity = (EditText) findViewById(R.id.txtHomeCity);
        EditText txtHomeCountry = (EditText) findViewById(R.id.txtHomeCountry);

        if (txtHomeStreetAddress1.getText() == null
                || txtHomeZip.getText() == null
                || txtHomeCity.getText() == null
                || txtHomeCountry.getText() == null) {
            return null;
        }

        Address homeAddress = new Address();
        homeAddress.setStreetAddress1(String.valueOf(txtHomeStreetAddress1.getText()));
        homeAddress.setStreetAddress2(String.valueOf(txtHomeStreetAddress2.getText()));
        homeAddress.setStreetAddress3(String.valueOf(txtHomeStreetAddress3.getText()));
        homeAddress.setZip(String.valueOf(txtHomeZip.getText()));
        homeAddress.setCity(String.valueOf(txtHomeCity.getText()));
        homeAddress.setCountry(String.valueOf(txtHomeCountry.getText()));

        return homeAddress;
    }

    protected Address getOfficeAddress() {
        EditText txtOfficeStreetAddress1 = (EditText) findViewById(R.id.txtOfficeStreetAddress1);
        EditText txtOfficeStreetAddress2 = (EditText) findViewById(R.id.txtOfficeStreetAddress2);
        EditText txtOfficeStreetAddress3 = (EditText) findViewById(R.id.txtOfficeStreetAddress3);
        EditText txtOfficeZip = (EditText) findViewById(R.id.txtOfficeZip);
        EditText txtOfficeCity = (EditText) findViewById(R.id.txtOfficeCity);
        EditText txtOfficeCountry = (EditText) findViewById(R.id.txtOfficeCountry);

        //If required values are invalid return null
        if (txtOfficeStreetAddress1.getText() == null
                || txtOfficeZip.getText() == null
                || txtOfficeCity.getText() == null
                || txtOfficeCountry.getText() == null) {
            return null;
        }

        Address officeAddress = new Address();
        officeAddress.setStreetAddress1(String.valueOf(txtOfficeStreetAddress1.getText()));
        officeAddress.setStreetAddress2(String.valueOf(txtOfficeStreetAddress2.getText()));
        officeAddress.setStreetAddress3(String.valueOf(txtOfficeStreetAddress3.getText()));
        officeAddress.setZip(String.valueOf(txtOfficeZip.getText()));
        officeAddress.setCity(String.valueOf(txtOfficeCity.getText()));
        officeAddress.setCountry(String.valueOf(txtOfficeCountry.getText()));

        return officeAddress;
    }

    protected ContactDetails getContactDetails() {
        EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        EditText txtLandline = (EditText) findViewById(R.id.txtLandline);
        EditText txtHandphone = (EditText) findViewById(R.id.txtHandphone);

        if (txtEmail.getText() == null
                && txtLandline.getText() == null
                && txtHandphone.getText() == null) {
            return null;
        }

        ContactDetails contactDetails = new ContactDetails();

        contactDetails.setEmail(String.valueOf(txtEmail.getText()));
        contactDetails.setLandline(String.valueOf(txtLandline.getText()));
        contactDetails.setHandphone(String.valueOf(txtHandphone.getText()));

        return contactDetails;
    }
}
