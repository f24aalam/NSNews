package com.w3students.nsnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.w3students.nsnews.adapters.ArticleAdapter;
import com.w3students.nsnews.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    RecyclerView article_activity;
    List<Article> articles;
    String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Intent intent = getIntent();
        country = intent.getStringExtra("countryCode");
        setTitle(country);
        article_activity=findViewById(R.id.source_category_flag);
        articles=new ArrayList<>();
        ArticleAdapter articleAdapter=new ArticleAdapter(articles,this);
        article_activity.setAdapter(articleAdapter);
        article_activity.setLayoutManager(new LinearLayoutManager(this));
    }
}
