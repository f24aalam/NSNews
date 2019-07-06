package com.w3students.nsnews;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.w3students.nsnews.adapters.ArticleAdapter;
import com.w3students.nsnews.models.Article;
import com.w3students.nsnews.models.ArticleSource;
import com.w3students.nsnews.utils.VolleyRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Article> articles;
    RecyclerView articlesView;
    ArticleAdapter articleAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        articlesView = findViewById(R.id.articles_view);
        layoutManager = new LinearLayoutManager(this);

        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=8c836e5a02774abc9ecaae10a1d1f5f6";
        articles = new ArrayList<>();


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

                                articleAdapter = new ArticleAdapter(articles,MainActivity.this);
                                articlesView.setLayoutManager(layoutManager);
                                articlesView.setAdapter(articleAdapter);

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


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
