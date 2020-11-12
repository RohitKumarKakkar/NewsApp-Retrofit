package com.trojan.newsappwithretrofit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trojan.newsappwithretrofit.Models.Articles;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<Articles> articles;

    public Adapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        final Articles a = articles.get(position);
        holder.tvTitle.setText(a.getTitle());
        holder.tvDate.setText(dateTime(a.getPublishedAt()));
        holder.tvSource.setText(a.getSource().getName());

        String iamegUrl = a.getUrlToImage();
        String url =a.getUrl();
        Picasso.with(context).load(iamegUrl).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,Detailed.class);
                i.putExtra("title",a.getTitle());
                i.putExtra("source",a.getSource().getName());
                i.putExtra("date",dateTime(a.getPublishedAt()));
                i.putExtra("imageUrl",a.getUrlToImage());
                i.putExtra("url",a.getUrl());
                i.putExtra("desc",a.getDescription());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSource, tvDate;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSource = itemView.findViewById(R.id.tvSource);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    public String dateTime(String t) {
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm",Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;

    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

}
