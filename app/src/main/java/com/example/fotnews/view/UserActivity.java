package com.example.fotnews.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fotnews.R;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {
    private TextView username, email;
    private ImageView profileImage;
    private Button editInfoBtn, signOutBtn, devInfoBtn;

    private int userId;
    private static final String BASE_URL = "http://192.168.8.144:3001/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profileImage = findViewById(R.id.profileImage);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        editInfoBtn = findViewById(R.id.editInfoBtn);
        signOutBtn = findViewById(R.id.signOutBtn);
        devInfoBtn = findViewById(R.id.devInfoBtn);

        // Get passed user info
        userId = getIntent().getIntExtra("userId", -1);
        String currentUsername = getIntent().getStringExtra("username");
        String currentEmail = getIntent().getStringExtra("email");

        username.setText(currentUsername != null ? currentUsername : "UserName");
        email.setText(currentEmail != null ? currentEmail : "Email");

        // Listeners
        editInfoBtn.setOnClickListener(v -> showEditDialog());
        signOutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        devInfoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, DeveloperActivity.class);
            startActivity(intent);
        });
    }

    private void showEditDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_info, null);

        EditText editUsername = dialogView.findViewById(R.id.editUsername);
        EditText editEmail = dialogView.findViewById(R.id.editEmail);
        Button okButton = dialogView.findViewById(R.id.okButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        editUsername.setText(username.getText().toString());
        editEmail.setText(email.getText().toString());

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        cancelButton.setOnClickListener(view -> dialog.dismiss());

        okButton.setOnClickListener(view -> {
            String newUsername = editUsername.getText().toString().trim();
            String newEmail = editEmail.getText().toString().trim();

            if (newUsername.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            } else {
                updateUserInfo(newUsername, newEmail, dialog);
            }
        });

        dialog.show();
    }

    private void updateUserInfo(String newUsername, String newEmail, AlertDialog dialog) {
        if (userId == -1) {
            Toast.makeText(this, "User ID missing", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = BASE_URL + userId;

        JSONObject body = new JSONObject();
        try {
            body.put("username", newUsername);
            body.put("email", newEmail);
        } catch (JSONException e) {
            Toast.makeText(this, "Error creating request", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body,
                response -> {
                    username.setText(newUsername);
                    email.setText(newEmail);
                    dialog.dismiss();
                    Toast.makeText(this, "Info updated", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(this, "Update failed", Toast.LENGTH_LONG).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
