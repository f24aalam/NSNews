package com.w3students.nsnews.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.w3students.nsnews.adapters.SourceAdapter;
import com.w3students.nsnews.models.Source;
import com.w3students.nsnews.utils.VolleyRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class NewsSources extends Fragment {


    RecyclerView sourceList;
    List<Source> sources;
    AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_sources,container,false);

        sourceList = view.findViewById(R.id.news_sources_view);

        String url = "https://newsapi.org/v2/sources?apiKey=8c836e5a02774abc9ecaae10a1d1f5f6";
        sources = new ArrayList<>();

        alertDialog = new SpotsDialog
                .Builder()
                .setContext(view.getContext())
                .setMessage(R.string.menu_top_headlines)
                .setCancelable(false)
                .build();

        alertDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equalsIgnoreCase("ok")){
                                JSONArray jsonArray = response.getJSONArray("sources");
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Source source = new Source();

                                    source.setId(jsonObject.getString("id"));
                                    source.setName(jsonObject.getString("name"));
                                    source.setDescription(jsonObject.getString("description"));
                                    source.setUrl(jsonObject.getString("url"));
                                    source.setCategory(jsonObject.getString("category"));
                                    source.setLanguage(jsonObject.getString("language"));
                                    source.setCountry(jsonObject.getString("country"));

                                    sources.add(source);

                                }

                                SourceAdapter adapter = new SourceAdapter(sources, view.getContext() );
                                sourceList.setAdapter(adapter);
                                sourceList.setLayoutManager(new LinearLayoutManager(view.getContext()));

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
        getActivity().setTitle(R.string.menu_news_sources);
    }
}
