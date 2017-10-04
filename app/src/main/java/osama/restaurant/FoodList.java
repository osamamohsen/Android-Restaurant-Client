package osama.restaurant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import osama.restaurant.FoodFragment.FoodFragment;

public class FoodList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        getSupportFragmentManager().beginTransaction().add(R.id.foodContainer, new FoodFragment()).commit();
    }

}
