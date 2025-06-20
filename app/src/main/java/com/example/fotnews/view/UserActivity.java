package com.example.fotnews.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fotnews.R;

public class UserActivity extends AppCompatActivity {
    private TextView username, email;
    private ImageView profileImage;
    private Button editInfoBtn, signOutBtn, devInfoBtn;

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

        // Dummy user data
        username.setText("Waruni");
        email.setText("waruni@example.com");

        // Handle button clicks
        editInfoBtn.setOnClickListener(v -> {
            // Navigate to edit activity or show edit dialog
        });

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
}
