package osama.restaurant.FoodFragment;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import osama.restaurant.Database.Database;
import osama.restaurant.Model.FirebaseConnection;
import osama.restaurant.Model.Food;
import osama.restaurant.Model.Order;
import osama.restaurant.R;

public class FoodDetailsFragment extends Fragment {

    View rootView;
    String foodId;
    TextView food_name,food_price,food_description,food_quality;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    DatabaseReference foods;
    Button btn_plus , btn_minus;
    Food  currentFood;
    int quality;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_food_details, container, false);
        Bundle bundle = getArguments();

        //Firebase
        foodId = bundle.getString("FoodId"); //retrieve data from fragment
        FirebaseConnection firebase = new FirebaseConnection();
        firebase.getTable("Foods");
        foods = firebase.getReference();

        //Init View
        btnCart = (FloatingActionButton) rootView.findViewById(R.id.btnCart);
        btn_plus = (Button) rootView.findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality = Integer.parseInt(food_quality.getText().toString());
                if(quality == 20)
                    Toast.makeText(getActivity(), "Max Limit in quality is 20 unit", Toast.LENGTH_SHORT).show();
                else{
                    quality++;
                    food_quality.setText(String.valueOf(quality));
                }
            }
        });

        btn_minus = (Button) (Button) rootView.findViewById(R.id.btn_minus);
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality = Integer.parseInt(food_quality.getText().toString());
                if(quality == 1)
                    Toast.makeText(getActivity(), "Min Limit in quality is 1 unit", Toast.LENGTH_SHORT).show();
                else{
                    quality--;
                    food_quality.setText(String.valueOf(quality));
                }
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getActivity().getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        food_quality.getText().toString(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));
                Toast.makeText(getActivity().getBaseContext(), "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });

        food_name = (TextView) rootView.findViewById(R.id.food_name);
        food_price = (TextView) rootView.findViewById(R.id.food_price);
        food_quality = (TextView) rootView.findViewById(R.id.food_quality);
        food_description = (TextView) rootView.findViewById(R.id.food_description);
        food_image = (ImageView) rootView.findViewById(R.id.food_image);


        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppbar);

        if(!foodId.isEmpty() && foodId != null){
            getDetailsFood(foodId);
        }
        return rootView;
    }

    private void getDetailsFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                Picasso.with(getActivity().getBaseContext()).load(currentFood.getImage())
                        .into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_name.setText(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
