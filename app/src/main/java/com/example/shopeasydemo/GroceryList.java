package com.example.shopeasydemo;

import java.io.Serializable;
import java.util.ArrayList;

public class GroceryList implements Serializable {
    private String listName;
    private ArrayList<ListItem> listItems;
    public GroceryList(String listName, ArrayList<ListItem> listItems){
        this.listItems = listItems;
        this.listName = listName;
    }
    public String getListName(){return listName;}
    public ArrayList<ListItem> getListItems(){return listItems;}
    public void addListItems(String itemName, String category){
        listItems.add(new ListItem(itemName, category));
    }

}
