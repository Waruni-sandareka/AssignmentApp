package com.example.fotnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fotnews.R;
import com.example.fotnews.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> newsList;
    private Context context;
    private static final String DELETE_URL_BASE = "http://192.168.8.144:3001/news/";

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.news_card, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getContent());
        holder.image.setImageResource(news.getImageResId());

        holder.deleteButton.setOnClickListener(v -> {
            deleteNews(news.getId(), position);
        });
    }

    private void deleteNews(int newsId, int position) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = DELETE_URL_BASE + newsId;

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                                                        response -> {
                                                            Toast.makeText(context, "News deleted", Toast.LENGTH_SHORT).show();
                                                            newsList.remove(position);
                                                            notifyItemRemoved(position);
                                                            notifyItemRangeChanged(position, newsList.size());
                                                        },
                                                        error -> {
                                                            Toast.makeText(context, "Failed to delete news", Toast.LENGTH_SHORT).show();
                                                        });

        queue.add(deleteRequest);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;
        Button deleteButton;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsTitle);
            description = itemView.findViewById(R.id.newsDescription);
            image = itemView.findViewById(R.id.newsImage);
            deleteButton = itemView.findViewById(R.id.btnDelete);
        }
    }
}