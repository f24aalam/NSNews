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
import com.w3students.nsnews.WebActivity;
import com.w3students.nsnews.R;
import com.w3students.nsnews.models.Article;
import com.w3students.nsnews.models.ArticleSource;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> articles;
    private Context context;

    public ArticleAdapter(List<Article> articles,Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.article,viewGroup,false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int i) {
        final Article article = articles.get(i);
        ArticleSource articleSource = article.getArticleSource();

        articleViewHolder.articleSourceName.setText(articleSource.getName());
        articleViewHolder.articleTitle.setText(article.getTitle());
        articleViewHolder.articlePublishedAt.setText(article.getPublishedAt());
        articleViewHolder.articleDescription.setText(article.getDescription());
        articleViewHolder.articleAuthor.setText(article.getAuthor());
        articleViewHolder.read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url",article.getUrl());
                intent.putExtra("title",article.getTitle());

                context.startActivity(intent);

            }
        });

        Glide.with(context)
                .load(article.getUrlToImage())
                .into(articleViewHolder.articleImage);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView articleSourceName;
        TextView articleTitle;
        TextView articlePublishedAt;
        TextView articleDescription;
        TextView articleAuthor;
        ImageView articleImage;
        Button read_more;



        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            articleSourceName = itemView.findViewById(R.id.article_source);
            articleTitle = itemView.findViewById(R.id.article_title);
            articlePublishedAt = itemView.findViewById(R.id.article_published_at);
            articleDescription = itemView.findViewById(R.id.article_desc);
            articleAuthor = itemView.findViewById(R.id.article_author);
            articleImage = itemView.findViewById(R.id.article_img);
            read_more = itemView.findViewById(R.id.button);



        }
    }
}
