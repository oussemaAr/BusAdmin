package dev.foursquad.busadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dev.foursquad.busadmin.V2.MainActivity;
import dev.foursquad.busadmin.adapters.MyAdapter;
import dev.foursquad.busadmin.V2.model.Bus;

public class ListBus extends AppCompatActivity implements MyAdapter.BusClickListener {

    FirebaseDatabase database;
    DatabaseReference reference;
    private ArrayList<Bus> buses = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bus);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("bus");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bus bus = snapshot.getValue(Bus.class);
                    bus.setKey(snapshot.getKey());
                    buses.add(bus);
                }
                adapter = new MyAdapter(buses);
                adapter.setBusClickListener(ListBus.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBusClicked(Bus bus) {
        Intent intent = new Intent(ListBus.this, MainActivity.class);
        intent.putExtra("data", bus);
        startActivity(intent);
    }
}
