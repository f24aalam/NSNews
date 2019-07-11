package com.w3students.nsnews.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.w3students.nsnews.R;
import com.w3students.nsnews.adapters.ArticleAdapter;
import com.w3students.nsnews.models.Article;
import com.w3students.nsnews.models.ArticleSource;
import com.w3students.nsnews.utils.VolleyRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Everything extends Fragment {

    RecyclerView articleList;
    List<Article> articles;
    AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_everything,container,false);

        articleList = view.findViewById(R.id.everything_view);

        String url = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=8c836e5a02774abc9ecaae10a1d1f5f6";
        articles = new ArrayList<>();

        alertDialog = new SpotsDialog
                .Builder()
                .setContext(view.getContext())
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

                                ArticleAdapter adapter = new ArticleAdapter(articles, view.getContext() );
                                articleList.setAdapter(adapter);
                                articleList.setLayoutManager(new LinearLayoutManager(view.getContext()));

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

        VolleyRequestQueue.getInstance(view.getContext()).addToRequestQueue(request);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.menu_everything);

    }
}
