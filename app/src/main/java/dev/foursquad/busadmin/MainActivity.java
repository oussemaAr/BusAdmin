package dev.foursquad.busadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import dev.foursquad.busadmin.model.Bus;
import dev.foursquad.busadmin.model.Station;

public class MainActivity extends AppCompatActivity {

    private int[] drawbles = new int[]{R.drawable.oval_shape_normal, R.drawable.oval_shape_panne, R.drawable.oval_shape_retard};
    private String[] st = new String[]{"NORMAL", "PANNE", "TARDER"};
    private int i;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Bus bus;
    private TextView s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.imageView);
        s = findViewById(R.id.status);
        final Button status = findViewById(R.id.etat);
        Button connection = findViewById(R.id.deconnecter);
        i = 0;

        bus = (Bus) getIntent().getSerializableExtra("data");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setBackgroundResource(drawbles[i % 3]);
                s.setText(st[i % 3]);
                i++;
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        Button direction = findViewById(R.id.direction);
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stationA = bus.getStationA();
                String stationB = bus.getStationB();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("s1", stationA);
                intent.putExtra("s2", stationB);
                startActivity(intent);
            }
        });
    }

    private void sendData() {
        DatabaseReference busRef = FirebaseDatabase.getInstance().getReference("bus");
        HashMap<String, String> map = new HashMap<>();
        busRef.child(bus.getKey()).child("status").setValue(s.getText().toString());
    }

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        revokeAccess();
                    }
                });
    }

    private void revokeAccess() {
        mAuth.signOut();
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }
}
