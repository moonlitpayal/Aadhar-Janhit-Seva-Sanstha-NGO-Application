package com.example.payalngo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.firebase.auth.FirebaseAuth;

public class SessionManager {

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Context _context;

    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "PayalNgoUserSession";

    // Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USER_TYPE = "UserType";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_USER_ID = "UserId"; // ✅ Naya Key add kiya hai

    // User Types
    public static final String USER_TYPE_ADMIN = "ADMIN";
    public static final String USER_TYPE_REGULAR = "USER";
    public static final String USER_TYPE_GUEST = "GUEST";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    // ✅ createLoginSession ko update kiya hai
    public void createLoginSession(String userType, String email, String userId) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_TYPE, userType);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_ID, userId); // User ID bhi save kar rahe hain
        editor.commit();
    }

    public void checkLogin() {
        if (this.isLoggedIn()) {
            String userType = getUserType();
            Intent i;
            if (USER_TYPE_ADMIN.equals(userType)) {
                i = new Intent(_context, AdminPanel.class);
            } else {
                i = new Intent(_context, HomeActivity.class);
            }
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public String getUserType() {
        return pref.getString(KEY_USER_TYPE, null);
    }

    // ✅ Naya function User ID ke liye
    public String getUserId() {
        return pref.getString(KEY_USER_ID, null);
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }
}