package com.example.fotnews.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.fotnews.R;

import org.json.*;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView signUpText;

    // 👉 Update to your IP/port if using a real device
    private static final String LOGIN_URL = "http://192.168.8.144:3001/login"; // for emulator
    // private static final String LOGIN_URL = "http://192.168.xx.xx:3001/login"; // for real device

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.username_input);  // using "username_input" for email
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_btn);
        signUpText = findViewById(R.id.donot_text);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });

        signUpText.setOnClickListener(v -> goToSignUp());
    }

    private void loginUser(String email, String password) {
        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("password", password);
        } catch (JSONException e) {
            Toast.makeText(this, "Invalid login data", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                LOGIN_URL,
                loginData,
                response -> {
                    try {
                        int userId = response.getInt("id");
                        String username = response.getString("username");
                        Toast.makeText(this, "Welcome, " + username + "!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, NewsActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("username", username);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(this, "Login success but response invalid", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    String message = "Login failed";
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        try {
                            String errorBody = new String(error.networkResponse.data);
                            JSONObject errorJson = new JSONObject(errorBody);
                            if (errorJson.has("error")) {
                                message = errorJson.getString("error");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
        );

        queue.add(request);
    }

    private void goToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
