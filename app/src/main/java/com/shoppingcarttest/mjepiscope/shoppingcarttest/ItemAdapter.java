package com.shoppingcarttest.mjepiscope.shoppingcarttest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.shoppingcarttest.mjepiscope.shoppingcarttest.models.Item;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.IListViewUpdate;
import com.shoppingcarttest.mjepiscope.shoppingcarttest.utils.ListViewHelper;

import java.util.List;

/**
 * Created by KaeL on 27/10/17.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private Context context;

    public ItemAdapter(Context context, List<Item> items) {
        super(context, R.layout.shopping_cart_item_row, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vw;
        LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();

        if (view == null) {
            view = inflater.inflate(R.layout.shopping_cart_item_row, null, false);
            vw = new ViewHolder(view);
            view.setTag(vw);
        }
        else {
            vw = (ViewHolder) view.getTag();
        }

        final Item item = getItem(position);

        if (item != null) {
            vw.getTxtItemId().setText(item.getItemId());
            vw.getTxtItemQty().setText(String.valueOf(item.getQty()));
            vw.getBtnDeleteItem().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IListViewUpdate) context).UpdateListView(item);
                }
            });
        }

        return view;
    }

    private class ViewHolder {
        private View row;
        private TextView txtItemId, txtItemQty;
        private Button btnDeleteItem;

        public ViewHolder(View row) {
            this.row = row;
        }

        public TextView getTxtItemId() {
            if (this.txtItemId == null) {
                this.txtItemId = row.findViewById(R.id.txtItemId);
            }
            return this.txtItemId;
        }

        public TextView getTxtItemQty() {
            if (this.txtItemQty == null) {
                this.txtItemQty = row.findViewById(R.id.txtItemQty);
            }
            return this.txtItemQty;
        }

        public Button getBtnDeleteItem() {
            if (this.btnDeleteItem == null) {
                this.btnDeleteItem = row.findViewById(R.id.btnDeleteItem);
            }
            return this.btnDeleteItem;
        }
    }
}
