package dev.foursquad.busadmin.V2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import dev.foursquad.busadmin.R;

public class AddAccount extends AppCompatActivity {

    LinearLayout login_form;
    LinearLayout create_form;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private EditText coPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        mAuth = FirebaseAuth.getInstance();
        Button login = findViewById(R.id.button);
        login_form = findViewById(R.id.login_form);
        create_form = findViewById(R.id.create_form);
        final PinView PinView = findViewById(R.id.pinView);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PinView.getText().toString().equals("admin")) {
                    updateUI();
                } else {
                    Toast.makeText(AddAccount.this, "Wrong pin code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI() {
        login_form.setVisibility(View.GONE);
        create_form.setVisibility(View.VISIBLE);
        Button create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = findViewById(R.id.email);
                password = findViewById(R.id.password);
                coPassword = findViewById(R.id.co_passsword);
                if (TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(coPassword.getText())){
                    Toast.makeText(AddAccount.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else{
                    if (password.getText().toString().equals(coPassword.getText().toString())){
                        createUser(email.getText().toString(), password.getText().toString());
                    }else{
                        Toast.makeText(AddAccount.this, "Passwords are not the same", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }


    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(AddAccount.this, LoginActivity.class));
                            finish();
                        } else {
                            Log.e("TAG", "onComplete: "+task.getException().getMessage() );
                            Toast.makeText(AddAccount.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
