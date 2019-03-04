package com.example.shopeasydemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private String text = "abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText("onCreate");
        new getData().execute();

    }
        private class getData extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                String url = "https://www.allrecipes.com/recipe/257938/spicy-thai-basil-chicken-pad-krapow-gai/?internalSource=staff%20pick&referringId=201&referringContentType=Recipe%20Hub";

                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements ingredient = doc.select("span[class=recipe-ingred_txt added]");
                    text = ingredient.text();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(text);
            }
        }
}
