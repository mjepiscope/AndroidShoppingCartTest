package com.shoppingcarttest.mjepiscope.shoppingcarttest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.Item;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.ICartItemDialog;

public class AddShoppingItemFragment extends DialogFragment {

    private EditText txtItemId;
    private EditText txtItemQty;

    ICartItemDialog cartItemDialog;

    public AddShoppingItemFragment() {
        // Required empty public constructor
    }

    public static AddShoppingItemFragment newInstance() {
        AddShoppingItemFragment fragment = new AddShoppingItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            cartItemDialog = (ICartItemDialog) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_shopping_item, container, false);

        this.txtItemId = view.findViewById(R.id.txtItemId);
        this.txtItemQty = view.findViewById(R.id.txtItemQty);

        Button btnAddCartItem = view.findViewById(R.id.btnAddCartItem);
        btnAddCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddCartItem_Click(view);
            }
        });

        Button btnCancelCartItem = view.findViewById(R.id.btnCancelCartItem);
        btnCancelCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void btnAddCartItem_Click(View view) {
        if (!isValidItem()) {
            Snackbar.make(view, "Item is invalid.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        Item item = new Item();
        item.setItemId(this.txtItemId.getText().toString());
        item.setQty(Integer.parseInt(this.txtItemQty.getText().toString()));

        cartItemDialog.btnAddCartItem_Click(item);

        dismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private boolean isValidItem() {
        if(this.txtItemId.getText() == null
                || this.txtItemId.getText().length() == 0
                || this.txtItemQty.getText() == null
                || this.txtItemQty.getText().length() == 0)
            return false;

        int itemQty;

        try {
            itemQty = Integer.parseInt(String.valueOf(this.txtItemQty.getText()));
        }
        catch (NumberFormatException nfe){
            return false;
        }

        return itemQty > 0;
    }
}
