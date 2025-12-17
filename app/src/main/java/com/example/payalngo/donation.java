/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class donation extends AppCompatActivity implements PaymentResultListener {

    private int selectedValue = 100;
    private SeekBar amountSeekbar;
    private TextView selectedAmountText;
    private EditText messageInput;
    private Button donateButton, donationHBtn, btn50, btn100, btn200, btn500;
    private TextView welcome;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private String userName = "Anonymous";

    private ImageView home, donationTab, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        Checkout.preload(getApplicationContext());

        // UI
        amountSeekbar = findViewById(R.id.amountSeekbar);
        selectedAmountText = findViewById(R.id.selectedAmountText);
        messageInput = findViewById(R.id.messageInput);
        donateButton = findViewById(R.id.donateButton);
        donationHBtn = findViewById(R.id.donationHistoryButton);
        welcome = findViewById(R.id.welcome);

        btn50 = findViewById(R.id.btn50);
        btn100 = findViewById(R.id.btn100);
        btn200 = findViewById(R.id.btn200);
        btn500 = findViewById(R.id.btn500);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("Donations");

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            userRef.child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        userName = snapshot.getValue(String.class);
                        welcome.setText("Welcome, " + userName);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(donation.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("Donations");
        }

        amountSeekbar.setMax(1000);
        amountSeekbar.setProgress(selectedValue);
        selectedAmountText.setText("₹" + selectedValue);

        amountSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedValue = progress;
                selectedAmountText.setText("₹" + selectedValue);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btn50.setOnClickListener(v -> updateAmount(50));
        btn100.setOnClickListener(v -> updateAmount(100));
        btn200.setOnClickListener(v -> updateAmount(200));
        btn500.setOnClickListener(v -> updateAmount(500));

        donateButton.setOnClickListener(v -> {
            if (selectedValue <= 0) {
                Toast.makeText(this, "Please select an amount to donate.", Toast.LENGTH_SHORT).show();
                return;
            }
            startRazorpayPayment();
        });

        donationHBtn.setOnClickListener(v -> {
            Intent intent = new Intent(donation.this, DonationHistory.class);
            startActivity(intent);
        });

        setupBottomNavigation(); // ✅ Navigation
    }

    private void updateAmount(int amount) {
        selectedValue = amount;
        amountSeekbar.setProgress(amount);
        selectedAmountText.setText("₹" + amount);
    }

    private void startRazorpayPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_1234567890abcdef"); // Dummy key for testing
        checkout.setImage(R.mipmap.ic_launcher);

        JSONObject options = new JSONObject();
        try {
            options.put("name", "Payal NGO");
            options.put("description", "Test Donation");
            options.put("currency", "INR");
            options.put("amount", selectedValue * 100);

            JSONObject prefill = new JSONObject();
            prefill.put("email", "test@example.com");
            prefill.put("contact", "9999999999");
            options.put("prefill", prefill);

            checkout.open(this, options);
        } catch (JSONException e) {
            Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        saveDonationToFirebase(razorpayPaymentID);
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment failed: " + response, Toast.LENGTH_SHORT).show();
    }

    private void saveDonationToFirebase(String paymentId) {
        String donationId = databaseReference.push().getKey();
        if (donationId == null) {
            Toast.makeText(this, "Error generating donation ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String message = messageInput.getText().toString().trim();

        HashMap<String, Object> donationData = new HashMap<>();
        donationData.put("Name", userName);
        donationData.put("Amount", selectedValue);
        donationData.put("Date", timestamp);
        donationData.put("Message", message);
        donationData.put("PaymentId", paymentId);

        databaseReference.child(donationId).setValue(donationData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Thank you! Donation saved.", Toast.LENGTH_LONG).show();
                    amountSeekbar.setProgress(100);
                    messageInput.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("FirebaseError", "Donation save failed", e);
                });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donationTab = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        home.setOnClickListener(v -> startActivity(new Intent(donation.this, HomeActivity.class)));
        donationTab.setOnClickListener(v -> Toast.makeText(this, "Already on Donations", Toast.LENGTH_SHORT).show());
        story.setOnClickListener(v -> startActivity(new Intent(donation.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(donation.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(donation.this, profile.class)));
    }
}*/


/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

// ✅ FirebaseAuth and FirebaseUser are no longer needed here
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class donation extends AppCompatActivity implements PaymentResultListener {

    private int selectedValue = 100;
    private SeekBar amountSeekbar;
    private TextView selectedAmountText;
    private EditText messageInput, customAmountInput;
    private Button donateButton, donationHBtn, btn50, btn100, btn200, btn500;
    private TextView welcome;


    private SessionManager sessionManager;
    private DatabaseReference userDatabaseReference;
    private DatabaseReference donationDatabaseReference;

    private String userName = "Anonymous";


    private ImageView home, donationTab, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        Checkout.preload(getApplicationContext());


        sessionManager = new SessionManager(getApplicationContext());

        customAmountInput = findViewById(R.id.customAmountInput);

        amountSeekbar = findViewById(R.id.amountSeekbar);
        selectedAmountText = findViewById(R.id.selectedAmountText);
        messageInput = findViewById(R.id.messageInput);
        donateButton = findViewById(R.id.donateButton);
        donationHBtn = findViewById(R.id.donationHistoryButton);
        welcome = findViewById(R.id.welcome);

        btn50 = findViewById(R.id.btn50);
        btn100 = findViewById(R.id.btn100);
        btn200 = findViewById(R.id.btn200);
        btn500 = findViewById(R.id.btn500);

        // Firebase
        donationDatabaseReference = FirebaseDatabase.getInstance().getReference("Donations");
        userDatabaseReference = FirebaseDatabase.getInstance().getReference("Users"); // Reference to Users node


        loadUserDetails();

        amountSeekbar.setMax(1000);
        amountSeekbar.setProgress(selectedValue);
        selectedAmountText.setText("₹" + selectedValue);

        amountSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedValue = progress;
                selectedAmountText.setText("₹" + selectedValue);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btn50.setOnClickListener(v -> updateAmount(50));
        btn100.setOnClickListener(v -> updateAmount(100));
        btn200.setOnClickListener(v -> updateAmount(200));
        btn500.setOnClickListener(v -> updateAmount(500));

        donateButton.setOnClickListener(v -> {
            if (selectedValue <= 0) {
                Toast.makeText(this, "Please select an amount to donate.", Toast.LENGTH_SHORT).show();
                return;
            }
            startRazorpayPayment();
        });

        donationHBtn.setOnClickListener(v -> {
            Intent intent = new Intent(donation.this, DonationHistory.class);
            startActivity(intent);
        });

        setupBottomNavigation();
    }

    private void loadUserDetails() {
        String userType = sessionManager.getUserType();

        // Check if the user is a GUEST
        if (userType != null && userType.equals(SessionManager.USER_TYPE_GUEST)) {
            welcome.setText("Welcome, Guest!");
            this.userName = "Anonymous"; // Set name for donation record
            return;
        }

        // If it's a REGULAR user, get the UID from the session
        String userId = sessionManager.getUserId();

        if (userId != null) {
            userDatabaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists() && snapshot.child("Full Name").exists()) {
                        String fullName = snapshot.child("Full Name").getValue(String.class);
                        welcome.setText("Welcome, " + fullName);
                        // This is important for saving the donation with the correct name
                        donation.this.userName = fullName;
                    } else {
                        welcome.setText("Welcome, User!");
                        donation.this.userName = "Registered User";
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(donation.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
                    welcome.setText("Welcome!");
                    donation.this.userName = "Anonymous";
                }
            });
        } else {
            // Fallback for any other case (e.g., error in session)
            welcome.setText("Welcome, Guest!");
            this.userName = "Anonymous";
        }
    }

    private void updateAmount(int amount) {
        selectedValue = amount;
        amountSeekbar.setProgress(amount);
        selectedAmountText.setText("₹" + amount);
    }

    private void startRazorpayPayment() {
        // Your existing Razorpay code... (No changes needed here)
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_1234567890abcdef");
        checkout.setImage(R.mipmap.ic_launcher);
        JSONObject options = new JSONObject();
        try {
            options.put("name", "Payal NGO");
            options.put("description", "Test Donation");
            options.put("currency", "INR");
            options.put("amount", selectedValue * 100);
            JSONObject prefill = new JSONObject();
            prefill.put("email", "test@example.com");
            prefill.put("contact", "9999999999");
            options.put("prefill", prefill);
            checkout.open(this, options);
        } catch (JSONException e) {
            Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        saveDonationToFirebase(razorpayPaymentID);
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment failed: " + response, Toast.LENGTH_SHORT).show();
    }

    private void saveDonationToFirebase(String paymentId) {
        String donationId = donationDatabaseReference.push().getKey();
        if (donationId == null) {
            Toast.makeText(this, "Error generating donation ID", Toast.LENGTH_SHORT).show();
            return;
        }
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String message = messageInput.getText().toString().trim();
        HashMap<String, Object> donationData = new HashMap<>();
        donationData.put("Name", userName); // This will now have the correct name
        donationData.put("Amount", selectedValue);
        donationData.put("Date", timestamp);
        donationData.put("Message", message);
        donationData.put("PaymentId", paymentId);
        donationDatabaseReference.child(donationId).setValue(donationData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Thank you! Donation saved.", Toast.LENGTH_LONG).show();
                    amountSeekbar.setProgress(100);
                    messageInput.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("FirebaseError", "Donation save failed", e);
                });
    }

    private void setupBottomNavigation() {
        // Your existing bottom navigation code... (No changes needed here)
        home = findViewById(R.id.homeid);
        donationTab = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donationTab.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donationTab.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
        home.setOnClickListener(v -> {
            setSelectedIcon(home);
            startActivity(new Intent(donation.this, HomeActivity.class));
        });
        donationTab.setOnClickListener(v -> {
            setSelectedIcon(donationTab);
            Toast.makeText(this, "Already on Donations", Toast.LENGTH_SHORT).show();
        });
        story.setOnClickListener(v -> {
            setSelectedIcon(story);
            startActivity(new Intent(donation.this, storie.class));
        });
        aboutus.setOnClickListener(v -> {
            setSelectedIcon(aboutus);
            startActivity(new Intent(donation.this, aboutUs.class));
        });
        profile.setOnClickListener(v -> {
            setSelectedIcon(profile);
            startActivity(new Intent(donation.this, profile.class));
        });
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        // Your existing icon selection code... (No changes needed here)
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donationTab.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }
}*/

