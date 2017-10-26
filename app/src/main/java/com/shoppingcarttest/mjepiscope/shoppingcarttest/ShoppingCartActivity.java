package com.shoppingcarttest.mjepiscope.shoppingcarttest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCart;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.ShoppingCartsResponse;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.services.ShoppingCartService;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity {

    final ShoppingCartService service;
    private ProgressBar progressBar;
    private ListView lvShoppingCarts;
    private SwipeRefreshLayout srlShoppingCart;

    public ShoppingCartActivity() {
        service = new ShoppingCartService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShoppingCartActivity.this, CreateShoppingCartActivity.class));
            }
        });

        lvShoppingCarts = (ListView) findViewById(R.id.lvShoppingCarts);
        lvShoppingCarts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingCart shoppingCart = (ShoppingCart) lvShoppingCarts.getAdapter().getItem(position);
                Intent intent = new Intent(ShoppingCartActivity.this, EditShoppingCartActivity.class);
                intent.putExtra(EditShoppingCartActivity.PARAM_ID, shoppingCart.getId());
                startActivity(intent);
            }
        });

        srlShoppingCart = (SwipeRefreshLayout) findViewById(R.id.srlShoppingCart);
        srlShoppingCart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadShoppingCart();
            }
        });

        loadShoppingCart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadShoppingCart() {
        Callback<ShoppingCartsResponse> callback = new Callback<ShoppingCartsResponse>() {
            @Override
            public void onResponse(Call<ShoppingCartsResponse> call, Response<ShoppingCartsResponse> response) {
                ArrayList<ShoppingCart> items = new ArrayList(Arrays.asList(response.body().getShoppingCarts()));

                if (items.size() > 0) {
                    ShoppingCartsAdapter adapter = new ShoppingCartsAdapter(ShoppingCartActivity.this, R.layout.shopping_cart_row, items);
                    lvShoppingCarts.setAdapter(adapter);
                }

                progressBar.setVisibility(View.INVISIBLE);
                srlShoppingCart.setRefreshing(false);

                if (response.body().getErrorMessage().length() > 0) {
                    View view = findViewById(R.id.lvShoppingCarts);
                    Snackbar.make(view, response.body().getErrorMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<ShoppingCartsResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                srlShoppingCart.setRefreshing(false);
                View view = findViewById(R.id.lvShoppingCarts);
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };

        progressBar.setVisibility(View.VISIBLE);
        service.getShoppingCartsAsync(callback);
    }

    private class ShoppingCartsAdapter extends ArrayAdapter<ShoppingCart> {
        public ShoppingCartsAdapter(Context context, int layoutId, ArrayList<ShoppingCart> items) {
            super(context, layoutId, items);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder vw;
            LayoutInflater inflater = getLayoutInflater();

            if (view == null) {
                view = inflater.inflate(R.layout.shopping_cart_row, null, false);
                vw = new ViewHolder(view);
                view.setTag(vw);
            }
            else {
                vw = (ViewHolder) view.getTag();
            }

            ShoppingCart item = getItem(position);

            if (item != null) {
                vw.getTxtShoppingCartId().setText("Shopping Cart Id: " + String.valueOf(item.getId()));
                vw.getTxtItemQty().setText(String.valueOf(item.getCartDetails().getItems().length) + " item/s");
                vw.getTxtAddress().setText(item.getShippingDetails().getDisplayAddress());
                vw.getTxtContactDetails().setText(item.getContactDetails().getDisplayContactDetails());
            }

            return view;
        }
    }

    private class ViewHolder {
        private View row;
        private TextView txtShoppingCartId, txtItemQty, txtAddress, txtContactDetails;

        public ViewHolder(View row) {
            this.row = row;
        }

        public TextView getTxtShoppingCartId() {
            if (this.txtShoppingCartId == null) {
                this.txtShoppingCartId = row.findViewById(R.id.txtShoppingCartId);
            }
            return this.txtShoppingCartId;
        }

        public TextView getTxtItemQty() {
            if (this.txtItemQty == null) {
                this.txtItemQty = row.findViewById(R.id.txtItemQty);
            }
            return this.txtItemQty;
        }

        public TextView getTxtAddress() {
            if (this.txtAddress == null) {
                this.txtAddress = row.findViewById(R.id.txtAddress);
            }
            return this.txtAddress;
        }

        public TextView getTxtContactDetails() {
            if (this.txtContactDetails == null) {
                this.txtContactDetails = row.findViewById(R.id.txtContactDetails);
            }
            return this.txtContactDetails;
        }
    }
}
