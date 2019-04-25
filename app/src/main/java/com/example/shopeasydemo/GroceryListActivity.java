package com.example.shopeasydemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity  {
    private String text;
    private String [] ingredients;
    private String url;
    private Button searchButton;
    private EditText et;
    private Bitmap bitmap;

    GroceryList groceryList;
    ArrayList<ListItem> groceryItems;
    CustomExpandableListAdapter adapter;
    ArrayList<String> listCategory;
    HashMap<String, List<ListItem>> listHashMap;
    int position;


    public void initData(){
        groceryList = (GroceryList) getIntent().getSerializableExtra("groceryList");
        position = (int) getIntent().getIntExtra("position", 0);
        groceryItems = groceryList.getListItems();
        TextView listName = (TextView) findViewById(R.id.groceryListName);
        listName.setText(groceryList.getListName());
        listCategory = new ArrayList<>();
        listHashMap = new HashMap<>();

        for(int i =0; i < groceryItems.size(); i ++){
            String category = groceryItems.get(i).getCategory();
            ArrayList<ListItem> listGroceryItems = new ArrayList<>();
            if(!(listCategory.contains(category))){
                listCategory.add(category);
                for(int j = 0; j < groceryItems.size(); j++){
                    if(groceryItems.get(j).getCategory().equals(category)){
                        listGroceryItems.add(groceryItems.get(j));
                    }
                }
                listHashMap.put(category,  listGroceryItems);
            }
        }
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        adapter = new CustomExpandableListAdapter(this, listCategory, listHashMap, groceryList, position);
        expandableListView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_grocery_list);
        setContentView(R.layout.activity_grocery_list);
        initData();

//
//
//        RecyclerView mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerViewGroceryListItems);
//        mRecyclerAdapter = new GroceryListItemsAdapter(this, groceryItems, groceryList);
//        mRecyclerView.setAdapter(mRecyclerAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));



        Button addRecipe = this.findViewById(R.id.addRecipeButton);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View addListItemView = (View) inflater.inflate(R.layout.add_list_recipe_popup,null);
                Button addNewListItem = addListItemView.findViewById(R.id.addListRecipeButton);
                final PopupWindow mPopupWindow = new PopupWindow(addListItemView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                addNewListItem.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        EditText et = addListItemView.findViewById(R.id.addListRecipeEditText);
                        et.setLongClickable(true);
                        String newListItem= et.getText().toString();
                        url = et.getText().toString();
                        text = "";
                        new getData().execute();
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(v, Gravity.CENTER,0,0);
            }
        });

        Button addListItem = this.findViewById(R.id.addListItem);
        addListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View addListItemView = (View) inflater.inflate(R.layout.add_list_item_popup,null);
                Button addNewListItem = addListItemView.findViewById(R.id.addListItemButton);
                final PopupWindow mPopupWindow = new PopupWindow(addListItemView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                addNewListItem.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        EditText et = addListItemView.findViewById(R.id.addListItemEditText);
                        String newListItem= et.getText().toString();

                        EditText et2 = addListItemView.findViewById(R.id.addListCategoryEditText);
                        String newItemCategory = et2.getText().toString();
                        System.out.println("Name " + newListItem + " cat " + newItemCategory + " list " + groceryList.getListName());
                        MainActivity.db.addListItem(groceryList.getListName(),newListItem,newItemCategory);

                        //add to data structures
                        groceryList.addListItems(newListItem, newItemCategory);
                        initData();
                        adapter.notifyChanges();
                        mPopupWindow.dismiss();
                    }
                });



                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(v, Gravity.CENTER,0,0);
            }
        });

    }
    private class getData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (url.contains("allrecipes.com")) {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements ingredientElement = doc.select("span[class=recipe-ingred_txt added]");
                    Element image = doc.select("img[class=rec-photo]").first();
                    String src = image.attr("src");
                    InputStream input = new java.net.URL(src).openStream();
                    bitmap = BitmapFactory.decodeStream(input);
                    ingredients = new String[ingredientElement.size()];
                    for (int i = 0; i < ingredientElement.size(); i++) {
                        ingredients[i] = ingredientElement.get(i).text();
                        if (text == null) {
                            text = ingredientElement.get(i).text();
                        } else {
                            text = text + "\n" + ingredientElement.get(i).text();
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (url.contains("foodnetwork.com")){

                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements ingredientElement = doc.select("p[class=o-Ingredients__a-Ingredient]");
                    Element image = doc.select("img[class=m-MediaBlock__a-Image a-Image]").first();
                    String src = image.attr("src");
                    InputStream input = new java.net.URL(src).openStream();
                    bitmap = BitmapFactory.decodeStream(input);
                    ingredients = new String[ingredientElement.size()];
                    for (int i = 0; i < ingredientElement.size(); i++) {
                        ingredients[i] = ingredientElement.get(i).text();
                        if (text == null) {
                            text = ingredientElement.get(i).text();
                        } else {
                            text = text + "\n" + ingredientElement.get(i).text();
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            if(!(ingredients == null)) {
                for (int i = 0; i < ingredients.length; i++) {
                    System.out.println(ingredients[i]);
                    groceryList.addListItems(ingredients[i], "Recipe List");
                    MainActivity.db.addListItem(groceryList.getListName(), ingredients[i], "Recipe List");
                }
                initData();
            }else{

                Toast.makeText(getApplicationContext(), "Not a compatable web page!",
                        Toast.LENGTH_LONG).show();
            }
//            Intent i = new Intent(getBaseContext(), GroceryListActivity.class);
//            i.putExtra("groceryList", groceryList);
//            startActivity(i);

        }
    }

}
