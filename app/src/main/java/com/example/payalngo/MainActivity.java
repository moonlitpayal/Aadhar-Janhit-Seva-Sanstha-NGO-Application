/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class    MainActivity extends AppCompatActivity {

    // Declaring UI elements
    EditText email, password;
    Button loginbtn, guestbtn, mobilebtn;
    TextView redirectToRegister;

    // Firebase authentication instance
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enables edge-to-edge UI rendering
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Linking UI components
        email = findViewById(R.id.useremail);
        password = findViewById(R.id.userpassword);
        loginbtn = findViewById(R.id.loginbtn);
        guestbtn = findViewById(R.id.guestbtn);
        mobilebtn = findViewById(R.id.mobilebtn);
        redirectToRegister = findViewById(R.id.textv);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // 🔐 Login with email/password
        loginbtn.setOnClickListener(v -> {
            String useremail = email.getText().toString().trim();
            String userpassword = password.getText().toString().trim();

            if (TextUtils.isEmpty(useremail)) {
                email.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(userpassword)) {
                password.setError("Password is required");
                return;
            }

            if (useremail.equals("adminn@gmail.com") && userpassword.equals("1234567890")) {
                Toast.makeText(MainActivity.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, AdminPanel.class));
                finish();
            } else {
                auth.signInWithEmailAndPassword(useremail, userpassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // 📝 Register redirect
        redirectToRegister.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
            finish();
        });

        // 👤 Guest login
        guestbtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Guest Login", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        });

        // 📱 Mobile login via OTP
        mobilebtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginWithMobile.class));
        });
    }
}*/


package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Declaring UI elements
    EditText email, password;
    Button loginbtn, guestbtn, mobilebtn;
    TextView redirectToRegister;

    // Firebase authentication instance
    private FirebaseAuth auth;

    // Session Manager
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Session Manager
        sessionManager = new SessionManager(getApplicationContext());

        // Check if the user is already logged in BEFORE setting the view
        if (sessionManager.isLoggedIn()) {
            sessionManager.checkLogin(); // This will redirect to the correct activity
            finish(); // Finish MainActivity so user can't press back to it
            return; // Stop executing the rest of onCreate
        }

        // If not logged in, proceed to set up the login UI
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Linking UI components
        email = findViewById(R.id.useremail);
        password = findViewById(R.id.userpassword);
        loginbtn = findViewById(R.id.loginbtn);
        guestbtn = findViewById(R.id.guestbtn);
        mobilebtn = findViewById(R.id.mobilebtn);
        redirectToRegister = findViewById(R.id.textv);

        // 🔐 Login with email/password
        loginbtn.setOnClickListener(v -> {
            String useremail = email.getText().toString().trim();
            String userpassword = password.getText().toString().trim();

            if (TextUtils.isEmpty(useremail)) {
                email.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(userpassword)) {
                password.setError("Password is required");
                return;
            }

            // --- Admin Login ---
            if (useremail.equals("adminn@gmail.com") && userpassword.equals("1234567890")) {
                Toast.makeText(MainActivity.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                // We pass 'null' for the User ID because the admin is not a Firebase user.
                sessionManager.createLoginSession(SessionManager.USER_TYPE_ADMIN, useremail, null);
                startActivity(new Intent(MainActivity.this, AdminPanel.class));
                finish();
            } else {
                // --- Regular User Login ---
                auth.signInWithEmailAndPassword(useremail, userpassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // ✅ FINAL FIX: Get UID right after login and save it to the session
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null) {
                                    String userId = user.getUid();
                                    sessionManager.createLoginSession(SessionManager.USER_TYPE_REGULAR, useremail, userId);

                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    finish();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // 📝 Register redirect
        redirectToRegister.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
            // Do not finish() here, so user can come back
        });

        // 👤 Guest login
        guestbtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Guest Login", Toast.LENGTH_SHORT).show();
            // We pass 'null' for email and UID for guests
            sessionManager.createLoginSession(SessionManager.USER_TYPE_GUEST, null, null);
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        });

        // 📱 Mobile login via OTP
        mobilebtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginWithMobile.class));
        });
    }
}