/*package com.example.payalngo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class donation extends AppCompatActivity {

    private static final int UPI_PAYMENT = 0;

    private int selectedValue = 100;
    private SeekBar amountSeekbar;
    private TextView selectedAmountText;
    private EditText messageInput, customAmountInput;
    private Button donateButton, donationHBtn, btn50, btn100, btn200, btn500;
    private TextView welcome;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private String userName = "Anonymous";

    private ImageView home, donationTab, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        // UI
        amountSeekbar = findViewById(R.id.amountSeekbar);
        selectedAmountText = findViewById(R.id.selectedAmountText);
        messageInput = findViewById(R.id.messageInput);
        customAmountInput = findViewById(R.id.customAmountInput);
        donateButton = findViewById(R.id.donateButton);
        donationHBtn = findViewById(R.id.donationHistoryButton);
        welcome = findViewById(R.id.welcome);

        btn50 = findViewById(R.id.btn50);
        btn100 = findViewById(R.id.btn100);
        btn200 = findViewById(R.id.btn200);
        btn500 = findViewById(R.id.btn500);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Donations");

        fetchUserName();

        amountSeekbar.setMax(1000);
        amountSeekbar.setProgress(selectedValue);
        selectedAmountText.setText("₹" + selectedValue);

        amountSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    selectedValue = progress;
                    selectedAmountText.setText("₹" + selectedValue);
                    customAmountInput.setText(String.valueOf(progress));
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        customAmountInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    try {
                        int customAmount = Integer.parseInt(s.toString());
                        selectedValue = customAmount;
                        amountSeekbar.setProgress(customAmount);
                        selectedAmountText.setText("₹" + customAmount);
                    } catch (NumberFormatException e) {
                        Log.e("DonationActivity", "Invalid custom amount: " + s.toString());
                    }
                }
            }
        });

        btn50.setOnClickListener(v -> updateAmount(50));
        btn100.setOnClickListener(v -> updateAmount(100));
        btn200.setOnClickListener(v -> updateAmount(200));
        btn500.setOnClickListener(v -> updateAmount(500));

        donateButton.setOnClickListener(v -> {
            if (selectedValue <= 0) {
                Toast.makeText(this, "Please select an amount to donate.", Toast.LENGTH_SHORT).show();
                return;
            }
            payUsingUpi();
        });

        donationHBtn.setOnClickListener(v -> {
            Intent intent = new Intent(donation.this, DonationHistory.class);
            startActivity(intent);
        });

        setupBottomNavigation();
    }

    private void updateAmount(int amount) {
        selectedValue = amount;
        amountSeekbar.setProgress(amount);
        selectedAmountText.setText("₹" + amount);
        customAmountInput.setText(String.valueOf(amount));
    }

    private void payUsingUpi() {
        String amountStr = customAmountInput.getText().toString().trim();
        if (TextUtils.isEmpty(amountStr) || Integer.parseInt(amountStr) <= 0) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            return;
        }
        String message = messageInput.getText().toString().trim();

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "varunshah0333@okhdfcbank")
                .appendQueryParameter("pn", "Aadhar Janhit Seva Sanstha")
                .appendQueryParameter("tr", "NGODONATION" + System.currentTimeMillis())
                .appendQueryParameter("tn", message)
                .appendQueryParameter("am", amountStr)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (chooser.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI app found, please install one to proceed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_PAYMENT) {
            if (data != null) {
                String response = data.getStringExtra("response");
                if (response != null) {
                    HashMap<String, String> map = new HashMap<>();
                    String[] dataArray = response.split("&");
                    for (String s : dataArray) {
                        String[] pair = s.split("=");
                        if (pair.length > 1) {
                            map.put(pair[0], pair[1]);
                        }
                    }

                    String status = map.get("Status");
                    String transactionId = map.get("txnRef");

                    if ("Success".equals(status)) {
                        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_LONG).show();
                        saveDonationToFirebase(customAmountInput.getText().toString().trim(), transactionId, messageInput.getText().toString().trim());
                    } else {
                        Toast.makeText(this, "Payment Failed/Cancelled!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Payment response is null", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveDonationToFirebase(String amountStr, String transactionId, String message) {
        String donationId = databaseReference.push().getKey();
        if (donationId == null) {
            Toast.makeText(this, "Error generating donation ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String uid = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "guest_user";

        HashMap<String, Object> donationData = new HashMap<>();
        donationData.put("userId", uid);
        donationData.put("Name", userName);
        donationData.put("Amount", Integer.parseInt(amountStr));
        donationData.put("TransactionId", transactionId);
        donationData.put("Message", message);
        donationData.put("Date", timestamp);
        donationData.put("Status", "Completed");

        databaseReference.child(donationId).setValue(donationData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Thank you! Donation saved and acknowledged.", Toast.LENGTH_LONG).show();
                    customAmountInput.setText("");
                    messageInput.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void fetchUserName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            userRef.child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        userName = snapshot.getValue(String.class);
                        welcome.setText("Welcome, " + userName);
                    } else {
                        welcome.setText("Welcome Guest");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(donation.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            welcome.setText("Welcome Guest");
        }
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donationTab = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        home.setOnClickListener(v -> startActivity(new Intent(donation.this, HomeActivity.class)));
        donationTab.setOnClickListener(v -> Toast.makeText(this, "Already on Donations", Toast.LENGTH_SHORT).show());
        story.setOnClickListener(v -> startActivity(new Intent(donation.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(donation.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(donation.this, profile.class)));

        setSelectedIcon(donationTab);
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donationTab.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }
}*/




