package osama.restaurant.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import osama.restaurant.R;

/**
 * Created by osama on 12/09/17.
 */

public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_cart_item_name,txt_cart_item_price;
    public ImageView cart_item_count;

    public void setTxt_cart_item_name(TextView txt_cart_item_name) {
        this.txt_cart_item_name = txt_cart_item_name;
    }

    public CardViewHolder(View itemView) {
        super(itemView);
        txt_cart_item_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_cart_item_price = (TextView) itemView.findViewById(R.id.cart_item_price);
        cart_item_count = (ImageView) itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View v) {

    }
}

