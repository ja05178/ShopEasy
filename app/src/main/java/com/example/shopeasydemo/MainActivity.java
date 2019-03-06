package com.example.shopeasydemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private String text;
    private String [] ingredients;
    private String url;
    private Button searchButton;
    private EditText et;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText("onCreate");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        et = (EditText) findViewById(R.id.editText);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = et.getText().toString();
                text = "";
                new getData().execute();
            }
        });
        //new getData().execute();
    }
        private class getData extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements ingredientElement = doc.select("span[class=recipe-ingred_txt added]");
                    Element image = doc.select("img.rec-photo").first();
                    String src = image.attr("src");
                    InputStream input = new java.net.URL(src).openStream();
                    bitmap = BitmapFactory.decodeStream(input);
                    ingredients = new String[ingredientElement.size()];
                    for(int i =0; i < ingredientElement.size();i++){
                        //ingredients[i] = ingredientElement.get(i).text();
                        if(text == null){
                            text =  ingredientElement.get(i).text();
                        }else{
                            text = text +  "\n" +  ingredientElement.get(i).text();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                TextView textView = (TextView) findViewById(R.id.textView);
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
                textView.setText(text);
            }
        }
}
