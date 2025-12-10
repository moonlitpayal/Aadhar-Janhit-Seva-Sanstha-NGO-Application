package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText semail, suname, spass, sname, sphone;
    Button signup;
    TextView redirectToLogin;
    private FirebaseAuth mauth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup);

        semail = findViewById(R.id.signuser);
        suname = findViewById(R.id.signname);
        spass = findViewById(R.id.singpassword);
        sname = findViewById(R.id.userfullname);
        signup = findViewById(R.id.signup);
        redirectToLogin = findViewById(R.id.loginn);
        sphone = findViewById(R.id.usermobile);

        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        signup.setOnClickListener(v -> registerUser());

        redirectToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String remail = semail.getText().toString().trim();
        String rusern = suname.getText().toString().trim();
        String rpass = spass.getText().toString().trim();
        String rfullname = sname.getText().toString().trim();
        String rphone = sphone.getText().toString().trim();

        if (TextUtils.isEmpty(remail) || TextUtils.isEmpty(rusern) || TextUtils.isEmpty(rpass) || TextUtils.isEmpty(rfullname) || TextUtils.isEmpty(rphone)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rpass.length() < 6) {
            Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        mauth.createUserWithEmailAndPassword(remail, rpass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mauth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            HashMap<String, String> userData = new HashMap<>();
                            userData.put("Username", rusern);
                            userData.put("Full Name", rfullname); // 👇 Fix: Key ko "Full Name" kiya gaya hai
                            userData.put("Email", remail);
                            userData.put("Phone", rphone);

                            databaseReference.child(userId).setValue(userData)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Failed to save user data", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}