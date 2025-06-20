package com.example.fotnews.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fotnews.R;
import com.example.fotnews.adapter.NewsAdapter;
import com.example.fotnews.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsList = new ArrayList<>();
        newsList.add(new News("Campus Reopening", "University reopens on July 1st.", R.drawable.fot));
        newsList.add(new News("New Research Lab", "New AI lab opens in engineering faculty.", R.drawable.fot));
        newsList.add(new News("Scholarships Available", "Apply now for 2025 scholarships.", R.drawable.fot));
        newsList.add(new News("Scholarships Available", "Apply now for 2025 scholarships.", R.drawable.fot));
        newsList.add(new News("Scholarships Available", "Apply now for 2025 scholarships.", R.drawable.fot));

        newsAdapter = new NewsAdapter(newsList);
        newsRecyclerView.setAdapter(newsAdapter);

        // 🔗 Navigate to UserActivity on icon click
        ImageButton profileButton = findViewById(R.id.btn_dev_info);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(NewsActivity.this, UserActivity.class);
            startActivity(intent);
        });
    }
}
