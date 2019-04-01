package com.example.shopeasydemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

public class MainActivity extends AppCompatActivity{
    private String text;
    private String [] ingredients;
    private String url;
    private Button searchButton;
    private EditText et;
    private Bitmap bitmap;
    private RecipeListRecyclerAdapter mRecyclerAdapter;
    private ArrayList<GroceryList> mArrayListGroceryList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        testData();
        mRecyclerAdapter = new RecipeListRecyclerAdapter(this, mArrayListGroceryList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Button addNewList = this.findViewById(R.id.addGroceryList);
        addNewList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View addGroceryListView = (View) inflater.inflate(R.layout.add_list_popup,null);
                Button addNewGroceryList = addGroceryListView.findViewById(R.id.addNewGroceryList);
                final PopupWindow mPopupWindow = new PopupWindow(addGroceryListView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                addNewGroceryList.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        EditText et = addGroceryListView.findViewById(R.id.addListNameEditText);
                        String newListName = et.getText().toString();
                        ArrayList<String> testItems = new ArrayList<>();
                        mArrayListGroceryList.add(new GroceryList(newListName, testItems));
                        mRecyclerAdapter.notifyItemInserted(mArrayListGroceryList.size() - 1);
                       mPopupWindow.dismiss();
                    }
                });



                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(v, Gravity.CENTER,0,0);
            }
        });

    }
    private void testData() {
        ArrayList<String> testItems = new ArrayList<>();
        testItems.add("fruit");
        testItems.add("vegies");
        testItems.add("drpepper");
        testItems.add("meat");
        mArrayListGroceryList.add(new GroceryList("Dinner Sunday", testItems));
        mArrayListGroceryList.add(new GroceryList("Weekly Groceries", testItems));
        mArrayListGroceryList.add(new GroceryList("New Recipe", testItems));
      // mRecyclerAdapter.notifyDataSetChanged();
    }
}
