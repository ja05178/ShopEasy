package com.example.shopeasydemo;

import java.io.Serializable;
import java.util.ArrayList;

public class GroceryList implements Serializable {
    private String listName;
    private ArrayList<String> listItems;
    public GroceryList(String listName, ArrayList<String> listItems){
        this.listItems = listItems;
        this.listName = listName;
    }
    public String getListName(){return listName;}
    public ArrayList<String> getListItems(){return listItems;}
    public void addListItems(String newItem){
        listItems.add(newItem);
    }

}
