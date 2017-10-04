package osama.restaurant.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import osama.restaurant.Interface.ItemClickListner;
import osama.restaurant.R;

/**
 * Created by osama on 09/09/17.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView foodName;
    public ImageView foodImage;

    private ItemClickListner itemClickListner;
    public FoodViewHolder(View itemView) {
        super(itemView);
        foodName = (TextView) itemView.findViewById(R.id.food_name);// R is in my package
        foodImage = (ImageView) itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }
}
