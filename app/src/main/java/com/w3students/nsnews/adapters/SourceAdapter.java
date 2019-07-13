package com.w3students.nsnews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.w3students.nsnews.ArticleActivity;
import com.w3students.nsnews.R;
import com.w3students.nsnews.WebActivity;
import com.w3students.nsnews.models.Source;

import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter <SourceAdapter.SourceViewHolder>{

    private List<Source> sources;
    private Context context;
    Intent intent;
    String countryFlags = "https://www.countryflags.io/";

    public SourceAdapter(List<Source> sources, Context context) {
        this.sources = sources;
        this.context = context;
    }

    @NonNull
    @Override
    public SourceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.source_view,viewGroup,false);
        return new SourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceViewHolder sourceViewHolder, final int i) {
        sourceViewHolder.sourceName.setText(sources.get(i).getName());
        sourceViewHolder.sourceCategory.setText(sources.get(i).getCategory());
        Glide.with(context).load(countryFlags+sources.get(i).getCountry()+"/flat/64.png").into(sourceViewHolder.sourceCountry);
        sourceViewHolder.sourceCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("countryCode",sources.get(i).getCountry());
                context.startActivity(intent);
            }
        });
        sourceViewHolder.sourceDescription.setText(sources.get(i).getDescription());
        sourceViewHolder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), WebActivity.class);
                intent.putExtra("url",sources.get(i).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public class SourceViewHolder extends RecyclerView.ViewHolder {
        TextView sourceName,sourceCategory,sourceDescription;
        ImageView sourceCountry;
        Button viewButton;
        public SourceViewHolder(@NonNull View itemView) {
            super(itemView);
            sourceName = itemView.findViewById(R.id.source_name);
            sourceCategory = itemView.findViewById(R.id.source_category);
            sourceCountry = itemView.findViewById(R.id.source_country);
            sourceDescription = itemView.findViewById(R.id.source_description);
            viewButton = itemView.findViewById(R.id.view_btn);
        }
    }
}