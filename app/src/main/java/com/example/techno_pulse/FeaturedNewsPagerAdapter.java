package com.example.techno_pulse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeaturedNewsPagerAdapter extends RecyclerView.Adapter<FeaturedNewsPagerAdapter.ViewHolder> {
    private List<FeaturedNews> newsList;

    public FeaturedNewsPagerAdapter(List<FeaturedNews> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeaturedNews news = newsList.get(position);
        holder.imageView.setImageResource(news.getImageResId());
        holder.title.setText(news.getTitle());
        holder.author.setText(news.getAuthor());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, author;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.featured_news_image);
            title = itemView.findViewById(R.id.featured_news_title);
            author = itemView.findViewById(R.id.featured_news_author);
        }
    }
}
