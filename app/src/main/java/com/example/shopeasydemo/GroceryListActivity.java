package com.example.shopeasydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class GroceryListActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        final GroceryList groceryList = (GroceryList) getIntent().getSerializableExtra("groceryList");
        ArrayList<String> groceryItems = groceryList.getListItems();
        TextView listName = (TextView) findViewById(R.id.groceryListName);
        listName.setText(groceryList.getListName());


        RecyclerView mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerViewGroceryListItems);
        GroceryListItemsAdapter mRecyclerAdapter = new GroceryListItemsAdapter(this, groceryItems);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Button addRecipe = this.findViewById(R.id.addRecipeButton);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), GroceryListAdd.class);
                i.putExtra("groceryList", groceryList);
                startActivity(i);
            }
        });






    }

}
