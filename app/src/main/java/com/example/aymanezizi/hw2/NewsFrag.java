package com.example.aymanezizi.hw2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    TextView txt;
    private Button btn1,btn2,btn3;
    public WebView myWebView;


    private String mParam1;


    public NewsFrag() {
     }

     public static NewsFrag newInstance(String param1) {
        NewsFrag fragment = new NewsFrag();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_news, container, false);
        Toast.makeText(getActivity(),"Loading...", Toast.LENGTH_SHORT).show();
        txt =(TextView) v.findViewById(R.id.contentnews);
        btn1 = (Button)v.findViewById((R.id.btn_news1));
        btn2 = (Button)v.findViewById((R.id.btn_news2));
        btn3 = (Button)v.findViewById((R.id.btn_news3));



        myWebView= (WebView)v.findViewById(R.id.newsweb);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);   //to make webview size fit the layout
        webSettings.setUseWideViewPort(true);
        myWebView.getSettings().setMinimumFontSize(40);    //increase the font because default is small

        Thread t = new Thread(new Runnable() {

            String[] loadurls = new String[3];
            Document doc;
            Elements[] elements = new Elements[3];
            String[] title = new  String[3];
            @Override
            public void run() {

                try {
                    doc = Jsoup.connect("http://ybu.edu.tr/muhendislik/bilgisayar/").get();

                    elements[0] = doc.select("a[id=ContentPlaceHolder1_ctl01_rpData_hplink_0]");
                    title[0] = elements[0].attr("title");

                    elements[1] = doc.select("a[id=ContentPlaceHolder1_ctl01_rpData_hplink_1]");
                    title[1] = elements[1].attr("title");

                    elements[2] = doc.select("a[id=ContentPlaceHolder1_ctl01_rpData_hplink_2]");
                    title[2] = elements[2].attr("title");

                    for(int k = 0; k<=2; k++)
                    {
                        loadurls[k] = ("http://www.ybu.edu.tr/muhendislik/bilgisayar/")+ elements[k].attr("href");
                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            btn1.setText(title[0]);
                            btn2.setText(title[1]);
                            btn3.setText(title[2]);
                        }
                    });
                }
                catch (IOException e) {
                    e.printStackTrace();
                }



                btn1.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        myWebView.loadUrl(loadurls[0]);


                    }
                });


                btn2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        myWebView.loadUrl(loadurls[1]);

                    }
                });



                btn3.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        myWebView.loadUrl(loadurls[2]);

                    }
                });



            }
        });
        t.start();

        return v;
    }


}







