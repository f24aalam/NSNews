package com.w3students.nsnews;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.w3students.nsnews.adapters.ArticleAdapter;
import com.w3students.nsnews.adapters.SourceAdapter;
import com.w3students.nsnews.models.Article;
import com.w3students.nsnews.models.ArticleSource;
import com.w3students.nsnews.models.Source;
import com.w3students.nsnews.utils.VolleyRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ArticleActivity extends AppCompatActivity {

    RecyclerView articleList;
    List<Article> articles;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String country = intent.getStringExtra("countryCode");

        articleList = findViewById(R.id.source_category_flag);

        String url = "https://newsapi.org/v2/everything?q="+country+"&apiKey=8c836e5a02774abc9ecaae10a1d1f5f6";
        articles = new ArrayList<>();

        alertDialog = new SpotsDialog
                .Builder()
                .setContext(this)
                .setMessage(R.string.menu_everything)
                .setCancelable(false)
                .build();

        alertDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equalsIgnoreCase("ok")){

                                JSONArray jsonArray = response.getJSONArray("articles");
                                for(int i = 0; i < jsonArray.length(); i++){

                                    Article article = new Article();
                                    ArticleSource articleSource = new ArticleSource();

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONObject jsonSourceObject = jsonObject.getJSONObject("source");

                                    articleSource.setId(jsonSourceObject.getString("id"));
                                    articleSource.setName(jsonSourceObject.getString("name"));

                                    article.setArticleSource(articleSource);
                                    article.setAuthor(jsonObject.getString("author"));
                                    article.setTitle(jsonObject.getString("title"));
                                    article.setDescription(jsonObject.getString("description"));
                                    article.setUrl(jsonObject.getString("url"));
                                    article.setUrlToImage(jsonObject.getString("urlToImage"));
                                    article.setPublishedAt(jsonObject.getString("publishedAt"));
                                    article.setContent(jsonObject.getString("content"));

                                    articles.add(article);
                                }

                                ArticleAdapter adapter = new ArticleAdapter(articles, ArticleActivity.this );
                                articleList.setAdapter(adapter);
                                articleList.setLayoutManager(new LinearLayoutManager(ArticleActivity.this));

                                alertDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyRequestQueue.getInstance(this).addToRequestQueue(request);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
