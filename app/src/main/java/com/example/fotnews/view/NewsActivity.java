package com.example.fotnews.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fotnews.R;
import com.example.fotnews.adapter.NewsAdapter;
import com.example.fotnews.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<News> newsList;
    private static final String NEWS_URL = "http://192.168.8.144:3001/news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        int userId = getIntent().getIntExtra("userId", -1);
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList);
        newsRecyclerView.setAdapter(newsAdapter);

        loadNewsFromBackend();

        // Navigate to User Profile
        ImageButton profileButton = findViewById(R.id.btn_dev_info);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(NewsActivity.this, UserActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            startActivity(intent);
        });
    }

    private void loadNewsFromBackend() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                NEWS_URL,
                null,
                response -> {
                    newsList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            int id = obj.getInt("id");
                            String title = obj.getString("title");
                            String content = obj.getString("content");

                            int imageIndex = (i % 5) + 1;
                            int imageResId = getResources().getIdentifier("image" + imageIndex, "drawable", getPackageName());

                            newsList.add(new News(id, title, content, imageResId));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    newsAdapter.notifyDataSetChanged();
                },
                error -> {
                    Toast.makeText(this, "Failed to load news", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(request);
    }
}
