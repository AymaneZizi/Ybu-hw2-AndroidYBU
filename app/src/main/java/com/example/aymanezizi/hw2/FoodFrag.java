package com.example.aymanezizi.hw2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FoodFrag extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView txt;

    public WebView mWebView;

    public FoodFrag() {
    }

    public static FoodFrag newInstance(String param1) {
        FoodFrag fragment = new FoodFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {

        View v = inflater.inflate(R.layout.fragment_fragment_food, container, false);



        mWebView = (WebView) v.findViewById(R.id.foodweb);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT).show();
        txt = (TextView) v.findViewById(R.id.textFood);

        Thread t = new Thread(new Runnable() {
            Document doc;
            Elements element;
            String urlMenu;
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://ybu.edu.tr/sks/").get();
                    final Element foodTable = doc.select("table").get(1);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String test = foodTable.text();
                            txt.setText(test);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        t.start();
        return v;
    }
}
