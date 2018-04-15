package dev.foursquad.busadmin.V2;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dev.foursquad.busadmin.R;
import dev.foursquad.busadmin.V2.model.Bus;
import dev.foursquad.busadmin.V2.model.Station;

public class AddBus extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner station;
    EditText name, description, price, timeTogo, timeToarrive, timeToback, timeToend;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        timeTogo = findViewById(R.id.timeTogo);
        timeToarrive = findViewById(R.id.timeToarrive);
        timeToback = findViewById(R.id.timeToback);
        timeToend = findViewById(R.id.timeToend);
        create = findViewById(R.id.create);
        station = findViewById(R.id.station);

        timeTogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddBus.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTogo.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        timeToarrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddBus.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeToarrive.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        timeToback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddBus.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeToback.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        timeToend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddBus.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeToend.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        FirebaseDatabase.getInstance().getReference("station").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Station> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Station s = snapshot.getValue(Station.class);
                    s.setKey(snapshot.getKey());
                    list.add(s);
                }
                ArrayAdapter<Station> adapter = new ArrayAdapter<>(AddBus.this, android.R.layout.simple_list_item_1, list);
                station.setAdapter(adapter);
                station.setOnItemSelectedListener(AddBus.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        create.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        DatabaseReference bus = FirebaseDatabase.getInstance().getReference("bus");

        Bus b = new Bus();
        b.setDescription(description.getText().toString());
        b.setName(name.getText().toString());
        b.setPrice(price.getText().toString());
        b.setStatus(((Station) station.getSelectedItem()).getKey());
        b.setTimeToarrive(timeToarrive.getText().toString());
        b.setTimeTogo(timeTogo.getText().toString());
        b.setTimeToback(timeToback.getText().toString());
        b.setTimeToend(timeToend.getText().toString());
        b.setStationA(((Station) station.getSelectedItem()).getKey());

        b.setLat(((Station) station.getSelectedItem()).getLat());
        b.setLng(((Station) station.getSelectedItem()).getLng());
        b.setStatus("0");

        bus.push().setValue(b).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(AddBus.this, MainActivity.class));
                finish();
            }
        });


    }
}
