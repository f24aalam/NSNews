package com.w3students.nsnews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.w3students.nsnews.R;
import com.w3students.nsnews.models.Source;

import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter <SourceAdapter.SourceViewHolder>{
    private List<Source> Sources;
    private Context context;

    public SourceAdapter(List<Source> sources, Context context) {
        Sources = sources;
        this.context = context;
    }

    @NonNull
    @Override
    public SourceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view =inflater.inflate(R.layout.source_view,viewGroup,false);
        return new SourceViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SourceViewHolder sourceViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SourceViewHolder extends RecyclerView.ViewHolder {
        public SourceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
