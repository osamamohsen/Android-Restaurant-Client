package osama.restaurant.MenuFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;
import osama.restaurant.Common.Common;
import osama.restaurant.Database.Database;
import osama.restaurant.Model.FirebaseConnection;
import osama.restaurant.Model.Order;
import osama.restaurant.Model.Request;
import osama.restaurant.OrderStatus;
import osama.restaurant.R;
import osama.restaurant.ViewHolder.CartAdapter;

public class OrderFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView txtTotalPrice,txtTotalItems;
    FButton btnPlace,btnOrderHistory;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    DatabaseReference db;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_order, container, false);

        //Init Firebase

        FirebaseConnection firebaseConnection = new FirebaseConnection();
        firebaseConnection.getTable("Requests");
        db = firebaseConnection.getReference();

        //Init component
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listCart);
        txtTotalPrice = (TextView) rootView.findViewById(R.id.total);
        txtTotalItems = (TextView)  rootView.findViewById(R.id.txtTotalItems);
        btnPlace = (FButton) rootView.findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();

            }
        });


        btnOrderHistory = (FButton) rootView.findViewById(R.id.btnOrderHistory);

        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ordersStatus = new Intent(getActivity(),OrderStatus.class);
                startActivity(ordersStatus);
            }
        });
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        loadListFood();

        return rootView;
    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.myDialog);
        alertDialog.setTitle("one more steps!");
        alertDialog.setMessage("Enter your address: ");
        final EditText editAddress = new EditText(getActivity().getBaseContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editAddress.setLayoutParams(lp);
        alertDialog.setView(editAddress);
        alertDialog.setIcon(R.drawable.cart);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Add Order
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        editAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );
                db.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                new Database(getActivity().getBaseContext()).clearCart();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, new OrderFragment()).commit();
                btnOrderHistory.callOnClick();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void loadListFood() {
        cart = new Database(getActivity().getBaseContext()).getCarts();
        adapter = new CartAdapter(cart,getActivity().getBaseContext());
        recyclerView.setAdapter(adapter);

        txtTotalItems.setText(cart.size()+" Items ");
        //colculate total price
        double total = 0;
        for(Order order:cart)
            total += (Double.parseDouble(order.getPrice())) * (Double.parseDouble(order.getQuantity()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }
}