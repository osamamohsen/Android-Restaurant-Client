package osama.restaurant.MenuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import osama.restaurant.FoodList;
import osama.restaurant.Interface.ItemClickListner;
import osama.restaurant.Model.Category;
import osama.restaurant.Model.FirebaseConnection;
import osama.restaurant.R;
import osama.restaurant.ViewHolder.MenuViewHolder;


public class HomeFragment extends Fragment {

    View rootView;
    RecyclerView recycle_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);


        //Load Menu
        recycle_menu = (RecyclerView)  rootView.findViewById(R.id.recycler_menu);
        recycle_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recycle_menu.setLayoutManager(layoutManager);

        LoadMenu();
        return rootView;
    }

    private void LoadMenu() {

        FirebaseConnection firebaseConnection = new FirebaseConnection();
        firebaseConnection.getTable("Category");

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>
                (Category.class,R.layout.menu_item,MenuViewHolder.class,firebaseConnection.getReference()) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getActivity().getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodList = new Intent(getActivity().getApplicationContext(), FoodList.class);
                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);
//                        Toast.makeText(getActivity().getApplicationContext(), position + " : " + clickItem.getName() , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recycle_menu.setAdapter(adapter);
    }//end load menu
}