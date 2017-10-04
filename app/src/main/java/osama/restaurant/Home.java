package osama.restaurant;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import osama.restaurant.MenuFragment.OrderFragment;
import osama.restaurant.MenuFragment.HomeFragment;
import osama.restaurant.MenuFragment.ProfileFragment;


public class Home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
            if (tabId == R.id.tab_home) {
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, new HomeFragment()).commit();
            }else if (tabId == R.id.tab_profile) {
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, new ProfileFragment()).commit();
            }else if (tabId == R.id.tab_order) {
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, new OrderFragment()).commit();
            }

            }
        });

    }
}
