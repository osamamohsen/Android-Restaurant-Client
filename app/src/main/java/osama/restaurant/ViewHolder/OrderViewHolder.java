package osama.restaurant.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import osama.restaurant.Interface.ItemClickListner;
import osama.restaurant.R;

/**
 * Created by osama on 15/09/17.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder{

    public TextView txt_order_id , txt_order_status , txt_order_address , txt_order_phone;
    private ItemClickListner itemClickListner;

    public OrderViewHolder(View itemView) {
        super(itemView);
        txt_order_id = (TextView) itemView.findViewById(R.id.txt_order_id);
        txt_order_status = (TextView) itemView.findViewById(R.id.txt_order_status);
        txt_order_address = (TextView) itemView.findViewById(R.id.txt_order_address);
        txt_order_phone = (TextView) itemView.findViewById(R.id.txt_order_phone);

    }
}
