package osama.restaurant.FoodFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import osama.restaurant.Interface.ItemClickListner;
import osama.restaurant.Model.FirebaseConnection;
import osama.restaurant.Model.Food;
import osama.restaurant.R;
import osama.restaurant.ViewHolder.FoodViewHolder;

public class FoodFragment extends Fragment {

    View rootView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String categoryId = "";
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    //Search Functionallity
    FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    FirebaseConnection firebaseFoodList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_food_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // get Intent which came from Category
        if(getActivity().getIntent() != null)
            categoryId = getActivity().getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFood(categoryId);
        }

        //Search
        materialSearchBar = (MaterialSearchBar) rootView.findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your food");
        loadSuggest(); // write function to load suggest from firebase
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<String>();
                for (String search: suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search bar is close , restore original adapter
                if(!enabled) recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when search is finished show result in adapter
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        return rootView;
    }

    private void startSearch(CharSequence text) {
        firebaseFoodList = new FirebaseConnection();
        firebaseFoodList.getTable("Foods");

        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                firebaseFoodList.getReference().orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.foodName.setText(model.getName());
                Picasso.with(getActivity().getBaseContext()).load(model.getImage())
                        .into(viewHolder.foodImage);
                final Food local = model;
                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(getActivity().getBaseContext(), ""+local.getName(), Toast.LENGTH_SHORT).show();
//                        Intent intent
                        Bundle bundle = new Bundle();
                        bundle.putString("FoodId",adapter.getRef(position).getKey());
                        FoodDetailsFragment foodDetailsFragment = new FoodDetailsFragment();
                        foodDetailsFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.foodContainer,foodDetailsFragment).addToBackStack(null).commit();

                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        DatabaseReference reference = firebaseFoodList.getReference();
        reference.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Food item = postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName());//add name of food to suggest list
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void loadListFood(String categoryId) {

        firebaseFoodList = new FirebaseConnection();
        firebaseFoodList.getTable("Foods");

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>
                (Food.class,
                        R.layout.food_item,
                        FoodViewHolder.class,
                        firebaseFoodList.getReference().orderByChild("MenuId").equalTo(categoryId)) {
            //select * from Foods where MenuId = categoryId
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.foodName.setText(model.getName());
                Picasso.with(getActivity().getBaseContext()).load(model.getImage())
                        .into(viewHolder.foodImage);
                final Food local = model;
                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(getActivity().getBaseContext(), ""+local.getName(), Toast.LENGTH_SHORT).show();
//                        Intent intent
                        Bundle bundle = new Bundle();
                        bundle.putString("FoodId",adapter.getRef(position).getKey());
                        FoodDetailsFragment foodDetailsFragment = new FoodDetailsFragment();
                        foodDetailsFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.foodContainer,foodDetailsFragment).addToBackStack(null).commit();

                    }
                });
            }
        };

        //Set Adapter
        recyclerView.setAdapter(adapter);

    }//end loadListFood
}
