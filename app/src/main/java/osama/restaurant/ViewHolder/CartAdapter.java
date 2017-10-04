package osama.restaurant.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import osama.restaurant.Model.Order;
import osama.restaurant.R;

/**
 * Created by osama on 12/09/17.
 */


public class CartAdapter extends RecyclerView.Adapter<CardViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return  new CardViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.cart_item_count.setImageDrawable(drawable);

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        double price = Double.parseDouble(listData.get(position).getPrice()) * Double.parseDouble(listData.get(position).getQuantity()) ;
        holder.txt_cart_item_price.setText(fmt.format(price).toString());
        holder.txt_cart_item_name.setText(listData.get(position).getProductName());
//        holder.txt_cart_item_price.setText(listData.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}