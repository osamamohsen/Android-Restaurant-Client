package osama.restaurant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import osama.restaurant.Common.Common;
import osama.restaurant.Model.Request;
import osama.restaurant.ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    DatabaseReference requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        requests = firebase.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.rv_listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());

    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txt_order_id.setText(adapter.getRef(position).getKey());
                viewHolder.txt_order_status.setText(ConvertCodeToStatus(model.getStatus()));
                viewHolder.txt_order_address.setText(model.getAddress());
                viewHolder.txt_order_phone.setText(model.getPhone());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String ConvertCodeToStatus(String status) {
        if(status.equals("0")) return  "Placed";
        if(status.equals("1")) return  "On My Way";
        return "Shipped";
    }
}