/*package com.example.payalngo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class donation extends AppCompatActivity {

    private static final int UPI_PAYMENT = 0;
    private int selectedValue = 100;

    // UI Elements
    private SeekBar amountSeekbar;
    private TextView selectedAmountText, upiIdText, welcome;
    private EditText messageInput, customAmountInput;
    private Button donateButton, donationHBtn, btn50, btn100, btn200, btn500;

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String userName = "Anonymous";

    // Navigation
    private ImageView home, donationTab, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        // 1. Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Donations");

        // 2. Initialize UI Views
        welcome = findViewById(R.id.welcome);
        amountSeekbar = findViewById(R.id.amountSeekbar);
        selectedAmountText = findViewById(R.id.selectedAmountText);
        upiIdText = findViewById(R.id.upiIdText);
        messageInput = findViewById(R.id.messageInput);
        customAmountInput = findViewById(R.id.customAmountInput);
        donateButton = findViewById(R.id.donateButton);
        donationHBtn = findViewById(R.id.donationHistoryButton);

        btn50 = findViewById(R.id.btn50);
        btn100 = findViewById(R.id.btn100);
        btn200 = findViewById(R.id.btn200);
        btn500 = findViewById(R.id.btn500);

        // Load Data
        fetchUserName();
        setupBottomNavigation();

        // 3. Official UPI Copy Logic
        upiIdText.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("UPI ID", "332120009429769@cnrb");
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "UPI ID Copied!", Toast.LENGTH_SHORT).show();
        });

        // 4. Amount Selection Logic
        amountSeekbar.setMax(1000);
        amountSeekbar.setProgress(selectedValue);
        selectedAmountText.setText("₹" + selectedValue);

        amountSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) updateAmount(progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        customAmountInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    try {
                        int amount = Integer.parseInt(s.toString());
                        selectedValue = amount;
                        amountSeekbar.setProgress(amount);
                        selectedAmountText.setText("₹" + amount);
                    } catch (NumberFormatException ignored) {}
                }
            }
        });

        btn50.setOnClickListener(v -> updateAmount(50));
        btn100.setOnClickListener(v -> updateAmount(100));
        btn200.setOnClickListener(v -> updateAmount(200));
        btn500.setOnClickListener(v -> updateAmount(500));

        // 5. Payment Button Trigger
        donateButton.setOnClickListener(v -> {
            if (selectedValue <= 0) {
                Toast.makeText(this, "Please select an amount to donate.", Toast.LENGTH_SHORT).show();
                return;
            }
            payUsingUpi();
        });

        donationHBtn.setOnClickListener(v -> startActivity(new Intent(this, DonationHistory.class)));
    }

    private void updateAmount(int amount) {
        selectedValue = amount;
        amountSeekbar.setProgress(amount);
        selectedAmountText.setText("₹" + amount);
        customAmountInput.setText(String.valueOf(amount));
    }

    private void payUsingUpi() {
        String amountStr = String.valueOf(selectedValue);
        String note = messageInput.getText().toString().trim();

        // Official App & Payment Details
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "332120009429769@cnrb")
                .appendQueryParameter("pn", "Aadhar Janhit Seva Sanstha")
                .appendQueryParameter("tr", "ADHAR" + System.currentTimeMillis())
                .appendQueryParameter("tn", note.isEmpty() ? "NGO Donation" : note)
                .appendQueryParameter("am", amountStr)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (chooser.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI app found, please install GPay or PhonePe.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPI_PAYMENT && data != null) {
            String response = data.getStringExtra("response");
            if (response != null && response.toLowerCase().contains("success")) {
                saveDonationToFirebase(String.valueOf(selectedValue), "TXN_" + System.currentTimeMillis(), messageInput.getText().toString());
            } else {
                Toast.makeText(this, "Payment Cancelled or Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveDonationToFirebase(String amountStr, String txnId, String msg) {
        String id = databaseReference.push().getKey();
        String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String uid = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "guest";

        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", uid);
        data.put("Name", userName);
        data.put("Amount", Integer.parseInt(amountStr));
        data.put("TransactionId", txnId);
        data.put("Message", msg);
        data.put("Date", ts);
        data.put("Status", "Completed");

        if (id != null) {
            databaseReference.child(id).setValue(data).addOnSuccessListener(unused -> {
                showThankYouDialog(amountStr);
            });
        }
    }

    private void showThankYouDialog(String amount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Donation Acknowledged! ❤️");
        builder.setMessage("Thank you, " + userName + ", for donating ₹" + amount + " to Adhar Janhit Seva Sanstha. Your contribution has been saved to your history.");
        builder.setPositiveButton("View History", (dialog, which) -> {
            startActivity(new Intent(this, DonationHistory.class));
            finish();
        });
        builder.setNegativeButton("Close", (dialog, which) -> {
            customAmountInput.setText("");
            messageInput.setText("");
            dialog.dismiss();
        });
        builder.create().show();
    }

    private void fetchUserName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            userRef.child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        userName = snapshot.getValue(String.class);
                        welcome.setText("Welcome, " + userName);
                    }
                }
                @Override public void onCancelled(DatabaseError error) {}
            });
        }
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donationTab = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        home.setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));
        story.setOnClickListener(v -> startActivity(new Intent(this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, profile.class)));
        setSelectedIcon(donationTab);
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donationTab.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }
}*/
package com.example.payalngo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class donation extends AppCompatActivity {

    private static final int UPI_PAYMENT = 0;
    private int selectedValue = 100;

    // UI Elements
    private SeekBar amountSeekbar;
    private TextView selectedAmountText, upiIdText, welcome;
    private EditText messageInput, customAmountInput, utrInput;
    private Button donateButton, donationHBtn, btnPaidManual, btn50, btn100, btn200, btn500;

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String userName = "Anonymous";

    // Navigation
    private ImageView home, donationTab, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        // 1. Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Donations");

        // 2. Initialize UI Views
        welcome = findViewById(R.id.welcome);
        amountSeekbar = findViewById(R.id.amountSeekbar);
        selectedAmountText = findViewById(R.id.selectedAmountText);
        upiIdText = findViewById(R.id.upiIdText);
        messageInput = findViewById(R.id.messageInput);
        customAmountInput = findViewById(R.id.customAmountInput);
        utrInput = findViewById(R.id.utrInput);
        donateButton = findViewById(R.id.donateButton);
        btnPaidManual = findViewById(R.id.btnPaidManual);
        donationHBtn = findViewById(R.id.donationHistoryButton);

        btn50 = findViewById(R.id.btn50);
        btn100 = findViewById(R.id.btn100);
        btn200 = findViewById(R.id.btn200);
        btn500 = findViewById(R.id.btn500);

        fetchUserName();
        setupBottomNavigation();

        // 3. Official UPI Copy Logic
        upiIdText.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("UPI ID", "332120009429769@cnrb");
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "UPI ID Copied!", Toast.LENGTH_SHORT).show();
        });

        // 4. Amount Buttons logic
        btn50.setOnClickListener(v -> updateAmount(50));
        btn100.setOnClickListener(v -> updateAmount(100));
        btn200.setOnClickListener(v -> updateAmount(200));
        btn500.setOnClickListener(v -> updateAmount(500));

        // Seekbar logic
        amountSeekbar.setMax(1000);
        amountSeekbar.setProgress(selectedValue);
        selectedAmountText.setText("₹" + selectedValue);
        amountSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) updateAmount(progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 5. Instant UPI Donate Button
        donateButton.setOnClickListener(v -> {
            if (selectedValue <= 0) {
                Toast.makeText(this, "Please select an amount.", Toast.LENGTH_SHORT).show();
                return;
            }
            payUsingUpi();
        });

        // 6. Manual Payment Submission (Needs Admin Approval)
        btnPaidManual.setOnClickListener(v -> {
            String amount = customAmountInput.getText().toString().trim();
            String utr = utrInput.getText().toString().trim();

            if (amount.isEmpty() || Integer.parseInt(amount) <= 0) {
                Toast.makeText(this, "Enter the amount you paid", Toast.LENGTH_SHORT).show();
                return;
            }
            if (utr.isEmpty()) {
                Toast.makeText(this, "Enter Transaction ID (UTR) for verification", Toast.LENGTH_SHORT).show();
                return;
            }
            saveDonationToFirebase(amount, utr, "Pending Verification");
        });

        donationHBtn.setOnClickListener(v -> startActivity(new Intent(this, DonationHistory.class)));
    }

    private void updateAmount(int amount) {
        selectedValue = amount;
        amountSeekbar.setProgress(amount);
        selectedAmountText.setText("₹" + amount);
        customAmountInput.setText(String.valueOf(amount));
    }

    private void payUsingUpi() {
        String amountStr = String.valueOf(selectedValue);
        String note = messageInput.getText().toString().trim();

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "332120009429769@cnrb")
                .appendQueryParameter("pn", "Aadhar Janhit Seva Sanstha")
                .appendQueryParameter("tr", "ADHAR" + System.currentTimeMillis())
                .appendQueryParameter("tn", note.isEmpty() ? "NGO Donation" : note)
                .appendQueryParameter("am", amountStr)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (chooser.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI app found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPI_PAYMENT && data != null) {
            String response = data.getStringExtra("response");
            if (response != null && (response.toLowerCase().contains("success") || response.contains("Status=00"))) {
                saveDonationToFirebase(String.valueOf(selectedValue), "UPI_INST_" + System.currentTimeMillis(), "Completed");
            } else {
                Toast.makeText(this, "Payment Failed/Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveDonationToFirebase(String amountStr, String txnId, String status) {
        String id = databaseReference.push().getKey();
        String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String uid = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "guest";

        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", uid);
        data.put("Name", userName);
        data.put("Amount", Integer.parseInt(amountStr));
        data.put("TransactionId", txnId);
        data.put("Message", messageInput.getText().toString().trim());
        data.put("Date", ts);
        data.put("Status", status);

        if (id != null) {
            databaseReference.child(id).setValue(data).addOnSuccessListener(unused -> {
                showAcknowledgmentDialog(status, amountStr);
                utrInput.setText("");
            });
        }
    }

    private void showAcknowledgmentDialog(String status, String amount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (status.equals("Completed")) {
            builder.setTitle("Thank You! ❤️");
            builder.setMessage("Your donation of ₹" + amount + " has been received successfully.");
        } else {
            builder.setTitle("Under Review 🙏");
            builder.setMessage("Your payment of ₹" + amount + " is submitted. Admin will verify the Transaction ID soon.");
        }
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void fetchUserName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            userRef.child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        userName = snapshot.getValue(String.class);
                        welcome.setText("Welcome, " + userName);
                    }
                }
                @Override public void onCancelled(DatabaseError error) {}
            });
        }
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donationTab = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        home.setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));
        story.setOnClickListener(v -> startActivity(new Intent(this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, profile.class)));

        donationTab.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }
}